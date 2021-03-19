

CREATE TABLE accounts
(
    account_id bigint NOT NULL,
    document_number character varying(255),
    CONSTRAINT accounts_pkey PRIMARY KEY (account_id)
);

CREATE TABLE operational_types
(
    operationtype_id bigint NOT NULL,
    description0 character varying(255),
    CONSTRAINT operational_types_pkey PRIMARY KEY (operationtype_id)
);

CREATE TABLE transactions
(
    transaction_id bigint NOT NULL,
    amount numeric(19,2),
    event_date timestamp without time zone,
    account_id bigint NOT NULL,
    operationtype_id bigint NOT NULL,
    CONSTRAINT transactions_pkey PRIMARY KEY (transaction_id),
    CONSTRAINT account_fk FOREIGN KEY (account_id)
        REFERENCES accounts (account_id),
    CONSTRAINT operationtype_fk FOREIGN KEY (operationtype_id)
        REFERENCES operational_types (operationtype_id)
);

CREATE SEQUENCE hibernate_sequence
    INCREMENT 1
    START 1;

INSERT INTO operational_types(
    operationtype_id, description0)
VALUES (1, 'COMPRA A VISTA');

INSERT INTO operational_types(
    operationtype_id, description0)
VALUES (2, 'COMPRA PARCELADA');

INSERT INTO operational_types(
    operationtype_id, description0)
VALUES (3, 'SAQUE');

INSERT INTO operational_types(
    operationtype_id, description0)
VALUES (4, 'PAGAMENTO');