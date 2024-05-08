package br.rn.sesed.sides.domain.exception;

import br.rn.sesed.sides.exception.SidesException;

public class ErroAoDeletarUsuarioException extends SidesException{

	private static final long serialVersionUID = 1L;

	public ErroAoDeletarUsuarioException(Long id) {
		super(String.format("Não foi possivel deletar o usuario de id: %d", id));
	}	
}