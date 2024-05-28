package seed.project.google.model;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleService {
	
	@Value("${google.api.client.id}")
	private String client_id;
	
	@Value("${google.api.secret}")
	private String secret;
	
	@Value("${google.redirect.uri}")
	private String redirect_uri;

	public String getGoogleToken(String code) {
		
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", client_id);
		params.add("client_secret", secret);
		params.add("code", code);
		params.add("redirect_uri", redirect_uri);
		
		HttpEntity<MultiValueMap<String, String>> googleTokenRequest = 
				new HttpEntity<>(params, headers);
		
		
        ResponseEntity<Map<String, Object>> response = rt.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                googleTokenRequest,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseBody = response.getBody();
        System.out.println("토큰 : " + responseBody);
		
		return (String)responseBody.get("access_token");
	}

	
	
	public Map<String, Object> getUserInfo(String googleToken) {
		
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders header2 = new HttpHeaders();
		header2.add("Authorization", "Bearer " + googleToken);
		
		
		HttpEntity<MultiValueMap<String, String>> googleUserRequest = 
				new HttpEntity<>(header2);
		
		 ResponseEntity<Map<String, Object>> googleUserInfo = rt.exchange(
				"https://www.googleapis.com/oauth2/v2/userinfo",
				HttpMethod.GET,
				googleUserRequest,
				new ParameterizedTypeReference<Map<String, Object>>() {}
				);
		 
		 Map<String, Object> responseBody = googleUserInfo.getBody();
		
		return responseBody;
	}

}
