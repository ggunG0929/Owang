package aaa.controll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import aaa.model.MCompanyDTO;
import aaa.model.PaymentResponseAll;
import aaa.model.PaymentResponseMember;
import aaa.model.PaymentResponseAll.PaymentAll;
import aaa.model.ProductDTO;
import aaa.model.SoloDTO;
import aaa.service.MCompanyMapper;
import aaa.service.PayMapper;
import aaa.service.PayService;
import aaa.service.ProductMapper;
import aaa.service.SoloMapper;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("admin_product")
public class AdminProductController {

	@Resource
	ProductMapper pm;

	// 상품관리
	@RequestMapping("/modify")
	String admin_product_modify(Model mm) {
		// 상품 목록
		List<ProductDTO> data = pm.list();
		mm.addAttribute("data", data);
		// 상품 추가 위한 DTO
		mm.addAttribute("ProductDTO", new ProductDTO());
		return "admin/product/modify";
	}

	@PostMapping("/insertProduct")
	public String addProduct(@ModelAttribute ProductDTO productDTO) {
		// DTO 정보를 db에 저장(상품추가)
		pm.insert(productDTO);
		return "redirect:/admin_product/modify"; // 목록 페이지로 리다이렉트(새로고침)
	}

	@RequestMapping("/deleteProduct")
	public String deleteProduct(@RequestParam("productId") String productId) {
		// 파라미터로 전달받은 상품 id를 통해 db 삭제(상품삭제)
		pm.delete(productId);
		return "redirect:/admin_product/modify";
	}

	@Resource
	PayMapper paym;
	@Autowired
	PayService payS;

	// 결제정산
	@RequestMapping("/graph")
	String admin_product_graph(Model mm,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

		// 그래프 데이타
		// db에서 일매출 합산
		List<Map<String, Object>> dailytotal = paym.dailytotal(startDate, endDate);

		// 리스트 데이타
		// db에서 회원종류별 매출액 순위리스트
		List<Map<String, Object>> totalbys = paym.totalbys(startDate, endDate);
		List<Map<String, Object>> totalbyc = paym.totalbyc(startDate, endDate);
		// 회원자료 중 sid나 cid가 없는 자료 제외하기 - sid가 없는 자료면 cid자료고, cid가 없는 자료면 sid자료임
		totalbys = totalbys.stream().filter(map -> map.containsKey("sid")).collect(Collectors.toList());
		totalbyc = totalbyc.stream().filter(map -> map.containsKey("cid")).collect(Collectors.toList());

		// db에서 상품별 매출액 순위리스트
		List<Map<String, Object>> totalbyp = paym.totalbyp(startDate, endDate);

		// 기간내 값들 합산(원본맵,합산할 키)
		int totalSum = mapSum(dailytotal, "totalAmount");
		int sSum = mapSum(totalbys, "total");
		int cSum = mapSum(totalbyc, "total");
		int pSum = mapSum(totalbyp, "total");

		// 오늘날짜(날짜 선택시 오늘 이후 날짜는 선택 불가하도록 따로 정보 주기-string으로)
		String today = payS.dateformat(new Date());

		// 미선택시 db가 들어간 첫날과 오늘을 자동선택
		// 선택시 선택한 날짜 뜨도록 string으로 보내주기
		String strStartDate = "2022-09-01";
		String strEndDate = today;
		if (startDate != null && endDate != null) {
			// 시작일이 종료일보다 전인 경우 혹은 같은 경우
			if (startDate.before(endDate) || startDate.equals(endDate)) {
				strStartDate = payS.dateformat(startDate);
				strEndDate = payS.dateformat(endDate);
			} else {
				String errorMsg = "기간을 확인하세요";
				mm.addAttribute("errorMsg", errorMsg);
				startDate = null;
				endDate = null;
			}
		}
		mm.addAttribute("graphData", dailytotal);
		mm.addAttribute("slist", totalbys);
		mm.addAttribute("clist", totalbyc);
		mm.addAttribute("plist", totalbyp);
		mm.addAttribute("totalSum", totalSum);
		mm.addAttribute("sSum", sSum);
		mm.addAttribute("cSum", cSum);
		mm.addAttribute("pSum", pSum);
		mm.addAttribute("today", today);
		mm.addAttribute("startDate", strStartDate);
		mm.addAttribute("endDate", strEndDate);

		return "admin/product/graph";
	}
	// 맵리스트에서 키 값들을 합하는 메서드
	public int mapSum(List<Map<String, Object>> list, String key) {
		return list.stream().mapToInt(entry -> ((BigDecimal) entry.get(key)).intValue()).sum();
	}

