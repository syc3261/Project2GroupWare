package org.spring.groupAir.naver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class NaverApiController {

	@Value("${navar.api.client-id}")
	String CLIENT_ID;

	@Value("${navar.api.client-secret}")
	String CLIENT_SECRET;

//	@GetMapping("/naver")
//	public String index() {
//		return "naver/index";
//	}

	//http://localhost:8095/naver/auth2
	@GetMapping("/naver/auth2")    // Redirect URL
	public String naver(String code, String state,Model model) throws Exception {

		//Access Token: 사용자 인증 정보와 사용하는 API 요청 범위(scope)를 포함한다. 일반적으로 토큰이라고 하면 Access Token을 가리키며, 비교적 유효 기간이 짧다.
		String apiURL="https://auth.worksmobile.com/oauth2/v2.0/token";
		apiURL += "?code="+code; 								// Authorization Code는 일회성이고, 유효 기간은 10분이다.
		apiURL += "&client_id="+CLIENT_ID;			  //Developer Console에서 발급받은 앱의 client ID
		apiURL += "&client_secret="+CLIENT_SECRET;//Developer Console에서 발급받은 앱의 client secret
		apiURL += "&grant_type=authorization_code";//"authorization_code"로 고정

		URL url=new URL(apiURL);

//		Access Token 발급
//		발급받은 인가 코드로 Access Token을 발급받을 수 있다. 발급받은 Access Token은 헤더에 추가해 OPEN API를 호출할 때 사용한다.
//		Request URL
//		https://auth.worksmobile.com/oauth2/v2.0/token

		HttpURLConnection con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		int responseCode=con.getResponseCode();

		String responseJSONData=null;

		if(responseCode == HttpURLConnection.HTTP_OK) {
			responseJSONData=get(con.getInputStream()); // 정상
			System.out.println("정상");
		}else {
			responseJSONData=get(con.getErrorStream());
			System.out.println("에러");
		}
		con.disconnect();

		//System.out.println(responseData); JSON 형식 문자열데이터
		ObjectMapper mapper=new ObjectMapper();
		NaverTokenDTO dto=mapper.readValue(responseJSONData, NaverTokenDTO.class);

		OrgResponse orgResponse=getOrgUnit(dto);

		model.addAttribute("list", orgResponse.getOrgUnits());

		return "naver/naver-auth2"; //View
	}


	// 조직 연동 -> 실제 API 조직 get
	private OrgResponse getOrgUnit(NaverTokenDTO dto) throws IOException {

		String apiURL="https://www.worksapis.com/v1.0/orgunits?domainId=300146699";

		URL url=new URL(apiURL);
		HttpURLConnection con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		//"Bearer {token}"
		con.setRequestProperty("Authorization", "Bearer "+dto.getAccess_token());

		int responseCode=con.getResponseCode();
		String responseJSONData=null;
		if(responseCode == HttpURLConnection.HTTP_OK) {
			responseJSONData=get(con.getInputStream()); // 값 리턴
			System.out.println(">>>정상");
		}else {
			responseJSONData=get(con.getErrorStream());
			System.out.println(">>>에러");
		}
		con.disconnect();
		System.out.println(responseJSONData);
		// JSON -> JAVA
		ObjectMapper mapper=new ObjectMapper();
		//JSON -> Java class
		OrgResponse reponse=mapper.readValue(responseJSONData, OrgResponse.class);
		return reponse;

	}

	//응답데이터를 스트림을 통해서 한줄씩읽어서 문자열로 리턴
	private String get(InputStream inputStream) throws IOException {
		InputStreamReader streamReader=new InputStreamReader(inputStream);
		BufferedReader lineReader=new BufferedReader(streamReader);

		StringBuilder responseBody=new StringBuilder();

		String data;
		while((data=lineReader.readLine()) != null) {
			responseBody.append(data);
		}

		lineReader.close();
		streamReader.close();
		return responseBody.toString();
	}

}
