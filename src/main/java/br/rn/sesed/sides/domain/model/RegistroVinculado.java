package br.rn.sesed.sides.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "RegistroVinculado")
@Table(name = "registros_vinculados", schema = "dbo")
@DynamicUpdate
@DynamicInsert
public class RegistroVinculado {
    @EqualsAndHashCode.Include

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "dt_vinculacao")
    private LocalDateTime dataVinculacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_registro_boletim")
    private Registro registroBoletim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_registro_instituicao")
    private Registro registroInstituicao;

}
