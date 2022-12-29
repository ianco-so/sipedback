package br.rn.sesed.sides.core.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class SecuryContext implements SecurityContext{

	private String name;
	private String nameFull;
	private String email;
	
	
	
	public SecuryContext() {
		super();
	}

	public SecuryContext(String name, String nameFull, String email) {
		super();
		this.name = name;
		this.nameFull = nameFull;
		this.email = email;
	}



	@Override
	public String getAuthenticationScheme() {
		return "Bearer";
	}

	@Override
	public Principal getUserPrincipal() {
		PrincipalContext rotafxPrincipal = new PrincipalContext(name, nameFull, email);
		return rotafxPrincipal;
	}

	@Override
	public boolean isSecure() {
		return this.isSecure();
	}

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}



	public String getNameFull() {
		return nameFull;
	}



	public void setNameFull(String nameFull) {
		this.nameFull = nameFull;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
