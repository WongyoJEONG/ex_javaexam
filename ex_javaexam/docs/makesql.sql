-- 1. 정답 테이블 생성 (CorrectAnswer 엔티티와 매핑)
CREATE TABLE correct_answer (
                                question_num INT PRIMARY KEY,
                                answer VARCHAR(255) NOT NULL
);

-- 2. 학생 응시정보 테이블 생성 (ExamResult 엔티티와 매핑)

CREATE TABLE exam_result (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(50) NOT NULL,
                             email VARCHAR(100) NOT NULL,
                             password VARCHAR(50) NOT NULL,
                             ans1 VARCHAR(255),
                             ans2 VARCHAR(255),
                             ans3 VARCHAR(255),
                             ans4 VARCHAR(255),
                             ans5 VARCHAR(255),
                             score INT DEFAULT 0,
                             exam_date DATETIME
);

-- 3. 정답 데이터 입력
INSERT INTO correct_answer (question_num, answer) VALUES (1, '2');
INSERT INTO correct_answer (question_num, answer) VALUES (2, '3');
INSERT INTO correct_answer (question_num, answer) VALUES (3, '1');
INSERT INTO correct_answer (question_num, answer) VALUES (4, 'extends');
INSERT INTO correct_answer (question_num, answer) VALUES (5, 'try');

-- 4. 샘플 데이터 5개 이상 준비할 것.
INSERT INTO exam_result (name, email, password, ans1, ans2, ans3, ans4, ans5, score, exam_date)
VALUES ('홍길동', 'hong@test.com', '1234', '2', '3', '1', 'extends', 'try', 100, '2025-12-21 11:00:00');

INSERT INTO exam_result (name, email, password, ans1, ans2, ans3, ans4, ans5, score, exam_date)
VALUES ('김영희', 'kim@test.com', '1234', '2', '3', '1', 'extends', 'catch', 80, '2025-12-21 17:00:51');

INSERT INTO exam_result (name, email, password, ans1, ans2, ans3, ans4, ans5, score, exam_date)
VALUES ('이철수', 'lee@test.com', '1234', '1', '3', '1', 'extends', 'finally', 60, '2025-12-22 17:00:40');

INSERT INTO exam_result (name, email, password, ans1, ans2, ans3, ans4, ans5, score, exam_date)
VALUES ('박민수', 'park@test.com', '1234', '2', '1', '1', 'implements', 'try', 60, NOW());

INSERT INTO exam_result (name, email, password, ans1, ans2, ans3, ans4, ans5, score, exam_date)
VALUES ('최지우', 'choi@test.com', '1234', '2', '3', '2', 'extends', 'throw', 60, NOW());

-- 커밋용
COMMIT;