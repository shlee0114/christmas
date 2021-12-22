CREATE TABLE USER
(
    seq        BIGINT        NOT NULL AUTO_INCREMENT,
    id         VARCHAR2(128) NOT NULL COMMENT '사용자 id',
    pw         VARCHAR2(128) NOT NULL COMMENT '사용자 pw',
    user_type  CHAR(1)       NOT NULL COMMENT '사용자 타입(0:일반 유저, 1:판매자)',
    created_at TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP     NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    deleted_at TIMESTAMP     NULL,
    use_yn     CHAR(1)       NOT NULL DEFAULT 'Y' COMMENT '사용 여부',
    PRIMARY KEY (seq),
    UNIQUE (id)
);

CREATE TABLE USER_INFO
(
    seq          BIGINT        NOT NULL AUTO_INCREMENT,
    user_id      VARCHAR2(128) NOT NULL COMMENT '사용자 id',
    name         VARCHAR2(128) NOT NULL COMMENT '사용자 이름',
    phone_number VARCHAR2(128) NOT NULL COMMENT '사용자 전화번호',
    PRIMARY KEY (seq),
    CONSTRAINT fk_user_info_user_id FOREIGN KEY (user_id) REFERENCES USER (seq)
);

CREATE TABLE SELLER_INFO
(
    seq                 BIGINT        NOT NULL AUTO_INCREMENT,
    user_id             VARCHAR2(128) NOT NULL COMMENT '사용자 id',
    name                VARCHAR2(128) NOT NULL COMMENT '스토어 이름',
    representative_name VARCHAR2(128) NOT NULL COMMENT '대표 이름',
    phone_number        VARCHAR2(128) NOT NULL COMMENT '기업 전화번호',
    crn                 CHAR(10)      NOT NULL COMMENT '사업자 등록 번호',
    email               VARCHAR2(128) NOT NULL COMMENT '기업 E-MAIL',
    address             VARCHAR2(128) NOT NULL COMMENT '주소',
    address_detail      VARCHAR2(128) NOT NULL COMMENT '주소 상세',
    PRIMARY KEY (seq),
    CONSTRAINT fk_seller_info_user_id FOREIGN KEY (user_id) REFERENCES USER (seq)
);

CREATE TABLE USER_CARD_LIST
(
    seq                 BIGINT        NOT NULL AUTO_INCREMENT,
    user_id             VARCHAR2(128) NOT NULL COMMENT '사용자 id',
    card_type           CHAR(2)       NOT NULL COMMENT '카드 사',
    card_num            CHAR(12)      NOT NULL COMMENT '카드 번호',
    card_available_date CHAR(4)       NOT NULL COMMENT '카드 유효 기간',
    card_pw             CHAR(2)       NOT NULL COMMENT '카드 비밀번호 앞 2자리',
    card_cvc            CHAR(3)       NOT NULL COMMENT '카드 CVC',
    default_yn          CHAR(1)       NOT NULL DEFAULT 'N' COMMENT '기본 카드 여부',
    created_at          TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP     NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (seq),
    CONSTRAINT fk_card_list_user_id FOREIGN KEY (user_id) REFERENCES USER (seq)
);

CREATE TABLE USER_ADDRESS_LIST
(
    seq                   BIGINT        NOT NULL AUTO_INCREMENT,
    user_id               VARCHAR2(128) NOT NULL COMMENT '사용자 id',
    address               VARCHAR2(128) NOT NULL COMMENT '주소',
    address_detail        VARCHAR2(128) NOT NULL COMMENT '주소 상세',
    receiver_name         VARCHAR2(128) NOT NULL COMMENT '수취인 이름',
    receiver_phone_number VARCHAR2(128) NOT NULL COMMENT '수취인 전화번호',
    receiver_request      VARCHAR2(300) NOT NULL COMMENT '수취인 요구사항',
    default_yn            CHAR(1)       NOT NULL DEFAULT 'N' COMMENT '기본 주소 여부',
    created_at            TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP     NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (seq),
    CONSTRAINT fk_address_list_user_id FOREIGN KEY (user_id) REFERENCES USER (seq)
);

CREATE TABLE USER_ORDER_LIST
(
    seq          BIGINT         NOT NULL AUTO_INCREMENT,
    user_id      VARCHAR2(128)  NOT NULL COMMENT '사용자 id',
    address_seq  BIGINT         NOT NULL COMMENT '주소 id',
    card_seq     BIGINT         NOT NULL COMMENT '카드 id',
    state        CHAR(1)        NOT NULL DEFAULT '0' COMMENT '주문 진행 상태(0:주문,1:접수,2:배송중,3:완료,4:거절)',
    request_msg  VARCHAR2(1000) NULL COMMENT '주문 요청 메시지',
    reject_msg   VARCHAR2(1000) NULL COMMENT '주문 거절 메시지',
    completed_at TIMESTAMP      NULL COMMENT '주문 완료 처리 일자',
    rejected_at  TIMESTAMP      NULL COMMENT '주문 거절일자',
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP      NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (seq),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES USER (seq),
    CONSTRAINT fk_address_seq FOREIGN KEY (address_seq) REFERENCES USER_ADDRESS_LIST (seq),
    CONSTRAINT fk_card_seq FOREIGN KEY (card_seq) REFERENCES USER_CARD_LIST (seq)
);