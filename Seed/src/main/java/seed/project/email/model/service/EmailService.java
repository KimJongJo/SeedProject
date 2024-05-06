package seed.project.email.model.service;

import java.util.Map;

public interface EmailService {

	/** 이메일 발송
	 * @param string
	 * @param email
	 * @return authKey
	 */
	String sendEmail(String pageName , String email);

	/**
	 * @param map
	 * @return count
	 */
	int checkAuthKey(Map<String, Object> map);

}
