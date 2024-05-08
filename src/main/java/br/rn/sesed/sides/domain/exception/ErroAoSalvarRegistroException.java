package br.rn.sesed.sides.domain.exception;

import br.rn.sesed.sides.exception.SidesException;


public class ErroAoSalvarRegistroException extends SidesException {

	private static final long serialVersionUID = 1L;

//	public ErroAoSalvarUsuarioException() {
//		super("Não foi possível salvar o usuario");
//	}
	
	public ErroAoSalvarRegistroException(String message) {
		super(message);
	}
	
}

