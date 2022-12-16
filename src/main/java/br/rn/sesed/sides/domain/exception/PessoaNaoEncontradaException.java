package br.rn.sesed.sides.domain.exception;

public class PessoaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PessoaNaoEncontradaException(String nomePessoa) {
		super(String.format("Não existe um cadastro de usuário com nome %s", nomePessoa));
	}
	
	

}
