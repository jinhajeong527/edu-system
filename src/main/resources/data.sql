INSERT INTO member (username, email, password, phone_number, role, created_date, last_modified_date)
VALUES
('testuser1', 'testuser1@example.com', 'password1', '010-1111-5678', 'STUDENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser2', 'testuser2@example.com', 'password2', '010-2222-5678', 'INSTRUCTOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser3', 'testuser3@example.com', 'password3', '010-3333-5678', 'INSTRUCTOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser4', 'testuser4@example.com', 'password4', '010-4444-5678', 'STUDENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser5', 'testuser5@example.com', 'password5', '010-5555-5678', 'STUDENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser6', 'testuser6@example.com', 'password6', '010-6666-5678', 'STUDENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser7', 'testuser7@example.com', 'password7', '010-7777-5678', 'STUDENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser8', 'testuser8@example.com', 'password8', '010-8888-5678', 'STUDENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser9', 'testuser9@example.com', 'password9', '010-9999-5678', 'STUDENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO course (enrollment_rate, price, course_id, created_date, current_enrollment, last_modified_date, max_capacity, created_by, last_modified_by, title)
VALUES
(0.0, 300000, 1, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 3, 'testuser2@example.com', 'testuser2@example.com', 'course1'),
(0.0, 300000, 2, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 2, 'testuser2@example.com', 'testuser2@example.com', 'course2'),
(0.0, 300000, 3, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 5, 'testuser2@example.com', 'testuser2@example.com', 'course3'),
(0.0, 300000, 4, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 5, 'testuser2@example.com', 'testuser2@example.com', 'course4'),
(0.0, 300000, 5, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 5, 'testuser2@example.com', 'testuser2@example.com', 'course5'),
(0.0, 300000, 6, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 5, 'testuser2@example.com', 'testuser2@example.com', 'course6'),
(0.0, 300000, 7, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 5, 'testuser2@example.com', 'testuser2@example.com', 'course7');

INSERT INTO member_course (course_id, id, member_id, status)
VALUES
(1, 1, 2, 'TEACHING'),
(2, 2, 2, 'TEACHING'),
(3, 3, 2, 'TEACHING'),
(4, 4, 2, 'TEACHING'),
(5, 5, 2, 'TEACHING'),
(6, 6, 2, 'TEACHING'),
(7, 7, 2, 'TEACHING');

ALTER TABLE member ALTER COLUMN member_id RESTART WITH 10;
ALTER TABLE course ALTER COLUMN course_id RESTART WITH 8;
ALTER TABLE member_course ALTER COLUMN id RESTART WITH 8;