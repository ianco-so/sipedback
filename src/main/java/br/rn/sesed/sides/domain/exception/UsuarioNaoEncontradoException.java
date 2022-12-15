package br.rn.sesed.sides.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String message) {
		super(message);
	}
	
	public UsuarioNaoEncontradoException(Long usuarioId) {
		super(String.format("Não existe um cadastro de usuário com código %d", usuarioId));
	}

}
