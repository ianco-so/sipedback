package br.rn.sesed.sides.core.security;

import java.security.Principal;

public class PrincipalContext implements Principal{

	private String name;
	private String nameFull;
	private String email;
	
	
	
	public PrincipalContext(String name, String nameFull, String email) {
		super();
		this.name = name;
		this.nameFull = nameFull;
		this.email = email;
	}

	@Override
	public String getName() {
		return name;
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

	public void setName(String name) {
		this.name = name;
	}
	
	

}
