package br.rn.sesed.sides.api.exception;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;

import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.ErroAoConectarFtpException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarFtpException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarRegistroException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,	WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErroExceptionType erroExceptionType = ErroExceptionType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		ErroException erroException = createProblemBuilder(status, erroExceptionType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, erroException, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ErroAoSalvarUsuarioException.class)
	public ResponseEntity<?> handleErroAoSalvarUsuario(ErroAoSalvarUsuarioException ex,	WebRequest request) {
		
		HttpStatus status = HttpStatus.FORBIDDEN;
		ErroExceptionType erroExceptionType = ErroExceptionType.ENTRADA_DUPLICADA;
		String detail = ex.getMessage();
		
		ErroException erroException = createProblemBuilder(status, erroExceptionType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, erroException, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ErroAoSalvarRegistroException.class)
	public ResponseEntity<?> handleErroAoSalvarRegistro(ErroAoSalvarRegistroException ex,	WebRequest request) {
		
		HttpStatus status = HttpStatus.FORBIDDEN;
		ErroExceptionType erroExceptionType = ErroExceptionType.DADOS_INVALIDOS;
		String detail = ex.getMessage();
		
		ErroException erroException = createProblemBuilder(status, erroExceptionType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, erroException, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ErroAoSalvarFtpException.class)
	public ResponseEntity<?> handleErroAoSalvarFtp(ErroAoSalvarFtpException ex,	WebRequest request) {
		
		HttpStatus status = HttpStatus.FORBIDDEN;
		ErroExceptionType erroExceptionType = ErroExceptionType.FALHA_FTP;
		String detail = ex.getMessage();
		
		ErroException erroException = createProblemBuilder(status, erroExceptionType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, erroException, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ErroAoConectarFtpException.class)
	public ResponseEntity<?> handleErroAoCOnectarFtp(ErroAoConectarFtpException ex,	WebRequest request) {
		
		HttpStatus status = HttpStatus.FORBIDDEN;
		ErroExceptionType erroExceptionType = ErroExceptionType.FALHA_FTP;
		String detail = ex.getMessage();
		
		ErroException erroException = createProblemBuilder(status, erroExceptionType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, erroException, new HttpHeaders(), status, request);
	}

	
	private ErroException.ErroExceptionBuilder createProblemBuilder(HttpStatus status,
			ErroExceptionType erroExceptionType, String detail) {
		
		return ErroException.builder()
			.timestamp(OffsetDateTime.now())
			.status(status.value())
			.type(erroExceptionType.getUri())
			.title(erroExceptionType.getTitle())
			.detail(detail);
	}

	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}
	
}
