-- liquibase formatted sql

-- changeset quocpn:create-user-verification-table
CREATE TABLE user_verification (
    user_info         VARCHAR(255) NOT NULL,
    user_info_type    VARCHAR(255) NOT NULL,
    verification_code VARCHAR(255) NOT NULL,
    created_at        datetime(6)  NOT NULL,
    CONSTRAINT pk_user_verification PRIMARY KEY (user_info)
);
-- rollback DROP TABLE user_verification;
