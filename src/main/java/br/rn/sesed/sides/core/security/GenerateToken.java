package br.rn.sesed.sides.core.security;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Stateless(name = "GenerateToken")
public class GenerateToken {
	
	private static final Log logger = LogFactory.getLog(GenerateToken.class);
	private static final String CHAVE_REVALIDA = "f6622e29a37c586bbde48be9d81dea52fa30340386602959491c97d69fe906c8";
	private static final Integer VALIDADE_MINUTES = 1440; 
	
	public GenerateToken() {
	}

	public Integer validadeEmMinutos() {
		return VALIDADE_MINUTES;
	}

	public String gerarRevalida(String chave) {
		
		try {
			
			Calendar calendar = Calendar.getInstance(); 
			String fator =  String.valueOf(calendar.get(Calendar.YEAR));
			fator += String.valueOf(calendar.get(Calendar.MONTH));
			fator += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			fator += String.valueOf(calendar.get(Calendar.HOUR));
			fator += String.valueOf(calendar.get(Calendar.MINUTE));
			fator += String.valueOf(calendar.get(Calendar.SECOND));
			fator += String.valueOf(calendar.get(Calendar.MILLISECOND));

			
			
			String chaveRevalida = fator + chave + CHAVE_REVALIDA;
		    MessageDigest m = MessageDigest.getInstance("SHA-256");
		    m.update( chaveRevalida.getBytes(), 0 , chaveRevalida.length() );
		    byte[] digest = m.digest();
		    String hexa = new BigInteger(1,digest).toString(16);
		    return hexa;
		} catch (NoSuchAlgorithmException e) {
			return "2104e56ef446302ca000d565b208f9f5b58b8d98585879da089b8940f50e40a0";
		}
	}

   public Claims validaToken(String jwtString) throws Exception {

    	try {
	        PublicKey publicKey = getPublicKey();
	
	        Jws<Claims> jwt = Jwts.parserBuilder()
	                .setSigningKey(publicKey)
	                .build()
	                .parseClaimsJws(jwtString);
	
	        return jwt.getBody();
    	} catch(ExpiredJwtException ex) {
    		logger.info("Token Expirado - " + ex.getMessage());
    		return null;

		}catch(Exception ex) {
    		logger.error("Tokem Inválido");
			throw ex;

		}

    }	
	
	
    public String gerarToken(String login, String cpf, String nome, String email) throws InvalidKeySpecException, NoSuchAlgorithmException {

    	// Data atual que data que o token foi gerado
    	Date agora = new Date();
    	
    	if(nome == null || nome.isEmpty()) {nome="";}
    	if(email == null || email.isEmpty()) {email="";}
    	if(cpf == null || cpf.isEmpty()) {cpf="";}
    	
//		Define até que data o token é pelo quantidade de dias que foi passo pelo
//		parâmetro expiraEmDias
		Calendar expira = Calendar.getInstance();

//		expira.add(Calendar.DAY_OF_MONTH, VALIDADE_MINUTES);
		expira.add(Calendar.MINUTE, VALIDADE_MINUTES);
    	
        PrivateKey privateKey = this.getPrivateKey();

        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("name", nome)
                .claim("cpf", cpf)
                .claim("email", email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(agora)
                .setIssuer(login)
                .setExpiration(expira.getTime())
                .signWith(privateKey)
                .compact();

        return jwtToken;
    }	
	
	
	
	
    private PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPublicKey = "-----BEGIN PUBLIC KEY-----" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyu3NB7Tr3nzETLNbZHYi" +
                "ZvgNeg3/OZUJZl40LzBzYGOD/8575eJETjfQ3QXaNyvNThu6Uf9B/V73QUxKI4/+" +
                "rwlbjA3niIga4MdDiY4b9K/KFA+HedvtZF1yE2p4smXGydPLOLBe31EgriGTob78" +
                "EE3f7SMFxlNaqn4Pm7KJkOodnMz0ilwLseeL1IkTtiFn/2OrcMpPHMtTxyDn3pQl" +
                "VCeJM5j/grDh+0YdyTMGdDHOBgM53VqSsDVyo1TNtP2yhPRYCIiI85hEHVaUnVM9" +
                "jGwCjNZLJHWh10Mrmh6B3z8BEmLhMAZXeL4fQBjBd42DLvIIJwM1USKFhjK+XghN" +
                "rQIDAQAB" +
                "-----END PUBLIC KEY-----";
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }

