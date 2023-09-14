package aaa.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import aaa.model.PaymentResponseMember;

@Service
public class PayService {

//   @Value("${iamport.impKey}")
   private String impKey="4780385322371542";

//   @Value("${iamport.impSecret}")
   private String impSecret="SS3JF3Z4XOrf6uLjjtTqyIsiI0gH5owDz62Ebcaha64SS9JhQ1c3AdQtDb3fxTpS4EWa3EMIzGIV0Trc";

   // 토큰발급
   public String getToken() throws Exception {
      // 객체선언, 초기화
      HttpsURLConnection conn = null;
      // api에 보낼 요청 url
      URL url = new URL("https://api.iamport.kr/users/getToken");
      // url 정보를 바탕으로 원격서버 통신을 위한 연결 오픈
      conn = (HttpsURLConnection) url.openConnection();
      // 요청메서드 설정
      conn.setRequestMethod("POST");   // 꼭
      // 요청헤더에 본문 데이터타입 형식을 json으로 설정
      conn.setRequestProperty("Content-type", "application/json");
      // 요청헤더에 응답 형식을 json으로 설정
      conn.setRequestProperty("Accept", "application/json");
      // 데이터를 서버로 보낼 것을 설정
      conn.setDoOutput(true);
      
      // json객체 생성, 초기화
      JsonObject json = new JsonObject();
      
      // 속성 추가
      json.addProperty("imp_key", impKey);
      json.addProperty("imp_secret", impSecret);
      
      // 객체 생성, 초기화
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
      
      // http요청 본문에 기록함(json객체를 문자열로 변환해서) 
      bw.write(json.toString());
      // 버퍼를 비우고 데이터를 전송
      bw.flush();
      // 버퍼를 닫음
      bw.close();

      // http응답을 읽기위해 reader 초기화
      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      // json데이터 파싱을 위한 객체 생성, 초기화
      Gson gson = new Gson();
      // response = http응답을 json문자열로 읽어오고, 이를 reader가 gson의 Map객체로 파싱."response" 키를 가진 값을 문자열로 변환
      String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
      // token = response값에서 "access_token" 키를 가진 값을 문자열로 변환
      String token = gson.fromJson(response, Map.class).get("access_token").toString();

      br.close();
      conn.disconnect();
//      System.out.println("토큰을 발급받았습니다." + token);
      return token;
   }
   
   
    // 결제검증
   public String paymentInfo(String imp_uid, String access_token) throws Exception {
//      System.out.println("검증합니다. 검증할 거래의 imp_uid = " + imp_uid + ", access_token = " + access_token);
      HttpsURLConnection conn = null;
      // 단건정보 - 결제금액확인 목적
      URL url = new URL("https://api.iamport.kr/payments/" + imp_uid);
      // url 정보를 바탕으로 원격서버 통신을 위한 연결 오픈
      conn = (HttpsURLConnection) url.openConnection();

      conn.setRequestMethod("GET");
      conn.setRequestProperty("Authorization", access_token);
      conn.setDoOutput(true);

      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

      JSONParser parser = new JSONParser();

      JSONObject p = (JSONObject) parser.parse(br.readLine());
      
      String response = p.get("response").toString();
      
      p = (JSONObject) parser.parse(response);
      
      String amount = p.get("amount").toString();
      // br과 conn을 닫아 주었더니 자바스크립트문 해석을 못해서 db저장과 리다이렉트가 발생하지 않음
      return amount;
   }
   
   
   // 결제취소
   public void paymentCancel(String access_token, String imp_uid, String reason) throws Exception {
//      System.out.println("취소합니다. 취소할 거래의 imp_uid = " + imp_uid);
      HttpsURLConnection conn = null;
      // 결제취소
      URL url = new URL("https://api.iamport.kr/payments/cancel");

      conn = (HttpsURLConnection) url.openConnection();

      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-type", "application/json");
      conn.setRequestProperty("Accept", "application/json");
      conn.setRequestProperty("Authorization", access_token);
      conn.setDoOutput(true);
      
      JsonObject json = new JsonObject();

      json.addProperty("reason", reason);
      json.addProperty("imp_uid", imp_uid);
      
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

      bw.write(json.toString());
      bw.flush();
      bw.close();
      
      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      br.close();
      conn.disconnect();
   }
   
   
   @Autowired
   PayMapper paym;
   // imp_uid 리스트로 결제정보 가져오기
    public List<PaymentResponseMember.Payment> getPaymentData(List<String> impuidList) throws Exception {
        String token = getToken();
        String apiUrl = "https://api.iamport.kr/payments?" +
                impuidList.stream()
                        .map(uid -> "imp_uid[]=" + uid)
                        .collect(Collectors.joining("&"))
                + "&_token=" + token;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PaymentResponseMember> responseEntity = restTemplate.getForEntity(apiUrl, PaymentResponseMember.class);
        List<PaymentResponseMember.Payment> paymentData = responseEntity.getBody().getResponse();
 
        // id 검색하고 시간데이터포맷팅해서 세팅
        for (PaymentResponseMember.Payment payment : paymentData) {
            String id = paym.idget(payment.getImp_uid());
            payment.setId(id);
            payment.setPaid_at(unixformat(payment.getPaid_at()));
            payment.setStarted_at(unixformat(payment.getStarted_at()));
            payment.setFailed_at(unixformat(payment.getFailed_at()));
            payment.setCancelled_at(unixformat(payment.getCancelled_at()));
            payment.setVbank_date(unixformat(payment.getVbank_date()));
        }
        
        // 정렬
      Collections.sort(paymentData, new Comparator<PaymentResponseMember.Payment>() {
          public int compare(PaymentResponseMember.Payment a, PaymentResponseMember.Payment b) {
              // paid_at 기준으로 내림차순 정렬
              return b.getPaid_at().compareTo(a.getPaid_at());
          }
      });
        return paymentData;
    }
   
    
    
   // Date형으로 받은 날짜 포맷팅 메서드
    public String dateformat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    // 날짜+시간 포맷팅 메서드
    public String timeformat(Date date) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return sdf.format(date);
    }
    
    // String형으로 받은 UNIXtimestamp를 String으로 포맷팅 메서드
    public String unixformat(String date) {
        if (!date.equals("0")) {
            Long dateInLong = Long.parseLong(date);
            Date unixToDate = new Date(dateInLong * 1000L);
            return timeformat(unixToDate);
        }
        return "0";
    }

    // 포맷팅된 형태(yyyy-MM-dd)의 String을 Unix타임스탬프로
    public Long stringToUnix(String date) throws java.text.ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date datedDate = sdf.parse(date);
       // Date 객체를 Unix타임스탬프로 변환
       long unixDate = datedDate.getTime() / 1000;
       return unixDate;
    }
}