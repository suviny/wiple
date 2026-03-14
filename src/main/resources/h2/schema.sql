
-- 사용자 정보 테이블
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users (
    id                      INT                 NOT NULL auto_increment,    -- 사용자 식별 번호
    email                   VARCHAR(45)         NOT NULL UNIQUE,            -- 로그인 이메일
    nickname                VARCHAR(20)         NOT NULL UNIQUE,            -- 닉네임
    name                    VARCHAR(15)         NOT NULL,                   -- 사용자 이름
    auth_type               VARCHAR(5)          NOT NULL,                   -- 계정 인증 유형
    is_enabled              TINYINT(1)          NOT NULL,                   -- 계정 활성화 여부
    is_locked               TINYINT(1)          NOT NULL,                   -- 계정 잠금 여부
    deleted_yn              CHAR(1)             NOT NULL,                   -- 사용자 정보 논리적 삭제 여부
    created_at              DATETIME            NOT NULL DEFAULT now(),     -- 사용자 정보 등록 일시
    updated_at              DATETIME            NOT NULL,                   -- 사용자 정보 변경 일시
    deleted_at              DATETIME            NULL,                       -- 사용자 정보 논리적 삭제 일시
    PRIMARY KEY (id)
);

-- 사용자 인증 비밀번호 정보
DROP TABLE IF EXISTS credentials CASCADE;
CREATE TABLE IF NOT EXISTS credentials (
    user_id                 INT                 NOT NULL,   -- 사용자 식별 번호
    password                VARCHAR(255)        NOT NULL,   -- 로그인 비밀번호
    updated_at              DATETIME            NOT NULL,   -- 비밀번호 변경 일시
    PRIMARY KEY (user_id),
    CONSTRAINT fk_credentials_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 사용자별 권한
DROP TABLE IF EXISTS user_roles CASCADE;
CREATE TABLE IF NOT EXISTS user_roles (
    id                      INT                 NOT NULL auto_increment,    -- 사용자 권한 식별 번호
    user_id                 INT                 NOT NULL,                   -- 사용자 식별 번호
    role                    VARCHAR(25)         NOT NULL,                   -- 권한 명 (ROLE_*)
    used_yn                 CHAR(1)             NOT NULL DEFAULT 'Y',       -- 권한 사용 여부
    created_at              DATETIME            NOT NULL DEFAULT now(),     -- 권한 등록 일시
    updated_at              DATETIME            NOT NULL,                   -- 권한 변경 일시
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_roles (user_id, role),
    CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 프로필 정보
DROP TABLE IF EXISTS profiles CASCADE;
CREATE TABLE IF NOT EXISTS profiles (
    id                      INT                 NOT NULL auto_increment,    -- 프로필 식별 번호
    user_id                 INT                 NOT NULL,                   -- 사용자 식별 번호
    profile_img_url         VARCHAR(255)        NULL,                       -- 프로필 이미지 저장 경로
    bio                     VARCHAR(200)        NULL,                       -- 한 줄 소개
    univ                    VARCHAR(55)         NULL,                       -- 대학 명
    site_url                VARCHAR(255)        NULL,                       -- 개인 사이트 주소
    github_url              VARCHAR(255)        NULL,                       -- 개인 깃허브 주소
    updated_at              DATETIME            NOT NULL,                   -- 프로필 정보 변경 일시
    PRIMARY KEY (id),
    CONSTRAINT fk_profiles_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 접속 이력
DROP TABLE IF EXISTS access_histories CASCADE;
CREATE TABLE IF NOT EXISTS access_histories (
    id                      INT                 NOT NULL auto_increment,    -- 이력 식별 번호
    user_id                 INT                 NOT NULL,                   -- 사용자 식별 번호
    ip_address              VARCHAR(45)         NOT NULL,                   -- 접속 아이피 주소
    device                  VARCHAR(10)         NOT NULL,                   -- 접속 기기 유형
    os                      VARCHAR(20)         NOT NULL,                   -- 접속 운영체제
    browser                 VARCHAR(20)         NOT NULL,                   -- 접속 브라우저 정보
    access_type             CHAR(1)             NOT NULL,                   -- 접속 유형 (I/O)
    login_at                DATETIME            NOT NULL,                   -- 로그인 일시
    logout_at               DATETIME            NULL,                       -- 로그아웃 일시
    PRIMARY KEY (id),
    CONSTRAINT fk_access_histories_user_id FOREIGN KEY (id) REFERENCES users (id)
);
