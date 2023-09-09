package aaa.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class PayService {

//	@Value("${iamport.impKey}")
	private static String impKey="4780385322371542";

//	@Value("${iamport.impSecret}")
	private static String impSecret="SS3JF3Z4XOrf6uLjjtTqyIsiI0gH5owDz62Ebcaha64SS9JhQ1c3AdQtDb3fxTpS4EWa3EMIzGIV0Trc";

	// 토큰발급
	public static String getToken() throws Exception {
		// 객체선언, 초기화
		HttpsURLConnection conn = null;
		// api에 보낼 요청 url
		URL url = new URL("https://api.iamport.kr/users/getToken");
		// url 정보를 바탕으로 원격서버 통신을 위한 연결 오픈
		conn = (HttpsURLConnection) url.openConnection();
		// 요청메서드 설정
		conn.setRequestMethod("POST");	// 꼭
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
//		System.out.println("토큰을 발급받았습니다." + token);
		return token;
	}
	
    // 결제검증
	public static String paymentInfo(String imp_uid, String access_token) throws Exception {
		System.out.println("검증합니다. 검증할 거래의 imp_uid = " + imp_uid + ", access_token = " + access_token);
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
	public static void paymentCancle(String access_token, String imp_uid, String amount,String reason) throws Exception {
		System.out.println("취소합니다. 취소할 거래의 imp_uid = " + imp_uid);
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
		json.addProperty("amount", amount);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

		bw.write(json.toString());
		bw.flush();
		bw.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		br.close();
		conn.disconnect();
	}
}