	// 전체 매출내역
	@RequestMapping("/payment/{page}")
	public String getPayments(Model mm, @PathVariable(required = false) int page,
			@RequestParam(value = "status", defaultValue = "paid") String status,
			@RequestParam(value = "limit", defaultValue = "15") int limit,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to,
			@RequestParam(value = "sorting", defaultValue = "-updated") String sorting
	// unix변환과 getToken때문에
	) throws Exception {
		// 날짜 변환
		String today = payS.dateformat(new Date());
		String range = "";
		if (!StringUtils.isEmpty(from) && !StringUtils.isEmpty(to)) {
			// Unix타임스탬프로 변환
			long fromunix = payS.stringToUnix(from);
			// 날짜의 전날까지의 결과만 나와서 to에 하루를 더해줌
			long tounix = payS.stringToUnix(to) + 24 * 60 * 60; // 24시간 * 60분 * 60초
			if (fromunix < tounix) {
				range = "&from=" + fromunix + "&to=" + tounix;
			} else {
				String errorMsg = "기간을 확인하세요 \n전체기간으로 검색합니다";
				mm.addAttribute("errorMsg", errorMsg);
				from = null;
				to = null;
			}
		}
		// API 통신
		String token = payS.getToken(); // PayService를 통해 토큰을 가져옴
		// URL 생성
		String apiUrl = "https://api.iamport.kr/payments/status/" + status + "?page=" + page + "&limit=" + limit + range
				+ "&sorting=" + sorting + "&_token=" + token;
		// rest템플릿
		RestTemplate restTemplate = new RestTemplate();
		// PaymentResponse 형태로 정보받음
		ResponseEntity<PaymentResponseAll> responseEntity = restTemplate.getForEntity(apiUrl, PaymentResponseAll.class);
		System.out.println("응답코드 : " + responseEntity.getBody().getCode());
		if (responseEntity.getBody().getResponse() == null) {
			mm.addAttribute("errorMsg", responseEntity.getBody().getMessage());
		} else {
			List<PaymentAll> paymentData = responseEntity.getBody().getResponse().getList();

			for (PaymentResponseAll.PaymentAll payment : paymentData) {
				// imp_uid로 db정보 검색해서 id 가져옴
				String id = paym.idget(payment.getImp_uid());
				if (id != null && paym.isEndMem(id)) {
					id += "(탈퇴)";
				}
				payment.setId(id);

				// unixtimestamp정보들을 포맷팅
				payment.setPaid_at(payS.unixformat(payment.getPaid_at()));
				payment.setStarted_at(payS.unixformat(payment.getStarted_at()));
				payment.setFailed_at(payS.unixformat(payment.getFailed_at()));
				payment.setCancelled_at(payS.unixformat(payment.getCancelled_at()));

				// status를 수정
				Map<String, String> statusToKor = new HashMap<>();
				{
					statusToKor.put("paid", "완료");
					statusToKor.put("ready", "대기");
					statusToKor.put("cancelled", "취소");
					statusToKor.put("failed", "실패");
				}
				payment.setStatus(statusToKor.get(payment.getStatus()));
			}

			// 페이지처리
			int total = responseEntity.getBody().getResponse().getTotal();
			int totalPages = (int) Math.ceil((double) total / limit);
//        System.out.println(responseEntity);
			// 앞뒤로 몇페이지씩 보일건지
			int ranged = 2;
			// 둘 중에 큰 숫자
			int startPage = Math.max(1, page - ranged);
			// 둘 중에 작은 숫자
			int endPage = Math.min(totalPages, page + ranged);
			// 이전버튼 눌렀을 때의 페이지
			int prevPage = Math.max(1, page - ranged - 1);
			// 다음버튼 눌렀을 때의 페이지
			int nextPage = Math.min(totalPages, page + ranged + 1);

			mm.addAttribute("page", page);
			mm.addAttribute("paymentData", paymentData);
			mm.addAttribute("status", status);
			mm.addAttribute("limit", limit);
			mm.addAttribute("from", from);
			mm.addAttribute("to", to);
			mm.addAttribute("sorting", sorting);
			mm.addAttribute("today", today);

			mm.addAttribute("startPage", startPage);
			mm.addAttribute("endPage", endPage);
			mm.addAttribute("prevPage", prevPage);
			mm.addAttribute("nextPage", nextPage);
			mm.addAttribute("totalPages", totalPages);
		}
		return "admin/product/payment_list";
	}

	// 매출내역상세
	@RequestMapping("/payment/detail/{impuid}")
	String paymentByImpuid(Model mm, @PathVariable String impuid) throws Exception {
		// impuid를 List에 추가
		List<String> impuidList = new ArrayList<>();
		impuidList.add(impuid);
		List<PaymentResponseMember.Payment> paymentData = payS.getPaymentData(impuidList);
		mm.addAttribute("paymentData", paymentData);
		return "admin/product/payment_detail";
	}

	@Resource
	SoloMapper sm;
	@Resource
	MCompanyMapper mcm;

	// 결제취소
	@RequestMapping("/payment/cancle")
	String paymentCancle(@RequestParam("impUid") String impUid, @RequestParam("id") String id,
			@RequestParam("name") String name) throws Exception {
		// 유효기간 추출
		int valid = 0;
		// 상품명에서 숫자추출
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(name);
		Date today = new Date();
		if (matcher.find()) {
			String num = matcher.group();
			valid = Integer.parseInt(num);
		}
		// 상품명으로 기업회원인지 개인회원인지
		if (name.contains("채용")) {
			// 기업회원
			// DTO 불러오기
			MCompanyDTO compinfo = mcm.deatilaaaCompany(id);
			// 유효기간 변경
			Date cdate = compinfo.getCdate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(cdate);
			calendar.add(Calendar.DATE, -valid);
			cdate = calendar.getTime();
			compinfo.setCdate(cdate);
			// db 수정
			if (compinfo.getCtype() == 2 && cdate != null && cdate.before(today)) { //
				compinfo.setCtype(1);
				mcm.logincmember(compinfo);
			} else {
				mcm.paycmember(compinfo);
			}
		} else {
			// 개인회원
			SoloDTO soloinfo = sm.detailSolo(id);
			Date sdate = soloinfo.getSdate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdate);
			calendar.add(Calendar.DATE, -valid);
			sdate = calendar.getTime();
			soloinfo.setSdate(sdate);
			if (soloinfo.getStype() == 2 && sdate != null && sdate.before(today)) { //
				soloinfo.setStype(1);
				sm.loginsmember(soloinfo);
			} else {
				sm.paysmember(soloinfo);
			}
		}
		// 취소진행
		String token = payS.getToken();
		payS.paymentCancel(token, impUid, "취소사유: 관리자에 의한 취소");
		paym.payCancel(impUid);
		return "redirect:/admin_product/payment/detail/" + impUid;
	}
}