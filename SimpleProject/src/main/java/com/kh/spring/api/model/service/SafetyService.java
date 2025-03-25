package com.kh.spring.api.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SafetyService {
	
	public String requestHospitalApi() throws IOException {
		// 우리가 만들고 있는 자바 프로그램에서
		// API 서버로 요청 보내고 응답 받아서 콘솔에 출력하기
		
		StringBuilder sb = new StringBuilder();
		sb.append("https://www.safetydata.go.kr/V2/api/DSSP-IF-10840");
		sb.append("?serviceKey=M9Z32MU58C747IQ9");
		
		// HttpURLConnection 객체를 활용해서 API 서버로 요청을 보내서 응답을 받아와야 함
		// 1. java.net.URL 객체 생성 => 생성자 호출 시 인자값으로 요청할 URL을 전달
		URL url = new URL(sb.toString());
		
		// 2. URL 객체를 이용해서 HttpURLConnection 객체 생성
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		
		connection.connect();
		
		// API 서버와 스트림 연결(BufferedReader)
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String responseData = br.readLine();
		
		log.info(responseData);
		
		br.close();
		is.close();
		connection.disconnect();
		return responseData;
	}
	
	public String requestMessage(int pageNo) {
		
		final String SERVICE_KEY = "8SGNFNK8490L3IRY";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("https://www.safetydata.go.kr/V2/api/DSSP-IF-00247");
		sb.append("?serviceKey=" + SERVICE_KEY);
		sb.append("&pageNo=" + pageNo);
		sb.append("&numOfRows=3");
		
		try {
			
			URI uri = new URI(sb.toString());
			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.getForObject(uri, String.class);
			return response;
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
