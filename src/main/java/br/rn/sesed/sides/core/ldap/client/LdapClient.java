package br.rn.sesed.sides.core.ldap.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;

public class LdapClient {
	@Autowired
	private Environment env;

	@Autowired
	private ContextSource contextSource;

	@Autowired
	private LdapTemplate ldapTemplate;

	public void authenticate(final String username, final String password) {
		contextSource.getContext("cn=" + username + ",ou=users," + env.getRequiredProperty("ldap.partitionSuffix"),
				password);
	}

	public List<String> search(final String username) {
		return ldapTemplate.search("ou=users", "cn=" + username,
				(AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
	}

}
