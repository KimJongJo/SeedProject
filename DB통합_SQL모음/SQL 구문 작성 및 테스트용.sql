-- 계정 추가
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user01', 'pass01', '유저일', 'user01@kh.com',
			NULL, '010-1111-2222', DEFAULT, DEFAULT, DEFAULT);

-- 유저일 비밀번호 
-- pass01 을 암호화된 $2a$10$2p01wv0FlCOJkGRcZCl.lOPXd1kYpmgxBNCZwdSNXiVzeQgthQuwe 로 업데이트
UPDATE "MEMBER" SET 
MEMBER_PW = '$2a$10$2p01wv0FlCOJkGRcZCl.lOPXd1kYpmgxBNCZwdSNXiVzeQgthQuwe'
WHERE MEMBER_ID = 'user03';

-- 계정 추가
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user03', 'pass01', '노댓글', 'user01@kh.com',
			NULL, '010-1111-2222', DEFAULT, DEFAULT, DEFAULT);

COMMIT;
		
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


SELECT * FROM BOARD_IMG ;


ROLLBACK;
SELECT * FROM BOARD
WHERE BOARD_CODE = 3;

SELECT * FROM "MEMBER";

-- [1] 자유 게시판 샘플 
-- ******* 아직 실행 안함 내용 수정하고 진행 ********
BEGIN
	FOR I IN 1..2 LOOP
	
		INSERT INTO "COMMENT"	
		VALUES(
			SEQ_COMMENT_NO.NEXTVAL,
			SEQ_COMMENT_NO.CURRVAL || '번째 댓글 입니다',
			DEFAULT, DEFAULT,
			CEIL( DBMS_RANDOM.VALUE(596, 596) ), -- 게시글번호
			CEIL( DBMS_RANDOM.VALUE(5,5) ), -- 댓글작성회원번호
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
	FOR I IN 1..3 LOOP
	
		INSERT INTO "COMMENT"	
		VALUES(
			SEQ_COMMENT_NO.NEXTVAL,
			SEQ_COMMENT_NO.CURRVAL || '번째 댓글 입니다',
			DEFAULT, DEFAULT,
			1834, -- 게시글번호
			3, -- 댓글작성회원번호
			NULL -- 부모댓글번호
		);
	END LOOP;
END;

---------------
BEGIN
	FOR I IN 1..3 LOOP
	
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
-----------------





COMMIT;

SELECT * FROM "COMMENT";


SELECT * FROM "MEMBER";
SELECT * FROM "BOARD_LIKE";
SELECT * FROM "BOARD"
WHERE BOARD_CODE = 2
ORDER BY BOARD_NO DESC;

SELECT SEED_NAME FROM SEED
WHERE SEED_NO IN (19, 27);

SELECT * FROM "CART"
WHERE MEMBER_NO = 9;


UPDATE "CART" SET
COUNT = (SELECT COUNT FROM "CART"
		WHERE MEMBER_NO = 4
		AND SEED_NO = 3) + 3
WHERE MEMBER_NO = 4
AND SEED_NO = 3;



SELECT * FROM SEED_TYPE;
SELECT * FROM CART;
ORDER BY CART_NO;
SELECT * FROM SEED;

--DELETE FROM SEED;

SELECT * FROM "MEMBER";

COMMIT;


SELECT SEED_NO, SEED_NAME, SEED_IMG_PATH, SEED_PRICE, SEED_TEMP,
SEED_TIME, SEED_DISTANCE, SEED_RATE
FROM "SEED"
WHERE SEED_SOLD_OUT = 'N'



INSERT INTO "CART"
VALUES(SEQ_CART_NO.NEXTVAL, 4, #3, DEFAULT);

SELECT * FROM "SEED"
WHERE SEED_NAME = '노각오이';

UPDATE "SEED" SET 
SEED_DISTANCE = '30cm'
WHERE SEED_NAME = '백가지';

COMMIT;

		SELECT LEVEL, C.* FROM
		(SELECT COMMENT_NO, COMMENT_CONTENT,
				    BOARD_NO, MEMBER_NO, MEMBER_NICKNAME, PARENT_COMMENT_NO, COMMENT_DEL_FL, COMMENT_WRITE_DATE
			    	
				FROM "COMMENT"
				JOIN MEMBER USING(MEMBER_NO)
				WHERE BOARD_NO = 1806) C
		WHERE COMMENT_DEL_FL = 'N'
		OR 0 != (SELECT COUNT(*) FROM "COMMENT" SUB
							WHERE SUB.PARENT_COMMENT_NO = C.COMMENT_NO
							AND COMMENT_DEL_FL='N')
		START WITH PARENT_COMMENT_NO IS NULL
		CONNECT BY PRIOR COMMENT_NO = PARENT_COMMENT_NO
		ORDER SIBLINGS BY COMMENT_NO;



SELECT * FROM "SEED"
ORDER BY SEED_TEMP;



--DELETE FROM "CART";

	SELECT SEED_PRICE FROM "SEED"
	WHERE SEED_NAME = '노각오이';

--DELETE FROM "CART"
--WHERE CART_NO = 1;



SELECT CART_NO, SEED_NO, COUNT, SEED_IMG_PATH, SEED_NAME, SEED_PRICE
FROM "CART"
JOIN "SEED" USING(SEED_NO)
WHERE MEMBER_NO = 4;






DELETE FROM "CART"
WHERE MEMBER_NO = 4
AND SEED_NO = (SELECT SEED_NO FROM "SEED"
				WHERE SEED_NAME = '노각오이');


UPDATE "CART"
SET COUNT = COUNT + 1
WHERE MEMBER_NO = 4
AND SEED_NO = (SELECT SEED_NO FROM "SEED"
							WHERE SEED_NAME = '노각오이');
				




SELECT CART_NO, SEED_NO, COUNT, SEED_IMG_PATH, SEED_NAME, SEED_PRICE
FROM "CART"
JOIN "SEED" USING(SEED_NO)
WHERE MEMBER_NO = 4;



COMMIT;




 

COMMIT;

UPDATE "MEMBER" SET
		MEMBER_ADDRESS = '12345^^^종로^^^5641'
		WHERE MEMBER_NO = 5;





SELECT SEQ_BOARD_IMG_NO.CURRVAL FROM DUAL;


----------------------------- 샘플데이터 끝 -----------------------------------------

SELECT * FROM BOARD;
SELECT * FROM BOARD_IMG;

-- SQL 구문 작성 구역


-- 이전글
SELECT NVL(
	(SELECT BOARD_NO 
	FROM "BOARD"
	WHERE BOARD_CODE = 3
	AND BOARD_NO < 1834
	AND BOARD_DEL_FL = 'N'
	ORDER BY BOARD_NO DESC
	FETCH FIRST 1 ROW ONLY), -1
)AS BOARD_NO 
FROM "BOARD"
WHERE BOARD_CODE = 3
ORDER BY BOARD_NO DESC
FETCH FIRST 1 ROW ONLY;

-- 다음글
SELECT NVL(
	(SELECT BOARD_NO 
	FROM "BOARD"
	WHERE BOARD_CODE = 3
	AND BOARD_NO > 1807
	AND BOARD_DEL_FL = 'N'
	ORDER BY BOARD_NO
	FETCH FIRST 1 ROW ONLY), -1
) AS BOARD_NO
FROM "BOARD"
WHERE BOARD_CODE = 3
ORDER BY BOARD_NO
FETCH FIRST 1 ROW ONLY;


SELECT * FROM BOARD_IMG ;
INSERT INTO "BOARD_IMG" VALUES(
	NEXT_BOARD_IMG_NO, '/images/board/', '원본4.jpg', 'test4.jpg', 0, 1818
);

COMMIT;



		SELECT BOARD_NO, BOARD_TITLE, MEMBER_NICKNAME, READ_COUNT,
    
		    (SELECT COUNT(*)
		    FROM "COMMENT" C
		    WHERE C.BOARD_NO = B.BOARD_NO) COMMENT_COUNT,
		    
		    (SELECT COUNT(*)
		    FROM "BOARD_LIKE" L
		    WHERE L.BOARD_NO = B.BOARD_NO) LIKE_COUNT,
	    
		    CASE
			    WHEN SYSDATE - BOARD_WRITE_DATE < 1 / 24 / 60
			    THEN FLOOR((SYSDATE - BOARD_WRITE_DATE) * 24 * 60 * 60) || '초 전'
			    
			    WHEN SYSDATE - BOARD_WRITE_DATE < 1 / 24
			    THEN FLOOR((SYSDATE - BOARD_WRITE_DATE) * 24 * 60) || '분 전'
			    
			    WHEN SYSDATE - BOARD_WRITE_DATE < 1
			    THEN FLOOR((SYSDATE - BOARD_WRITE_DATE) * 24) || '시간 전'
			    
			    ELSE TO_CHAR(BOARD_WRITE_DATE, 'YYYY-MM-DD')
			    
			END BOARD_WRITE_DATE,
			
		(SELECT CONCAT(BOARD_IMG_PATH, BOARD_IMG_RENAME)
		 FROM BOARD_IMG
		 WHERE BOARD_NO = B.BOARD_NO
		 AND BOARD_IMG_ORDER = 0) THUMBNAIL
    	  
		FROM "BOARD" B
		JOIN "MEMBER" USING(MEMBER_NO)
		WHERE BOARD_DEL_FL = 'N'
		AND BOARD_CODE = 3
		ORDER BY BOARD_NO DESC;
	
SELECT CONCAT(BOARD_IMG_PATH, BOARD_IMG_RENAME) AS THUMBNAIL
FROM BOARD_IMG
WHERE BOARD_NO = 1822
AND BOARD_IMG_ORDER = 0;




		SELECT LEVEL, C.* FROM
		(SELECT COMMENT_NO, COMMENT_CONTENT,
				    TO_CHAR(COMMENT_WRITE_DATE, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분" SS"초"') COMMENT_WRITE_DATE,
				    BOARD_NO, MEMBER_NO, MEMBER_NICKNAME, PARENT_COMMENT_NO, COMMENT_DEL_FL
				FROM "COMMENT"
				JOIN MEMBER USING(MEMBER_NO)
				WHERE BOARD_NO = 1834) C
		WHERE COMMENT_DEL_FL = 'N'
		OR 0 != (SELECT COUNT(*) FROM "COMMENT" SUB
							WHERE SUB.PARENT_COMMENT_NO = C.COMMENT_NO
							AND COMMENT_DEL_FL='N')
		START WITH PARENT_COMMENT_NO IS NULL
		CONNECT BY PRIOR COMMENT_NO = PARENT_COMMENT_NO
		ORDER SIBLINGS BY COMMENT_NO;
		
	
	SELECT * FROM "COMMENT"  
	WHERE BOARD_NO = 1834;
	

COMMIT;

SELECT * FROM BOARD;
	
SELECT * FROM MEMBER;
COMMIT;

-- 작성한 게시글 번호, 댓글 내용, 작성자, 작성일 

SELECT BOARD_NO, COMMENT_CONTENT, MEMBER_NICKNAME, 
TO_CHAR(COMMENT_WRITE_DATE, 'YYYY"."MM"."DD"." HH24:MI') COMMENT_WRITE_DATE,
		(SELECT COUNT(*)
		FROM "COMMENT"
		WHERE MEMBER_NO = 1
		AND COMMENT_DEL_FL = 'N'
		) COMMENT_COUNT,
		
		(SELECT BT.BOARD_CODE
		FROM "BOARD_TYPE" BT
		JOIN "BOARD" B ON (B.BOARD_CODE = BT.BOARD_CODE)
		JOIN "COMMENT" C ON (B.BOARD_NO = C.BOARD_NO)
		WHERE C.MEMBER_NO = 1) BOARD_CODE
		
FROM "COMMENT" C
JOIN "MEMBER" USING(MEMBER_NO)
WHERE MEMBER_NO = 1
AND COMMENT_DEL_FL = 'N'
ORDER BY BOARD_NO DESC;

SELECT COUNT(*)
FROM "COMMENT"
WHERE MEMBER_NO = 1
AND COMMENT_DEL_FL ='N';

SELECT *
FROM "BOARD_TYPE";

SELECT * FROM "MEMBER";
SELECT * FROM "COMMENT";

SELECT BT.BOARD_CODE
FROM "BOARD_TYPE" BT
JOIN "BOARD" B ON (B.BOARD_CODE = BT.BOARD_CODE)
JOIN "COMMENT" C ON (B.BOARD_NO = C.BOARD_NO)
WHERE C.MEMBER_NO = 1;



SELECT B.BOARD_NO, C.COMMENT_CONTENT, M.MEMBER_NICKNAME, 
TO_CHAR(C.COMMENT_WRITE_DATE, 'YYYY"."MM"."DD"." HH24:MI') COMMENT_WRITE_DATE,
		(SELECT COUNT(*)
		FROM "COMMENT"
		WHERE MEMBER_NO = 1
		AND COMMENT_DEL_FL = 'N'
		) COMMENT_COUNT, BT.BOARD_CODE AS BOARD_CODE
FROM "COMMENT" C
JOIN "MEMBER" M ON (C.MEMBER_NO = M.MEMBER_NO)
JOIN "BOARD" B ON (C.BOARD_NO = B.BOARD_NO)
JOIN "BOARD_TYPE" BT ON (B.BOARD_CODE = BT.BOARD_CODE)
WHERE C.MEMBER_NO = 1
AND COMMENT_DEL_FL = 'N'
ORDER BY C.COMMENT_NO DESC;



SELECT BOARD_CODE
FROM "COMMENT"
WHERE BOARD_NO = 1834;



SELECT * FROM "MEMBER";

DELETE FROM "MEMBER"
WHERE MEMBER_ID = 'ID3494198019';

DELETE FROM MEMBER
WHERE MEMBER_EMAIL = 'kakaoEmail';




COMMIT;

