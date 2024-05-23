package br.rn.sesed.sides.api.model.json;

public enum RegistroTipo {

    BOLETIM(1L), INSTITUICAO(2L);

    private Long value;

    RegistroTipo(Long i) {
        value = i;
    }

    public Long getValue(){
        return value;
    }

}
