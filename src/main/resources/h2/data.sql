
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--	상위 업무 그룹
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
INSERT INTO TASK_GROUPS (
    name,
    used_yn,
    order_no,
    updated_at
) VALUES (
    '기획',
    'Y',
    1,
    NOW()
);

INSERT INTO TASK_GROUPS (
    name,
    used_yn,
    order_no,
    updated_at
) VALUES (
    '요구사항 분석',
    'Y',
    2,
    NOW()
);

INSERT INTO TASK_GROUPS (
    name,
    used_yn,
    order_no,
    updated_at
) VALUES (
    '설계',
    'Y',
    3,
    NOW()
);

INSERT INTO TASK_GROUPS (
    name,
    used_yn,
    order_no,
    updated_at
) VALUES (
    '구현',
    'Y',
    4,
    NOW()
);

INSERT INTO TASK_GROUPS (
    name,
    used_yn,
    order_no,
    updated_at
) VALUES (
    '테스트',
    'Y',
    5,
    NOW()
);


INSERT INTO TASK_GROUPS (
    name,
    used_yn,
    order_no,
    updated_at
) VALUES (
    '유지보수',
    'Y',
    6,
    NOW()
);


-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--	게시판
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
INSERT INTO BOARDS (
    name,
    used_yn,
    order_no,
    updated_at
) VALUES (
    '커뮤니티',
    'Y',
    1,
    NOW()
);

INSERT INTO BOARDS (
    name,
    used_yn,
    order_no,
    updated_at
) VALUES (
    '공지사항',
    'Y',
    2,
    NOW()
);