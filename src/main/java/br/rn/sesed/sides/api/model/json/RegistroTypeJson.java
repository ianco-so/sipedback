package br.rn.sesed.sides.api.model.json;

import lombok.Data;

@Data
public class RegistroTypeJson {

    private RegistroTipo tipo;
    private Boolean vinculado;
    private Boolean boletim;
    private Boolean registroInstituicao;

}
