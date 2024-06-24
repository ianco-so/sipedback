package br.rn.sesed.sides.domain.rotafx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.rotafx.model.Notificacao;


@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {


    List<Notificacao> findByEnviada(Boolean enviada);


}
