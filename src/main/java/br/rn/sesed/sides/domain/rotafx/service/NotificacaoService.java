package br.rn.sesed.sides.domain.rotafx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.rn.sesed.sides.domain.rotafx.model.Notificacao;
import br.rn.sesed.sides.domain.rotafx.repository.NotificacaoRepository;

@Component
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public List<Notificacao> findByEnviada(Boolean enviada){
        
        return notificacaoRepository.findByEnviada(enviada);

    }

    public void save(Notificacao notificacao){
        
        notificacaoRepository.save(notificacao);

    }


}
