create sequence anexo_categoria_SEQ start with 1 increment by 1;
create sequence anexo_dominio_SEQ start with 1 increment by 1;
create sequence anexo_SEQ start with 1 increment by 1;
create sequence anexo_sub_categoria_SEQ start with 1 increment by 1;
create sequence REVINFO_SEQ start with 1 increment by 1;
create sequence Usuario_SEQ start with 1 increment by 1;

    create table anexo (
       id_anexo integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo varchar(60) not null,
        titulo_anexo varchar(60) not null,
        id_anexo_categoria integer not null,
        id_anexo_dominio integer not null,
        id_anexo_revogado_por integer,
        id_anexo_sub_categoria integer not null,
        primary key (id_anexo)
    );

    create table anexo_AUD (
       id_anexo integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo varchar(60),
        titulo_anexo varchar(60),
        id_anexo_categoria integer,
        id_anexo_dominio integer,
        id_anexo_revogado_por integer,
        id_anexo_sub_categoria integer,
        primary key (REV, id_anexo)
    );

    create table anexo_categoria (
       id_anexo_categoria integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_categoria varchar(255) not null,
        id_anexo_dominio integer not null,
        primary key (id_anexo_categoria)
    );

    create table anexo_categoria_AUD (
       id_anexo_categoria integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_categoria varchar(255),
        primary key (REV, id_anexo_categoria)
    );

    create table anexo_dominio (
       id_anexo_dominio integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_dominio varchar(255) not null,
        primary key (id_anexo_dominio)
    );

    create table anexo_dominio_AUD (
       id_anexo_dominio integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_dominio varchar(255),
        primary key (REV, id_anexo_dominio)
    );

    create table anexo_sub_categoria (
       id_anexo_sub_categoria integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_sub_categoria varchar(255) not null,
        id_anexo_categoria integer not null,
        primary key (id_anexo_sub_categoria)
    );

    create table anexo_sub_categoria_AUD (
       id_anexo_sub_categoria integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_sub_categoria varchar(255),
        primary key (REV, id_anexo_sub_categoria)
    );

    create table REVINFO (
       REV integer not null,
        REVTSTMP bigint,
        primary key (REV)
    );

    create table Usuario (
       idUsuario integer not null,
        usuario_usuario varchar(80) not null,
        primary key (idUsuario)
    );

    alter table if exists anexo 
       add constraint UK_s0odhmsj7p84d0uab1bslcpl1 unique (nome_anexo);

    alter table if exists anexo_categoria 
       add constraint UK_inenag417tkrhxmnljtilkrcy unique (nome_anexo_categoria);

    alter table if exists anexo_dominio 
       add constraint UK_hgc6dxs3w8quok4atgmrinf6l unique (nome_anexo_dominio);

    alter table if exists anexo_sub_categoria 
       add constraint UK_mskkl5apu3avsm5igdnj2713u unique (nome_anexo_sub_categoria);

    alter table if exists Usuario 
       add constraint UK_hddbu3wo6tdmvjkcd33ov7dad unique (usuario_usuario);

    alter table if exists anexo 
       add constraint FKeujithbq1g74w0p71btoiktkf 
       foreign key (id_anexo_categoria) 
       references anexo_categoria;

    alter table if exists anexo 
       add constraint FKin8n5uync6ibkxy3nnokhngpy 
       foreign key (id_anexo_dominio) 
       references anexo_dominio;

    alter table if exists anexo 
       add constraint FK8q6a1pdyx29r8ugou98fhhahx 
       foreign key (id_anexo_revogado_por) 
       references anexo;

    alter table if exists anexo 
       add constraint FK86eejs7nnn595be7uk7d93n92 
       foreign key (id_anexo_sub_categoria) 
       references anexo_sub_categoria;

    alter table if exists anexo_AUD 
       add constraint FKkkar9q0rmb7gj1vatkrd5cpl8 
       foreign key (REV) 
       references REVINFO;

    alter table if exists anexo_categoria 
       add constraint FKnq7limdl05l2togfjwai99tp7 
       foreign key (id_anexo_dominio) 
       references anexo_dominio;

    alter table if exists anexo_categoria_AUD 
       add constraint FKo2dboe9l3v037deb0ad02xn0n 
       foreign key (REV) 
       references REVINFO;

    alter table if exists anexo_dominio_AUD 
       add constraint FKopx5ajlcv87jrc5lxp51wsexx 
       foreign key (REV) 
       references REVINFO;

    alter table if exists anexo_sub_categoria 
       add constraint FKno7qydkc95sm12h4pvc4g2i3s 
       foreign key (id_anexo_categoria) 
       references anexo_categoria;

    alter table if exists anexo_sub_categoria_AUD 
       add constraint FK6vr66h3dgplwhq9pqe8sph9ff 
       foreign key (REV) 
       references REVINFO;