    private PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPrivateKey = "-----BEGIN PRIVATE KEY-----" +
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDK7c0HtOvefMRM" +
                "s1tkdiJm+A16Df85lQlmXjQvMHNgY4P/znvl4kRON9DdBdo3K81OG7pR/0H9XvdB" +
                "TEojj/6vCVuMDeeIiBrgx0OJjhv0r8oUD4d52+1kXXITaniyZcbJ08s4sF7fUSCu" +
                "IZOhvvwQTd/tIwXGU1qqfg+bsomQ6h2czPSKXAux54vUiRO2IWf/Y6twyk8cy1PH" +
                "IOfelCVUJ4kzmP+CsOH7Rh3JMwZ0Mc4GAzndWpKwNXKjVM20/bKE9FgIiIjzmEQd" +
                "VpSdUz2MbAKM1kskdaHXQyuaHoHfPwESYuEwBld4vh9AGMF3jYMu8ggnAzVRIoWG" +
                "Mr5eCE2tAgMBAAECggEBAKBPXiKRdahMzlJ9elyRyrmnihX7Cr41k7hwAS+qSetC" +
                "kpu6RjykFCvqgjCpF+tvyf/DfdybF0mPBStrlkIj1iH29YBd16QPSZR7NkprnoAd" +
                "gzl3zyGgcRhRjfXyrajZKEJ281s0Ua5/i56kXdlwY/aJXrYabcxwOvbnIXNxhqWY" +
                "NSejZn75fcacSyvaueRO6NqxmCTBG2IO4FDc/xGzsyFKIOVYS+B4o/ktUOlU3Kbf" +
                "vwtz7U5GAh9mpFF+Dkr77Kv3i2aQUonja6is7X3JlA93dPu4JDWK8jrhgdZqY9p9" +
                "Q8odbKYUaBV8Z8CnNgz2zaNQinshzwOeGfFlsd6H7SECgYEA7ScsDCL7omoXj4lV" +
                "Mt9RkWp6wQ8WDu5M+OCDrcM1/lfyta2wf7+9hv7iDb+FwQnWO3W7eFngYUTwSw5x" +
                "YP2uvOL5qbe7YntKI4Q9gHgUd4XdRJJSIdcoY9/d1pavkYwOGk7KsUrmSeoJJ2Jg" +
                "54ypVzZlVRkcHjuwiiXKvHwj2+UCgYEA2w5YvWSujExREmue0BOXtypOPgxuolZY" +
                "pS5LnuAr4rvrZakE8I4sdYjh0yLZ6qXJHzVlxW3DhTqhcrhTLhd54YDogy2IT2ff" +
                "0GzAV0kX+nz+mRhw0/u+Yw6h0QuzH9Q04Wg3T/u/K9+rG335j/RU1Tnh7nxetfGb" +
                "EwJ1oOqcXikCgYEAqBAWmxM/mL3urH36ru6r842uKJr0WuhuDAGvz7iDzxesnSvV" +
                "5PKQ8dY3hN6xfzflZoXssUGgTc55K/e0SbP93UZNAAWA+i29QKY6n4x5lKp9QFch" +
                "dXHw4baIk8Z97Xt/kw07f6FAyijdC9ggLHf2miOmdEQzNQm/9mcJ4cFn+DECgYEA" +
                "gvOepQntNr3gsUxY0jcEOWE3COzRroZD0+tLFZ0ZXx/L5ygVZeD4PwMnTNrGvvmA" +
                "tAFt54pomdqk7Tm3sBQkrmQrm0+67w0/xQ9eJE/z37CdWtQ7jt4twHXc0mVWHa70" +
                "NdPhTRVIAWhil7rFWANOO3Gw2KrMy6O1erW7sAjQlZECgYBmjXWzgasT7JcHrP72" +
                "fqrEx4cg/jQFNlqODNb515tfXSBBoAFiaxWJK3Uh/60/I6cFL/Qoner4trNDWSNo" +
                "YENBqXLZnWGfIo0vAIgniJ6OD67+1hEQtbenhSfeE8Hou2BnFOTajUxmYgGm3+hx" +
                "h8TPOvfHATdiwIm7Qu76gHhpzQ==" +
                "-----END PRIVATE KEY-----";

        rsaPrivateKey = rsaPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "");
        rsaPrivateKey = rsaPrivateKey.replace("-----END PRIVATE KEY-----", "");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        return privKey;
    }	
	
}
