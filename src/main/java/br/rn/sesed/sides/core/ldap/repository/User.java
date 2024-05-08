package br.rn.sesed.sides.core.ldap.repository;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import lombok.Data;

@Data
@Entry(base = "ou=users", objectClasses = { "person", "inetOrgPerson", "top", "user" })
public class User {
        
	    @Id
	    private Name id;
	    
	    private @Attribute(name = "cn") String username;
	    private @Attribute(name = "sn") String password;

	    public User() {
	    }

	    public User(String username, String password) {
	        this.username = username;
	        this.password = password;
	    }

	    @Override
	    public String toString() {
	        return username;
	    }
}
