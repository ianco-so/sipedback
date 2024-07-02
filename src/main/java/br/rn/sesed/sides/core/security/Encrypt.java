package br.rn.sesed.sides.core.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Encrypt {

	private final static String KEY_VALIDATION = "dc98d678b65ac489bb1542713a3151f6c95462a47d51472b3873fa2edabecd104f84066b2db7637e4326622cfc8b7eabdc0cc5cae9e3d10eb450a1dc06563de1";

	public static String getMD5(String str) {
		
		String hashMD5 = "";

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(str.getBytes()));
		hashMD5 = hash.toString(16);
		
		return hashMD5;
	}
	
	public static String generateCommonLangPassword() {
	    String upperCaseLetters = RandomStringUtils.random(4, 65, 90, true, true);
//	    String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
	    String numbers = RandomStringUtils.randomNumeric(2);
//	    String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
//	    String totalChars = RandomStringUtils.randomAlphanumeric(2);
	    String combinedChars = upperCaseLetters
//	      .concat(lowerCaseLetters)
//	      .concat(totalChars)		
//	      .concat(specialChar)
	      .concat(numbers);
	      
	    List<Character> pwdChars = combinedChars.chars()
	      .mapToObj(c -> (char) c)
	      .collect(Collectors.toList());
	    Collections.shuffle(pwdChars);
	    String password = pwdChars.stream()
	      .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
	      .toString();
	    return password;
	}
	
	public static String generateHash(String str) throws NoSuchAlgorithmException {
        String payload = str.concat(KEY_VALIDATION);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digest = md.digest(payload.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }

		log.debug(sb.toString());
        return sb.toString();
	}
}
