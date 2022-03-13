
    create table clientes (
       id  bigserial not null,
        clave varchar(16),
        codigo_cliente varchar(8),
        estatus boolean,
        fecha_actualizacion TIMESTAMP,
        fecha_creacion TIMESTAMP,
        id_persona int8,
        primary key (id)
    );

    create table cuentas (
       id  bigserial not null,
        estatus boolean,
        fecha_actualizacion TIMESTAMP,
        fecha_creacion TIMESTAMP,
        numero_cuenta varchar(8),
        saldo_inicial NUMERIC(15,2),
        tipo_cuenta varchar(1),
        id_cliente int8,
        primary key (id)
    );

    create table movimientos (
       id  bigserial not null,
        estatus boolean,
        fecha_actualizacion TIMESTAMP,
        fecha_creacion TIMESTAMP,
        fecha_transaccion TIMESTAMP,
        saldo NUMERIC(15,2),
        tipo_movimiento varchar(1),
        valor NUMERIC(15,2),
        id_cuenta int8,
        primary key (id)
    );

    create table personas (
       id  bigserial not null,
        direccion varchar(255),
        estatus boolean,
        fecha_actualizacion TIMESTAMP,
        fecha_creacion TIMESTAMP,
        fecha_nacimiento DATE,
        genero varchar(1),
        identificacion varchar(13),
        nombre varchar(128),
        telefono varchar(16),
        primary key (id)
    );

    alter table clientes 
       add constraint FKm3t59s0r9jowh1cjxwylrj0th 
       foreign key (id_persona) 
       references personas;

    alter table cuentas 
       add constraint FKl7qjco9qn0mai4bxjxee01vwr 
       foreign key (id_cliente) 
       references clientes;

    alter table movimientos 
       add constraint FKlebenvy2r7bf11m3tfi8uc9gu 
       foreign key (id_cuenta) 
       references cuentas;

    create table clientes (
       id  bigserial not null,
        clave varchar(16),
        codigo_cliente varchar(8),
        estatus boolean,
        fecha_actualizacion TIMESTAMP,
        fecha_creacion TIMESTAMP,
        id_persona int8,
        primary key (id)
    );

    create table cuentas (
       id  bigserial not null,
        estatus boolean,
        fecha_actualizacion TIMESTAMP,
        fecha_creacion TIMESTAMP,
        numero_cuenta varchar(8),
        saldo_inicial NUMERIC(15,2),
        tipo_cuenta varchar(1),
        id_cliente int8,
        primary key (id)
    );

    create table movimientos (
       id  bigserial not null,
        estatus boolean,
        fecha_actualizacion TIMESTAMP,
        fecha_creacion TIMESTAMP,
        fecha_transaccion TIMESTAMP,
        saldo NUMERIC(15,2),
        tipo_movimiento varchar(1),
        valor NUMERIC(15,2),
        id_cuenta int8,
        primary key (id)
    );

    create table personas (
       id  bigserial not null,
        direccion varchar(255),
        estatus boolean,
        fecha_actualizacion TIMESTAMP,
        fecha_creacion TIMESTAMP,
        fecha_nacimiento DATE,
        genero varchar(1),
        identificacion varchar(13),
        nombre varchar(128),
        telefono varchar(16),
        primary key (id)
    );

    alter table clientes 
       add constraint FKm3t59s0r9jowh1cjxwylrj0th 
       foreign key (id_persona) 
       references personas;

    alter table cuentas 
       add constraint FKl7qjco9qn0mai4bxjxee01vwr 
       foreign key (id_cliente) 
       references clientes;

    alter table movimientos 
       add constraint FKlebenvy2r7bf11m3tfi8uc9gu 
       foreign key (id_cuenta) 
       references cuentas;
