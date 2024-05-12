package br.rn.sesed.sides.core.ldap;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.api.serialization.UsuarioDtoConvert;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.repository.UsuarioRepository;
import br.sesed.authentication.security.ActiveDirectory;
import br.sesed.authentication.security.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LdapService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
	private UsuarioDtoConvert usuarioDtoConvert;



    public UsuarioDto login(String orgao, String cpf, String senha, String orgaoDomain) throws Exception {
        User ldapUser = null;

        
        try {
            LdapContext context = ActiveDirectory.getConnection(cpf, senha, orgao, orgaoDomain);

            ldapUser = ActiveDirectory.getUser(cpf, context);

            Optional<Usuario> op = usuarioRepository.findByCpf(ldapUser.getSamaccountname());
            Usuario usuario = op.get();
           
            var usuarioDto = usuarioDtoConvert.toDto(usuario);

            return  usuarioDto;

        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new Exception("Usuario não cadastrado");
        } catch (CommunicationException e) {
            log.error(e.getMessage());
            throw new Exception("LDAP indisponível");
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            throw new Exception("Usuario não localizado");
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }

    }

}
