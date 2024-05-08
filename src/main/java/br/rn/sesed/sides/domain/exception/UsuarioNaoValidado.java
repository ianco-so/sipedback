package br.rn.sesed.sides.domain.exception;

import br.rn.sesed.sides.exception.SidesException;

public class UsuarioNaoValidado extends SidesException {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public UsuarioNaoValidado() {
		super(String.format("O usuário não foi validado"));		
	}
	
	

}
