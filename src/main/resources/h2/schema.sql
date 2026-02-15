-- New script in schema.sql.
-- Date: Feb 14, 2026


-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--	
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
DROP TABLE IF EXISTS USERS CASCADE;

CREATE TABLE IF NOT EXISTS USERS COMMENT '사용자 정보' (
    id					INT				NOT NULL auto_increment,
    email				VARCHAR(45)		NOT NULL COMMENT '로그인 이메일',
    nickname			VARCHAR(20)		NOT NULL COMMENT '닉네임',
    name				VARCHAR(15)		NOT NULL COMMENT '사용자 이름',
    auth_type			VARCHAR(5)		NOT NULL COMMENT '인증 유형',
    is_enabled			TINYINT(1)		NOT NULL COMMENT '계정 활성화 여부',
    is_locked			TINYINT(1)		NOT NULL COMMENT '계정 잠김 여부',
    deleted_yn			CHAR(1)			NOT NULL DEFAULT 'N' COMMENT '사용자 삭제 여부',
    created_at			DATETIME		NOT NULL DEFAULT NOW() COMMENT '사용자 정보 생성 일시',
    updated_at			DATETIME		NOT NULL COMMENT '사용자 정보 변경 일시',
    deleted_at			DATETIME		NULL COMMENT '사용자 정보 삭제 일시',
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS AUTH_CREDENTIALS CASCADE;

CREATE TABLE IF NOT EXISTS AUTH_CREDENTIALS COMMENT '로컬 인증 계정 정보' (
    user_id				INT				NOT NULL COMMENT '사용자 번호',
    password			VARCHAR(255)	NOT NULL COMMENT '비밀번호',
    updated_at			DATETIME		NOT NULL COMMENT '변경 일시',
    PRIMARY KEY (user_id),
    CONSTRAINT FK_AUTH_CREDENTIALS_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS AUTH_SOCIALS CASCADE;
CREATE TABLE IF NOT EXISTS TABLENAME COMMENT '소셜 인증 계정 정보' (
    user_id				INT				NOT NULL COMMENT '사용자 번호',
    provider            VARCHAR(255)    NOT NULL COMMENT '소셜 서비스 명',
    provider_id         VARCHAR(255)    NOT NULL COMMENT '소셜 서비스별 유저 식별자',
    PRIMARY KEY (user_id),
    UNIQUE KEY UK_AUTH_SOCIALS (provider, provider_id),
    CONSTRAINT FK_AUTH_SOCIALS_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS USER_ROLES CASCADE;
CREATE TABLE IF NOT EXISTS USER_ROLES COMMENT '사용자별 보유 권한' (
    id					INT				NOT NULL auto_increment,
    user_id				INT				NOT NULL COMMENT '사용자 번호',
    role                VARCHAR(25)     NOT NULL COMMENT '보유 권한명',
    used_yn             CHAR(1)         NOT NULL DEFAULT 'Y' COMMENT '권한 사용 여부',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '권한 생성 일시',
    updated_at          DATETIME        NOT NULL COMMENT '권한 변경 일시',
    PRIMARY KEY (id),
    UNIQUE KEY UK_USER_ROLES (user_id, role),
    CONSTRAINT FK_USER_ROLES_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS PROFILES CASCADE;
CREATE TABLE IF NOT EXISTS PROFILES COMMENT '프로필 정보' (
    id					INT				NOT NULL auto_increment,
    user_id				INT				NOT NULL COMMENT '사용자 번호',
    profile_img_url     VARCHAR(255)    NOT NULL COMMENT '프로필 이미지 저장 경로',
    bio                 VARCHAR(200)    NULL COMMENT '한 줄 소개',
    univ                VARCHAR(55)     NULL COMMENT '대학명',
    site                VARCHAR(255)    NULL COMMENT '개인 사이트 URL',
    git_hub             VARCHAR(255)    NULL COMMENT '개인 깃허브 URL',
    updated_at          DATETIME        NOT NULL COMMENT '프로필 변경 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_PROFILES_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS LOGIN_HISTORIES CASCADE;
CREATE TABLE IF NOT EXISTS LOGIN_HISTORIES COMMENT '로그인 이력' (
    id					INT				NOT NULL auto_increment,
    user_id				INT				NOT NULL COMMENT '사용자 번호',
    ip_address          VARCHAR(45)     NOT NULL COMMENT '접속 아이피 주소',
    device              VARCHAR(10)     NOT NULL COMMENT '접속 기긱',
    os                  VARCHAR(20)     NOT NULL COMMENT '운영체제',
    browser             VARCHAR(20)     NOT NULL COMMENT '접속 브라우저',
    action_type         CHAR(1)         NOT NULL COMMENT '접속 유형',
    login_at            DATETIME        NOT NULL COMMENT '로그인 일시',
    logout_at           DATETIME        NULL COMMENT '로그아웃 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_LOGIN_HISTORIES_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);



-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--	
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
DROP TABLE IF EXISTS TEAMS CASCADE;
CREATE TABLE IF NOT EXISTS TEAMS COMMENT '팀 정보' (
    id					INT				NOT NULL auto_increment,
    user_id				INT				NOT NULL COMMENT '사용자 번호',
    name                VARCHAR(75)     NOT NULL COMMENT '팀명',
    introduction        VARCHAR(150)    NOT NULL COMMENT '팀 소개',
    deleted_yn          CHAR(1)         NOT NULL DEFAULT 'N' COMMENT '팀 정보 삭제 여부',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '팀 정보 생성 일시',
    updated_at          DATETIME        NOT NULL COMMENT '팀 정보 변경 일시',
    deleted_at          DATETIME        NULL COMMENT '팀 정보 삭제 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_TEAMS_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS TEAM_USERS CASCADE;
CREATE TABLE IF NOT EXISTS TEAM_USERS COMMENT '팀원' (
    team_id				INT				NOT NULL COMMENT '팀 번호',
    user_id				INT				NOT NULL COMMENT '사용자 번호',
    is_captain          CHAR(1)         NOT NULL DEFAULT 'N' COMMENT '팀장 여부',
    deleted_yn          CHAR(1)         NOT NULL DEFAULT 'N' COMMENT '팀원 삭제 여부',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '팀원 등록 일시',
    updated_at          DATETIME        NOT NULL COMMENT '팀원 변경 일시',
    deleted_at          DATETIME        NULL COMMENT '팀원 삭제 일시',
    PRIMARY KEY (team_id, user_id),
    CONSTRAINT FK_TEAM_USERS_TEAM_ID FOREIGN KEY (team_id) REFERENCES TEAMS (id),
    CONSTRAINT FK_TEAM_USERS_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);



-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--	
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
DROP TABLE IF EXISTS PROJECTS CASCADE;
CREATE TABLE IF NOT EXISTS PROJECTS COMMENT '프로젝트 정보' (
    id					INT				NOT NULL auto_increment,
    team_id				INT				NOT NULL COMMENT '팀 번호',
    user_id				INT				NOT NULL COMMENT '사용자 번호',
    name                VARCHAR(150)    NOT NULL COMMENT '프로젝트 명',
    description         VARCHAR(300)    NOT NULL COMMENT '프로젝트 설명',
    started_date        DATE            NOT NULL COMMENT '시작일',
    end_date            DATE            NOT NULL COMMENT '종료일',
    status              CHAR(1)         NOT NULL COMMENT '프로젝트 진행 상태',
    deleted_yn          CHAR(1)         NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '프로젝트 등록 일시',
    updated_at          DATETIME        NOT NULL COMMENT '프로젝트 변경 일시',
    deleted_at          DATETIME        NULL COMMENT '프로젝트 삭제 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_PROJECTS_TEAM_ID FOREIGN KEY (team_id) REFERENCES TEAMS (id),
    CONSTRAINT FK_PROJECTS_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS TASK_GROUPS CASCADE;
CREATE TABLE IF NOT EXISTS TASK_GROUPS COMMENT '상위 업무 그룹' (
    id                  INT				NOT NULL auto_increment,
    name                VARCHAR(55)     NOT NULL COMMENT '업무 그룹명',
    used_yn             CHAR(1)         NOT NULL DEFAULT 'Y' COMMENT '사용 여부',
    order_no            INT             NOT NULL COMMENT '화면 정렬 순서',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '그룹 생성 일시',
    updated_at          DATETIME        NOT NULL COMMENT '그룹 변경 일시',
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS PROJECT_TASKS CASCADE;
CREATE TABLE IF NOT EXISTS PROJECT_TASKS COMMENT '프로젝트 업무' (
    id                  INT				NOT NULL auto_increment,
    project_id          INT             NOT NULL COMMENT '프로젝트 번호',
    group_id            INT             NOT NULL COMMENT '업무 그룹 번호',
    name                VARCHAR(150)    NOT NULL COMMENT '업무명',
    started_date        DATE            NOT NULL COMMENT '시작일',
    end_date            DATE            NOT NULL COMMENT '종료일',
    actual_end_date     DATE            NULL COMMENT '실제 종료일',
    status              CHAR(1)         NOT NULL COMMENT '업무 진행 상태',
    rate                INT             NOT NULL DEFAULT 0 COMMENT '진행률',
    order_no            INT             NOT NULL COMMENT '화면 정렬 순서',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '업무 등록 일시',
    updated_at          DATETIME        NOT NULL COMMENT '업무 변경 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_PROJECT_TASKS_PROJECT_ID FOREIGN KEY (project_id) REFERENCES PROJECTS (id),
    CONSTRAINT FK_PROJECT_TASKS_PARENT_ID FOREIGN KEY (group_id) REFERENCES TASK_GROUPS (id)
);


DROP TABLE IF EXISTS ALLOCATIONS CASCADE;
CREATE TABLE IF NOT EXISTS ALLOCATIONS COMMENT '업무 할당' (
    project_task_id     INT             NOT NULL COMMENT '프로젝트 업무 번호',
    user_id             INT             NOT NULL COMMENT '사용자 번호',
    PRIMARY KEY (project_task_id, user_id),
    CONSTRAINT FK_ALLOCATIONS_PROJECT_TASK_ID FOREIGN KEY (project_task_id) REFERENCES PROJECT_TASKS (id),
    CONSTRAINT FK_ALLOCATIONS_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS TASK_ATTACHMENTS CASCADE;
CREATE TABLE IF NOT EXISTS TASK_ATTACHMENTS COMMENT '프로젝트 업무 산출물' (
    id                  INT				NOT NULL auto_increment,
    project_task_id     INT             NOT NULL COMMENT '프로젝트 업무 번호',
    user_id             INT             NOT NULL COMMENT '사용자 번호',
    original_name       VARCHAR(255)    NOT NULL COMMENT '파일 원본명',
    saved_name          VARCHAR(255)    NOT NULL COMMENT '파일 저장명',
    saved_path          VARCHAR(255)    NOT NULL COMMENT '파일 저장 경로',
    file_size           INT             NOT NULL COMMENT '파일 크기',
    file_type           VARCHAR(100)    NOT NULL COMMENT '파일 유형(마임타입)',
    extension           VARCHAR(10)     NOT NULL COMMENT '파일 확장자명',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '산출물 등록 일시',
    updated_at          DATETIME        NOT NULL COMMENT '산출물 변경 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_TASK_ATTACHMENTS_PROJECT_TASK_ID FOREIGN KEY (project_task_id) REFERENCES PROJECT_TASKS (id),
    CONSTRAINT FK_TASK_ATTACHMENTS_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS TASK_ENTRIES CASCADE;
CREATE TABLE IF NOT EXISTS TASK_ENTRIES COMMENT '업무 상세 항목' (
    id                  INT				NOT NULL auto_increment,
    project_task_id     INT             NOT NULL COMMENT '프로젝트 업무 번호',
    user_id             INT             NOT NULL COMMENT '사용자 번호',
    content             VARCHAR(300)    NOT NULL COMMENT '할 일 내용',
    is_done             TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '완료 여부',
    order_no            INT             NOT NULL COMMENT '화면 정렬 순서',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '상세 항목 생성 일시',
    updated_at          DATETIME        NOT NULL COMMENT '상세 항목 변경 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_TASK_ENTRIES_PROJECT_TASK_ID FOREIGN KEY (project_task_id) REFERENCES PROJECT_TASKS (id),
    CONSTRAINT FK_TASK_ENTRIES_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);



-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--	
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
DROP TABLE IF EXISTS MEETING_MINUTES CASCADE;
CREATE TABLE IF NOT EXISTS MEETING_MINUTES COMMENT '프로젝트 회의록' (
    id                  INT				NOT NULL auto_increment,
    project_id          INT             NOT NULL COMMENT '프로젝트 번호',
    user_id             INT             NOT NULL COMMENT '사용자(회의록 작성자) 번호',
    meeting_date        DATE            NOT NULL COMMENT '회의일',
    TITLE               VARCHAR(150)    NOT NULL COMMENT '회의록 제목',
    CONTENTS            TEXT            NOT NULL COMMENT '회의록 내용',
    CREATED_AT          DATETIME        NOT NULL DEFAULT NOW() COMMENT '회의록 등록 일시',
    UPDATED_AT          DATETIME        NOT NULL COMMENT '회의록 변경 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_MEETING_MINUTES_PROJECT_ID FOREIGN KEY (project_id) REFERENCES PROJECTS (id),
    CONSTRAINT FK_MEETING_MINUTES_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS MEETING_ATTENDEES CASCADE;
CREATE TABLE IF NOT EXISTS MEETING_ATTENDEES COMMENT '회의 참여자' (
    meeting_id          INT             NOT NULL COMMENT '회의록 번호',
    user_id             INT             NOT NULL COMMENT '사용자 번호',
    PRIMARY KEY (meeting_id, user_id),
    CONSTRAINT FK_MEETING_ATTENDEES FOREIGN KEY (meeting_id) REFERENCES MEETING_MINUTES (id),
    CONSTRAINT FK_MEETING_ATTENDEES FOREIGN KEY (user_id) REFERENCES USERS (id)
);



-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--	
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
DROP TABLE IF EXISTS BOARDS CASCADE;
CREATE TABLE IF NOT EXISTS BOARDS COMMENT '게시판' (
    id                  INT				NOT NULL auto_increment,
    name                VARCHAR(55)     NOT NULL COMMENT '게시판명',
    used_yn             CHAR(1)         NOT NULL DEFAULT 'Y' COMMENT '게시판 사용 여부',
    order_no            INT             NOT NULL COMMENT '화면 정렬 순서',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '게시판 생성 일시',
    updated_at          DATETIME        NOT NULL COMMENT '게시판 변경 일시',
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS POSTS CASCADE;
CREATE TABLE IF NOT EXISTS POSTS COMMENT '게시글' (
    id                  INT				NOT NULL auto_increment,
    board_id            INT             NOT NULL COMMENT '게시판 번호',
    user_id             INT             NOT NULL COMMENT '사용자 번호',
    title               VARCHAR(150)    NOT NULL COMMENT '게시글 제목',
    contents            TEXT            NOT NULL COMMENT '게시글 내용',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '게시글 생성 일시',
    updated_at          DATETIME        NOT NULL COMMENT '게시글 변경 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_POSTS_BOARD_ID FOREIGN KEY (board_id) REFERENCES BOARDS (id),
    CONSTRAINT FK_POSTS_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);


DROP TABLE IF EXISTS POST_LIKES CASCADE;
CREATE TABLE IF NOT EXISTS POST_LIKES COMMENT '게시글 좋아요' (
    post_id             INT             NOT NULL COMMENT '게시글 번호',
    user_id             INT             NOT NULL COMMENT '사용자 번호',
    PRIMARY KEY (post_id, user_id),
    CONSTRAINT FK_POST_LIKES_POST_ID FOREIGN KEY (post_id) REFERENCES POSTS (id),
    CONSTRAINT FK_POST_LIKES_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (user_id)
);


DROP TABLE IF EXISTS POST_ATTACHMENTS CASCADE;
CREATE TABLE IF NOT EXISTS TABLENAME COMMENT '게시글 첨부 파일' (
    id                  INT				NOT NULL auto_increment,
    post_id             INT             NOT NULL COMMENT '게시글 번호',
    original_name       VARCHAR(255)    NOT NULL COMMENT '첨부파일 원본명',
    saved_name          VARCHAR(255)    NOT NULL COMMENT '첨부파일 저장명',
    saved_path          VARCHAR(255)    NOT NULL COMMENT '파일 저장 경로',
    file_size           INT             NOT NULL COMMENT '파일 크기',
    file_type           VARCHAR(100)    NOT NULL COMMENT '파일 유형(마임 타입)',
    extension           VARCHAR(10)     NOT NULL COMMENT '확장자명',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '파일 등록 일시',
    updated_at          DATETIME        NOT NULL COMMENT '파일 변경 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_POST_ATTACHMENTS_POST_ID FOREIGN KEY (post_id) REFERENCES POSTS (id)
);


DROP TABLE IF EXISTS COMMENTS CASCADE;
CREATE TABLE IF NOT EXISTS COMMENTS COMMENT '댓글' (
    id                  INT				NOT NULL auto_increment,
    post_id             INT             NOT NULL COMMENT '게시글 번호',
    parent_id           INT             NOT NULL COMMENT '상위 댓글 번호',
    user_id             INT             NOT NULL COMMENT '사용자 번호',
    contents            VARCHAR(300)    NOT NULL COMMENT '댓글 내용',
    depth               INT             NOT NULL DEFAULT 0 COMMENT '댓글 깊이',
    order_no            INT             NOT NULL COMMENT '화면 정렬 순서',
    deleted_yn          CHAR(1)         NOT NULL DEFAULT 'N' COMMENT '댓글 삭제 여부',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '댓글 생성 일시',
    updated_at          DATETIME        NOT NULL COMMENT '댓글 변경 일시',
    deleted_at          DATETIME        NULL COMMENT '댓글 삭제 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_COMMENTS_POST_ID FOREIGN KEY (post_id) REFERENCES POSTS (id),
    CONSTRAINT FK_COMMENTS_PARENT_ID FOREIGN KEY (parent_id) REFERENCES COMMENTS (id),
    CONSTRAINT FK_COMMENTS_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);



-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--	
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
DROP TABLE IF EXISTS NOTIFICATIONS CASCADE;
CREATE TABLE IF NOT EXISTS NOTIFICATIONS COMMENT '알림' (
    id                  INT				NOT NULL auto_increment,
    receiver_id         INT             NOT NULL COMMENT '받는 사용자 번호',
    sender_id           INT             NOT NULL COMMENT '보낸 사용자 번호',
    notification_type   VARCHAR(25)     NOT NULL COMMENT '알림 유형',
    contents            VARCHAR(100)    NOT NULL COMMENT '알림 내용',
    target_id           INT             NOT NULL COMMENT '알림 원인에 대한 식별 번호',
    target_type         VARCHAR(25)     NOT NULL COMMENT '알림 원인 유형',
    read_at             CHAR(1)         NOT NULL DEFAULT 'N' COMMENT '알림 확인 여부',
    created_at          DATETIME        NOT NULL DEFAULT NOW() COMMENT '알림 생성 일시',
    PRIMARY KEY (id),
    CONSTRAINT FK_NOTIFICATIONS_RECEIVER_ID FOREIGN KEY (receiver_id) REFERENCES USERS (id),
    CONSTRAINT FK_NOTIFICATIONS_SENDER_ID FOREIGN KEY (sender_id) REFERENCES USERS (id)
);

