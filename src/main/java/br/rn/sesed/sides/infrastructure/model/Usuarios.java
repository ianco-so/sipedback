package br.rn.sesed.sides.infrastructure.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(schema="dbo", name = "usuarios")
public class Usuarios {
	
	@NotNull
	@Column(name="st_nome")
	private String nome;
	
	@NotNull
	@Column(name="st_cpf")
	private String cpf;
	
	@NotNull
	@Column(name="st_email")
	private String email;
	
	@NotNull
	@Column(name="st_telefone")
	private String telefone;
	
	@NotNull
	@Column(name="st_senha")
	private String senha;

}
