package br.rn.sesed.sides.core.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class SipedAutenticationToken extends AbstractAuthenticationToken {
    
    private static final long serialVersionUID = 4650802934486496135L;
    private Object principal;
    private Long idUsuario;

    public SipedAutenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public SipedAutenticationToken(Object principal, Long id_usuario) {
        super(null);
        this.principal = principal;
        this.idUsuario = id_usuario;
        setAuthenticated(true); 
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