create sequence anexo_categoria_SEQ start with 1 increment by 50;
create sequence anexo_dominio_SEQ start with 1 increment by 50;
create sequence anexo_SEQ start with 1 increment by 50;
create sequence anexo_sub_categoria_SEQ start with 1 increment by 50;
create sequence REVINFO_SEQ start with 1 increment by 50;
create sequence Usuario_SEQ start with 1 increment by 50;

    create table anexo (
       id_anexo integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo varchar(60) not null,
        titulo_anexo varchar(60) not null,
        id_anexo_categoria integer not null,
        id_anexo_dominio integer not null,
        id_anexo_revogado_por integer,
        id_anexo_sub_categoria integer not null,
        primary key (id_anexo)
    );

    create table anexo_AUD (
       id_anexo integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo varchar(60),
        titulo_anexo varchar(60),
        id_anexo_categoria integer,
        id_anexo_dominio integer,
        id_anexo_revogado_por integer,
        id_anexo_sub_categoria integer,
        primary key (REV, id_anexo)
    );

    create table anexo_categoria (
       id_anexo_categoria integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_categoria varchar(255) not null,
        id_anexo_dominio integer not null,
        primary key (id_anexo_categoria)
    );

    create table anexo_categoria_AUD (
       id_anexo_categoria integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_categoria varchar(255),
        primary key (REV, id_anexo_categoria)
    );

    create table anexo_dominio (
       id_anexo_dominio integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_dominio varchar(255) not null,
        primary key (id_anexo_dominio)
    );

    create table anexo_dominio_AUD (
       id_anexo_dominio integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_dominio varchar(255),
        primary key (REV, id_anexo_dominio)
    );

    create table anexo_sub_categoria (
       id_anexo_sub_categoria integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_sub_categoria varchar(255) not null,
        id_anexo_categoria integer not null,
        primary key (id_anexo_sub_categoria)
    );

    create table anexo_sub_categoria_AUD (
       id_anexo_sub_categoria integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_sub_categoria varchar(255),
        primary key (REV, id_anexo_sub_categoria)
    );

    create table REVINFO (
       REV integer not null,
        REVTSTMP bigint,
        primary key (REV)
    );

    create table Usuario (
       idUsuario integer not null,
        usuario_usuario varchar(80) not null,
        primary key (idUsuario)
    );

    alter table if exists anexo 
       add constraint UK_s0odhmsj7p84d0uab1bslcpl1 unique (nome_anexo);

    alter table if exists anexo_categoria 
       add constraint UK_inenag417tkrhxmnljtilkrcy unique (nome_anexo_categoria);

    alter table if exists anexo_dominio 
       add constraint UK_hgc6dxs3w8quok4atgmrinf6l unique (nome_anexo_dominio);

    alter table if exists anexo_sub_categoria 
       add constraint UK_mskkl5apu3avsm5igdnj2713u unique (nome_anexo_sub_categoria);

    alter table if exists Usuario 
       add constraint UK_hddbu3wo6tdmvjkcd33ov7dad unique (usuario_usuario);

    alter table if exists anexo 
       add constraint FKeujithbq1g74w0p71btoiktkf 
       foreign key (id_anexo_categoria) 
       references anexo_categoria;

    alter table if exists anexo 
       add constraint FKin8n5uync6ibkxy3nnokhngpy 
       foreign key (id_anexo_dominio) 
       references anexo_dominio;

    alter table if exists anexo 
       add constraint FK8q6a1pdyx29r8ugou98fhhahx 
       foreign key (id_anexo_revogado_por) 
       references anexo;

    alter table if exists anexo 
       add constraint FK86eejs7nnn595be7uk7d93n92 
       foreign key (id_anexo_sub_categoria) 
       references anexo_sub_categoria;

    alter table if exists anexo_AUD 
       add constraint FKkkar9q0rmb7gj1vatkrd5cpl8 
       foreign key (REV) 
       references REVINFO;

    alter table if exists anexo_categoria 
       add constraint FKnq7limdl05l2togfjwai99tp7 
       foreign key (id_anexo_dominio) 
       references anexo_dominio;

    alter table if exists anexo_categoria_AUD 
       add constraint FKo2dboe9l3v037deb0ad02xn0n 
       foreign key (REV) 
       references REVINFO;

    alter table if exists anexo_dominio_AUD 
       add constraint FKopx5ajlcv87jrc5lxp51wsexx 
       foreign key (REV) 
       references REVINFO;

    alter table if exists anexo_sub_categoria 
       add constraint FKno7qydkc95sm12h4pvc4g2i3s 
       foreign key (id_anexo_categoria) 
       references anexo_categoria;

    alter table if exists anexo_sub_categoria_AUD 
       add constraint FK6vr66h3dgplwhq9pqe8sph9ff 
       foreign key (REV) 
       references REVINFO;
