CREATE SEQUENCE lance_seq
  MINVALUE 1
  MAXVALUE 9999999999999999999999999999
  INCREMENT BY 1
  NOCYCLE
  NOORDER
  NOCACHE
;
CREATE SEQUENCE leilao_arrematado_seq
  MINVALUE 1
  MAXVALUE 9999999999999999999999999999
  INCREMENT BY 1
  NOCYCLE
  NOORDER
  NOCACHE
;
CREATE SEQUENCE leilao_seq
  MINVALUE 1
  MAXVALUE 9999999999999999999999999999
  INCREMENT BY 1
  NOCYCLE
  NOORDER
  NOCACHE
;

CREATE SEQUENCE produto_seq
  MINVALUE 1
  MAXVALUE 9999999999999999999999999999
  INCREMENT BY 1
  NOCYCLE
  NOORDER
  NOCACHE
;

CREATE SEQUENCE unidade_seq
  MINVALUE 1
  MAXVALUE 9999999999999999999999999999
  INCREMENT BY 1
  NOCYCLE
  NOORDER
  NOCACHE
 ;

 CREATE SEQUENCE usuario_seq
  MINVALUE 1
  MAXVALUE 9999999999999999999999999999
  INCREMENT BY 1
  NOCYCLE
  NOORDER
  NOCACHE
;

CREATE TABLE unidade (
  unidade_id   NUMBER(3,0)  NOT NULL,
  nome_unidade VARCHAR2(50) NOT NULL,
  situacao     NUMBER(1,0)  NULL,
   CONSTRAINT pk_id_unidade PRIMARY KEY (unidade_id)
);

CREATE TABLE tipo_usuario (
  id_tipo NUMBER(1,0) NOT NULL,
  tipo    VARCHAR2(5) NULL,
  CONSTRAINT pk_id_tipo PRIMARY KEY (id_tipo)
);

CREATE TABLE usuario (
  id_usuario NUMBER(5,0)  NOT NULL,
  nome       VARCHAR2(30) NULL,
  sobrenome  VARCHAR2(30) NULL,
  email      VARCHAR2(60) NULL,
  net_id     VARCHAR2(6)  NULL,
  cpf        VARCHAR2(14) NULL,
  senha      VARCHAR2(50) NULL,
  id_tipo    NUMBER(1,0)  NULL,
  situacao   NUMBER(1,0)  NULL,
  unidade_id NUMBER(3,0)  NULL,
  CONSTRAINT id_usuario_pk PRIMARY KEY (id_usuario),
  CONSTRAINT tipo_id_fk FOREIGN KEY (id_tipo) REFERENCES tipo_usuario (id_tipo),
  CONSTRAINT unidade_id_fk FOREIGN KEY (unidade_id) REFERENCES unidade (unidade_id)
);

CREATE TABLE produto (
  id_produto      NUMBER(10,0)   NOT NULL,
  nome_produto    VARCHAR2(50)   NULL,
  descricao       NVARCHAR2(500) NOT NULL,
  cod_patrimonio  VARCHAR2(100)  NULL,
  unidade_id      NUMBER(3,0)    NULL,
  valor_inicial   NUMBER(5,0)    NULL,
  valor_por_lance NUMBER(5,0)    NULL,
  situacao        NUMBER(1,0)    NULL,
  imagem_path     VARCHAR2(600)  NULL,
  CONSTRAINT pk_idproduto PRIMARY KEY (id_produto),
  CONSTRAINT fk_unidade_id_prod FOREIGN KEY (unidade_id) REFERENCES unidade (unidade_id)
);

CREATE TABLE parametros (
  id_parametro        NUMBER(1,0) NOT NULL,
  tempo_acrescimo     NUMBER(3,0) NULL,
  leiloes_simultaneos NUMBER(2,0) NULL,
  CONSTRAINT pk_id_parametro PRIMARY KEY (id_parametro)
);

