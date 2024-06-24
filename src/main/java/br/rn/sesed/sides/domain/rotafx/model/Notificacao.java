package br.rn.sesed.sides.domain.rotafx.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity(name = "Notificacao")
@Table(name = "fila_notificacao", schema = "dbo")
@DynamicUpdate
@DynamicInsert
public class Notificacao {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "st_tipo")
    private String tipo;

    @Column(name = "st_plataforma_envio")
    private String plataformaEnvio;

    @Column(name = "st_pessoa_destino")
    private String pessoaDestino;

    @Column(name = "st_origem")
    private String origem;

    @Column(name = "st_destino")
    private String destino; 

    @Column(name = "st_titulo")
    private String titulo;

    @Column(name = "st_corpo")
    private String corpo;

    @Column(name = "bo_enviado")
    private Boolean enviada;

    @Column(name = "bo_cancelada")
    private Boolean cancelada;

    @Column(name = "dt_recebido")
    private LocalDateTime dataRecebido;

    @Column(name = "dt_enviado")
    private LocalDateTime dataEnviado;

}
