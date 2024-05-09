-- 계정 추가
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user01', 'pass01', '유저일', 'user01@kh.com',
			NULL, '010-1111-2222', DEFAULT, DEFAULT, DEFAULT);

-- 유저일 비밀번호 
-- pass01 을 암호화된 $2a$10$2p01wv0FlCOJkGRcZCl.lOPXd1kYpmgxBNCZwdSNXiVzeQgthQuwe 로 업데이트
UPDATE "MEMBER" SET 
MEMBER_PW = '$2a$10$2p01wv0FlCOJkGRcZCl.lOPXd1kYpmgxBNCZwdSNXiVzeQgthQuwe'
WHERE MEMBER_ID = 'user01';


SELECT * FROM "MEMBER";

-- 계정 추가
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user02', 'pass02', '유저이', 'user02@kh.com',
			NULL, '010-2222-2222', DEFAULT, DEFAULT, DEFAULT);

-- 유저일 비밀번호 
-- pass01 을 암호화된 $2a$10$2p01wv0FlCOJkGRcZCl.lOPXd1kYpmgxBNCZwdSNXiVzeQgthQuwe 로 업데이트
UPDATE "MEMBER" SET 
MEMBER_PW = '$2a$10$2p01wv0FlCOJkGRcZCl.lOPXd1kYpmgxBNCZwdSNXiVzeQgthQuwe'
WHERE MEMBER_ID = 'user02';


COMMIT;


-- 게시글 샘플 ALT + X 로 실행
-- ******* 아직 실행 안함 내용 수정하고 진행 ********
BEGIN
	FOR I IN 1..600 LOOP
		
		INSERT INTO "BOARD"
		VALUES(SEQ_BOARD_NO.NEXTVAL,
					 SEQ_BOARD_NO.CURRVAL || '번째 게시글',
					 SEQ_BOARD_NO.CURRVAL || '번째 게시글 내용 입니다',
					 DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT,
					 CEIL( DBMS_RANDOM.VALUE(0,5) ), -- MEMBER_NO(작성회원번호)
					 3 -- BOARD_CODE(게시판종류)
		);
		
	END LOOP;
END;

COMMIT;

SELECT * FROM BOARD
WHERE BOARD_CODE = 3;
SELECT * FROM "MEMBER";

-- [1] 자유 게시판 샘플 
-- ******* 아직 실행 안함 내용 수정하고 진행 ********
BEGIN
	FOR I IN 1..300 LOOP
	
		INSERT INTO "COMMENT"	
		VALUES(
			SEQ_COMMENT_NO.NEXTVAL,
			SEQ_COMMENT_NO.CURRVAL || '번째 댓글 입니다',
			DEFAULT, DEFAULT,
			CEIL( DBMS_RANDOM.VALUE(0, 500) ), -- 게시글번호
			CEIL( DBMS_RANDOM.VALUE(0,3) ), -- 댓글작성회원번호
			NULL -- 부모댓글번호
		);
	END LOOP;
END;

-- [2] 문의 게시판 샘플
-- ******* 아직 실행 안함 내용 수정하고 진행 ********
BEGIN
	FOR I IN 1..1 LOOP
	
		INSERT INTO "COMMENT"	
		VALUES(
			SEQ_COMMENT_NO.NEXTVAL,
			SEQ_COMMENT_NO.CURRVAL || '번째 댓글 입니다',
			DEFAULT, DEFAULT,
			1806, -- 게시글번호
			4, -- 댓글작성회원번호
			1 -- 부모댓글번호
		);
	END LOOP;
END;

-- [3] 팁과 노하우 게시판 샘플
-- ******* 아직 실행 안함 내용 수정하고 진행 ********
BEGIN
	FOR I IN 1..300 LOOP
	
		INSERT INTO "COMMENT"	
		VALUES(
			SEQ_COMMENT_NO.NEXTVAL,
			SEQ_COMMENT_NO.CURRVAL || '번째 댓글 입니다',
			DEFAULT, DEFAULT,
			CEIL( DBMS_RANDOM.VALUE(0, 500) ), -- 게시글번호
			CEIL( DBMS_RANDOM.VALUE(0,3) ), -- 댓글작성회원번호
			NULL -- 부모댓글번호
		);
	END LOOP;
END;


COMMIT;

SELECT * FROM "COMMENT";
SELECT * FROM "BOARD" ORDER BY BOARD_NO DESC;
SELECT * FROM "MEMBER";


SELECT LEVEL, C.* FROM
(SELECT COMMENT_NO, COMMENT_CONTENT,
    TO_CHAR(COMMENT_WRITE_DATE, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분"') COMMENT_WRITE_DATE,
    BOARD_NO, MEMBER_NO, MEMBER_NICKNAME, PARENT_COMMENT_NO, COMMENT_DEL_FL
	FROM "COMMENT"
	JOIN MEMBER USING(MEMBER_NO)
	WHERE BOARD_NO = 1806) C
WHERE COMMENT_DEL_FL = 'N'

OR 0 != (SELECT COUNT(*) FROM "COMMENT" SUB 
				WHERE SUB.PARENT_COMMENT_NO = C.COMMENT_NO
				AND COMMENT_DEL_FL = 'N')

START WITH PARENT_COMMENT_NO IS NULL
CONNECT BY PRIOR COMMENT_NO = PARENT_COMMENT_NO
ORDER SIBLINGS BY COMMENT_NO;



----------------------------- 샘플데이터 끝 -----------------------------------------
SELECT * FROM BOARD;

-- SQL 구문 작성 구역