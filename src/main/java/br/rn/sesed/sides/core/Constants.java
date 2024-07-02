package br.rn.sesed.sides.core;

public class Constants {
    public static final String SECUTRITY_FILTER_NAME = "filtroSecurity";
    public static final String AUTHENTICATION_FILTER_NAME = "filtroAutenticacao";
    // public static final String TENANT_PARAMETER_NAME = "tenantId";
    // public static final String TENANT_COLUMN = "id_tenancy";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_SCHEMA = "Bearer ";

    //Claims do JWT
    public static final String USER_ID_CLAIM = "usuarioid";
    public static final String CPF_USER_CLAIM = "cpf";

    //validade do JWT (em horas)
    public static final Integer VALIDADE_HOURS = 24;

    // public static final int SUPER_ADMIN_ROLE_ID = 0;
    // public static final String SUPER_ADMIN_ROLE_NAME = "ROLE_SUPER_ADMIN";
    // public static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";
    // public static final String USER_ROLE_NAME = "ROLE_USER";

    // public static final int SUPER_TENANT_ADMINISTRATOR_ID = 0;
}
