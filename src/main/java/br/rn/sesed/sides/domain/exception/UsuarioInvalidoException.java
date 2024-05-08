package br.rn.sesed.sides.domain.exception;

import br.rn.sesed.sides.exception.SidesException;

public class UsuarioInvalidoException extends SidesException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioInvalidoException() {
		super(String.format("O usuário não pode ser vazio ou nulo"));
		
	}

}