create sequence anexo_categoria_SEQ start with 1 increment by 50;
create sequence anexo_dominio_SEQ start with 1 increment by 50;
create sequence anexo_SEQ start with 1 increment by 50;
create sequence anexo_sub_categoria_SEQ start with 1 increment by 50;
create sequence REVINFO_SEQ start with 1 increment by 50;
create sequence Usuario_SEQ start with 1 increment by 50;

    create table anexo (
       id_anexo integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo varchar(60) not null,
        titulo_anexo varchar(60) not null,
        id_anexo_categoria integer not null,
        id_anexo_dominio integer not null,
        id_anexo_revogado_por integer,
        id_anexo_sub_categoria integer not null,
        primary key (id_anexo)
    );

    create table anexo_AUD (
       id_anexo integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo varchar(60),
        titulo_anexo varchar(60),
        id_anexo_categoria integer,
        id_anexo_dominio integer,
        id_anexo_revogado_por integer,
        id_anexo_sub_categoria integer,
        primary key (REV, id_anexo)
    );

    create table anexo_categoria (
       id_anexo_categoria integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_categoria varchar(255) not null,
        id_anexo_dominio integer not null,
        primary key (id_anexo_categoria)
    );

    create table anexo_categoria_AUD (
       id_anexo_categoria integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_categoria varchar(255),
        primary key (REV, id_anexo_categoria)
    );

    create table anexo_dominio (
       id_anexo_dominio integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_dominio varchar(255) not null,
        primary key (id_anexo_dominio)
    );

    create table anexo_dominio_AUD (
       id_anexo_dominio integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_dominio varchar(255),
        primary key (REV, id_anexo_dominio)
    );

    create table anexo_sub_categoria (
       id_anexo_sub_categoria integer not null,
        ativo boolean default TRUE not null,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_sub_categoria varchar(255) not null,
        id_anexo_categoria integer not null,
        primary key (id_anexo_sub_categoria)
    );

    create table anexo_sub_categoria_AUD (
       id_anexo_sub_categoria integer not null,
        REV integer not null,
        REVTYPE smallint,
        ativo boolean,
        created_by varchar(255),
        created_date timestamp(6),
        last_modified_by varchar(255),
        last_modified_date timestamp(6),
        nome_anexo_sub_categoria varchar(255),
        primary key (REV, id_anexo_sub_categoria)
    );

    create table REVINFO (
       REV integer not null,
        REVTSTMP bigint,
        primary key (REV)
    );

    create table Usuario (
       idUsuario integer not null,
        usuario_usuario varchar(80) not null,
        primary key (idUsuario)
    );

    alter table if exists anexo 
       add constraint UK_s0odhmsj7p84d0uab1bslcpl1 unique (nome_anexo);

    alter table if exists anexo_categoria 
       add constraint UK_inenag417tkrhxmnljtilkrcy unique (nome_anexo_categoria);

    alter table if exists anexo_dominio 
       add constraint UK_hgc6dxs3w8quok4atgmrinf6l unique (nome_anexo_dominio);

    alter table if exists anexo_sub_categoria 
       add constraint UK_mskkl5apu3avsm5igdnj2713u unique (nome_anexo_sub_categoria);

    alter table if exists Usuario 
       add constraint UK_hddbu3wo6tdmvjkcd33ov7dad unique (usuario_usuario);

    alter table if exists anexo 
       add constraint FKeujithbq1g74w0p71btoiktkf 
       foreign key (id_anexo_categoria) 
       references anexo_categoria;

    alter table if exists anexo 
       add constraint FKin8n5uync6ibkxy3nnokhngpy 
       foreign key (id_anexo_dominio) 
       references anexo_dominio;

    alter table if exists anexo 
       add constraint FK8q6a1pdyx29r8ugou98fhhahx 
       foreign key (id_anexo_revogado_por) 
       references anexo;

    alter table if exists anexo 
       add constraint FK86eejs7nnn595be7uk7d93n92 
       foreign key (id_anexo_sub_categoria) 
       references anexo_sub_categoria;

    alter table if exists anexo_AUD 
       add constraint FKkkar9q0rmb7gj1vatkrd5cpl8 
       foreign key (REV) 
       references REVINFO;

    alter table if exists anexo_categoria 
       add constraint FKnq7limdl05l2togfjwai99tp7 
       foreign key (id_anexo_dominio) 
       references anexo_dominio;

    alter table if exists anexo_categoria_AUD 
       add constraint FKo2dboe9l3v037deb0ad02xn0n 
       foreign key (REV) 
       references REVINFO;

    alter table if exists anexo_dominio_AUD 
       add constraint FKopx5ajlcv87jrc5lxp51wsexx 
       foreign key (REV) 
       references REVINFO;

    alter table if exists anexo_sub_categoria 
       add constraint FKno7qydkc95sm12h4pvc4g2i3s 
       foreign key (id_anexo_categoria) 
       references anexo_categoria;

    alter table if exists anexo_sub_categoria_AUD 
       add constraint FK6vr66h3dgplwhq9pqe8sph9ff 
       foreign key (REV) 
       references REVINFO;
