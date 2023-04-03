package br.rn.sesed.sides.domain.exception;

public class RegistroNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RegistroNaoEncontradoException(Long idRegistro) {
		super(String.format("Não existe um registro com id %d", idRegistro));
	}

}
