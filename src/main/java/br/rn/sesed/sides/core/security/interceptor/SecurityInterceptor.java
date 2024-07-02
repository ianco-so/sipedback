package br.rn.sesed.sides.core.security.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.rn.sesed.sides.core.Constants;
import br.rn.sesed.sides.core.security.GenerateToken;
import br.rn.sesed.sides.core.security.SipedAutenticationToken;
import br.rn.sesed.sides.core.security.annotation.Security;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SecurityInterceptor implements HandlerInterceptor{

    @Autowired
	private GenerateToken generateToken;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        try{
            boolean authorization = false;

            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            log.debug("URI -> {}", request.getRequestURI());
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                Security annotation = method.getAnnotation(Security.class);
                
                if(annotation == null){ //torna obrigatorio a anotacao do Security nos endpoints da API
                    throw new Exception("Annotation of security not found.");
                }else if(!annotation.enabled()){
                    log.warn("Api com annotation security disabilitada, aplicação não terá contexto do Principal substituido");
                    return true;
                }
            }
            
            return analiseCredenciais(authorizationHeader); // Analise as credencias e Continue a cadeia de interceptores 

        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Access to this resource is unauthorized.");
            log.error("Erro no Interceptor", e.getMessage());
            return false;
        }
    }

    private boolean analiseCredenciais(String authorizationHeader) throws Exception{
       try{ 
        if (StringUtils.hasText(authorizationHeader) &&	authorizationHeader.startsWith(Constants.BEARER_SCHEMA)) {
            authorizationHeader = authorizationHeader.replace(Constants.BEARER_SCHEMA, "");
            SipedAutenticationToken sipedAuthenticationToken = getAuthenticationToken(authorizationHeader);
            SecurityContextHolder.getContext().setAuthentication(sipedAuthenticationToken);
        }else{
            throw new Exception("Authentication invalid");
        }

        return true;

    }catch(Exception e){
        throw e;
    }
    }


    private SipedAutenticationToken getAuthenticationToken(String token) throws Exception {
		try {
			var generateToken = new GenerateToken();
			Claims claims = generateToken.validaToken(token);
			String cpf = claims.get(Constants.CPF_USER_CLAIM, String.class);
			Long userId = claims.get(Constants.USER_ID_CLAIM, Long.class);

			SipedAutenticationToken sipedAuthenticationToken = new SipedAutenticationToken(cpf, userId);
			return sipedAuthenticationToken;
		} catch (Exception e) {

			log.error("Problemas com o JWT: " + e.getMessage());
            throw new Exception("Authentication invalid");
		}

	}


}
