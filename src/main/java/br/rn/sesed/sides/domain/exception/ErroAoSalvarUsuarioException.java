package br.rn.sesed.sides.domain.exception;

import br.rn.sesed.sides.exception.SidesException;


public class ErroAoSalvarUsuarioException extends SidesException {

	private static final long serialVersionUID = 1L;

	public ErroAoSalvarUsuarioException(String message) {
		super(String.format("Não foi possível salvar o usuario %s", message));
	}
	
}