CREATE TABLE leilao (
  id_leilao       NUMBER(5,0)  NOT NULL,
  id_produto      NUMBER(5,0)  NULL,
  data_inicio     TIMESTAMP(6) NULL,
  duracao         NUMBER(15,0) NULL,
  arrematado      NUMBER(1,0)  NULL,
  data_fim_leilao TIMESTAMP(6) NULL,
  valor_atual     NUMBER(5,0)  NULL,
  id_usuario      NUMBER(5,0)  NULL,
  CONSTRAINT pk_idleilao PRIMARY KEY (id_leilao),
  CONSTRAINT fk_idpruduto FOREIGN KEY (id_produto) REFERENCES produto (id_produto),
  CONSTRAINT fk_id_usuario_lei FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);

CREATE TABLE lance (
  id_lance    NUMBER(10,0) NOT NULL,
  id_leilao   NUMBER(5,0)  NULL,
  id_usuario  NUMBER(5,0)  NULL,
  valor_lance NUMBER(5,0)  NULL,
  CONSTRAINT pk_id_lance PRIMARY KEY (id_lance),
  CONSTRAINT fk_id_leilao FOREIGN KEY (id_leilao) REFERENCES leilao (id_leilao),
  CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);

CREATE TABLE leilao_arrematado (
  id_leilao_arrematado NUMBER(5,0)  NOT NULL,
  id_leilao            NUMBER(5,0)  NOT NULL,
  netid_ganhador       VARCHAR2(6)  NULL,
  nome_ganhador        VARCHAR2(50) NULL,
  unidade_ganhador     VARCHAR2(50) NULL,
  nome_produto         VARCHAR2(50) NULL,
  unidade_produto      VARCHAR2(50) NULL,
  n_patrimonio_produto VARCHAR2(50) NULL,
  valor_arrematado     NUMBER(5,0)  NOT NULL,
  data_arremate        TIMESTAMP(6) NULL,
  CONSTRAINT id_leilao_arrematado_pk PRIMARY KEY (id_leilao_arrematado),
  CONSTRAINT id_leilao_fk FOREIGN KEY (id_leilao) REFERENCES leilao (id_leilao)
);

INSERT INTO unidade VALUES(unidade.NEXTVAL,'Alphaville', 1);

CREATE OR REPLACE TRIGGER leilao_arrematado
AFTER INSERT ON leilao_arrematado
DECLARE
A LEILAO_ARREMATADO.ID_LEILAO%TYPE;
BEGIN
    SELECT id_leilao INTO A
      FROM leilao_arrematado
     WHERE id_leilao_arrematado = (SELECT Max(id_leilao_arrematado)
                                     FROM LEILAO_arrematado);

    UPDATE LEILAO_ARREMATADO
    SET NetId_ganhador = (SELECT net_id FROM usuario WHERE id_usuario =(SELECT ID_USUARIO FROM LANCE WHERE ID_LANCE = (SELECT Max(ID_LANCE) FROM LANCE WHERE ID_LEILAO = A))),
        nome_ganhador =   (SELECT nome FROM usuario WHERE id_usuario =(SELECT ID_USUARIO FROM LANCE WHERE ID_LANCE = (SELECT Max(ID_LANCE) FROM LANCE WHERE ID_LEILAO = A))),
        unidade_ganhador = (SELECT Nome_unidade FROM unidade WHERE unidade_id = (SELECT unidade_id FROM usuario WHERE id_usuario =(SELECT ID_USUARIO FROM LANCE WHERE ID_LANCE = (SELECT Max(ID_LANCE) FROM LANCE WHERE ID_LEILAO = A)))),
        Nome_produto = (SELECT nome_produto FROM produto WHERE id_produto = (SELECT id_produto FROM leilao WHERE id_leilao = A)),
        unidade_produto = (SELECT Nome_unidade FROM unidade WHERE unidade_id =(SELECT unidade_id FROM produto WHERE id_produto = (SELECT id_produto FROM leilao WHERE id_leilao = A))),
        N_patrimonio_produto = (SELECT cod_patrimonio FROM produto WHERE id_produto = (SELECT id_produto FROM leilao WHERE id_leilao = A)),
        valor_arrematado = 	(SELECT valor_atual FROM leilao WHERE id_leilao = A),
        Data_arremate = (SELECT data_fim_leilao FROM leilao WHERE id_leilao = A)
    WHERE ID_LEILAO = A;

END LEILAO_ARREMATADO;




