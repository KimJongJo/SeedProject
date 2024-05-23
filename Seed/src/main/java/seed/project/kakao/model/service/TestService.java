package seed.project.kakao.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import seed.project.kakao.model.dto.KakaoProfile;
import seed.project.kakao.model.dto.OAuthToken;


@Service
public class TestService {
	
	@Value("${kakao.api.key}")
	private String kakaoApiKey;
	
	@Value("${kakao.redirect.uri}")
	private String kakaoApiUri;

	// 인가 코드를 주고 토큰 발급 받아서 반환
	public String getKakaoToken(String code) {
		
		// POST방식으로 key=value 데이터를 요청(카카오쪽으로)
		String accessToken = "";
		
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", kakaoApiKey);
		params.add("redirect_uri", kakaoApiUri);
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);
		
		// Http 요청하기 - POST 방식으로 , response 변수의 응답 받음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class);
		
//		System.out.println("카카오 토큰 요청 완료 : 토큰 요청에 대한 응답 : " + response);
		
		ObjectMapper obMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		
		try {
			oauthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		accessToken = oauthToken.getAccess_token();
//		System.out.println("카카오 액세스 토큰 : " + accessToken);
		
		return accessToken;
	}

	// 토큰을 받아서 user의 정보를 반환
	public HashMap<String, Object> getUserInfo(String accessToken) {
		
		HashMap<String, Object> userInfo = new HashMap<>();
		
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + accessToken);
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest2 = 
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - POST 방식으로 , response 변수의 응답 받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoTokenRequest2,
				String.class);
		
		System.out.println("response2 ======= " + response2.getBody());
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		}catch(JsonMappingException e) {
			e.printStackTrace();
		}catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 닉네임 : " + kakaoProfile.getProperties().getNickname());
		
		userInfo.put("kakaoId", kakaoProfile.getId());
		userInfo.put("memberNickname", kakaoProfile.getProperties().getNickname());
	
		return userInfo;
	}

}
