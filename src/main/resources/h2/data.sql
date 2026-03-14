
-- 로그인 기능 점검을 위한 데이터 삽입
INSERT INTO users (
    id,
    email,
    nickname,
    name,
    auth_type,
    is_enabled,
    is_locked,
    deleted_yn,
    created_at,
    updated_at
) VALUES (
    1,
    'viny3230@gmail.com',
    'suviny',
    '박수빈',
    'LOCAL',
    1,
    0,
    'N',
    now(),
    now()
);

INSERT INTO credentials (
    user_id,
    password,
    updated_at
) VALUES (
    1,
    '$2a$10$nA4IKXj9SHjOTMyCYsLyXu0vqKWx5pIaI5iqThg3at6Ch53mOCf7e',
    now()
);

INSERT INTO user_roles (
    user_id,
    role,
    used_yn,
    created_at,
    updated_at
) VALUES (
    1,
    'ROLE_USER',
    'Y',
    now(),
    now()
);

INSERT INTO profiles (
    user_id,
    updated_at
) VALUES (
    1,
    now()
);