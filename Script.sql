DROP DATABASE IF EXISTS IJSE;
CREATE DATABASE IF NOT EXISTS IJSE;
SHOW DATABASES;
USE IJSE;

DROP TABLE IF EXISTS Student;
CREATE TABLE IF NOT EXISTS Student
(
    studentId   VARCHAR(45),
    studentName VARCHAR(45),
    email       TEXT,
    contact     VARCHAR(20),
    address     TEXT,
    nic         VARCHAR(45),
    CONSTRAINT PRIMARY KEY (studentId)
    );
SHOW TABLES;
DESCRIBE Student;

USE IJSE;
INSERT INTO Student
VALUES ('STU-001', 'Ravindu', 'ravi630@gmail.com', '0759677524', 'Colombo', '200956742221781'),
       ('STU-002', 'Maneesha', 'gune111@gmail.com', '07728886782', 'Panadura', '20011243556781'),
       ('STU-003', 'Dakshina', 'daki12@gmail.com', '07111456782', 'Mathara', '200500354626781'),
       ('STU-004', 'Nimesh', 'nimma@gmail.com', '07223455242', 'Pandura', '20011856556781'),
       ('STU-005', 'Pasan', 'pahasara@gmail.com', '07830056782', 'Galle', '20085593456781');

DROP TABLE IF EXISTS Teacher;
CREATE TABLE IF NOT EXISTS Teacher
(
    teacherId VARCHAR(45),
    name      VARCHAR(45),
    nic       VARCHAR(45),
    contact   VARCHAR(45),
    address   TEXT,
    CONSTRAINT PRIMARY KEY (teacherId)
    );
SHOW TABLES;
DESCRIBE Teacher;

USE IJSE;
INSERT INTO Teacher
VALUES ('TEC-001', 'Hasika Sandaruwan', '199523456788V', '07123456782', 'Colombo'),
       ('TEC-002', 'Danuja Deshan ', '199623456788V', '07523456782', 'Colombo'),
       ('TEC-003', 'Yasindu Sathsara', '199524456788V', '07523456782', 'Galle'),
       ('TEC-004', 'Maneesha Dadigama', '199523451788V', '07143456782', 'Colombo'),
       ('TEC-005', 'K.S Vithanage', '199523436788V', '07823446782', 'Panadura');

DROP TABLE IF EXISTS Subject;
CREATE TABLE IF NOT EXISTS Subject
(
    subjectId   VARCHAR(45),
    subjectName VARCHAR(45),
    credit      DOUBLE,
    teacherId   VARCHAR(45),
    CONSTRAINT PRIMARY KEY (subjectId, teacherID),
    CONSTRAINT FOREIGN KEY (teacherId) REFERENCES Teacher (teacherId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Subject;

USE IJSE;
INSERT INTO Subject
VALUES ('SUB-001', 'DBMS', '20', 'TEC-001'),
       ('SUB-002', 'SE', '20', 'TEC-004'),
       ('SUB-003', 'Hibernate', '20', 'TEC-002'),
       ('SUB-004', 'OOP', '20', 'TEC-003'),
       ('SUB-005', 'HTML', '20', 'TEC-005');

DROP TABLE IF EXISTS Course;
CREATE TABLE IF NOT EXISTS Course
(
    courseId   VARCHAR(45),
    courseName VARCHAR(45),
    cost       DOUBLE,
    duration   VARCHAR(45),
    subjectId  VARCHAR(45),
    CONSTRAINT PRIMARY KEY (courseId, subjectId),
    CONSTRAINT FOREIGN KEY (subjectId) REFERENCES Subject (subjectId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Course;

USE IJSE;
INSERT INTO Course
VALUES ('COU-001', 'GDSE', '420000', '2 Years', 'SUB-002'),
       ('COU-002', 'CMJD', '60000', '6 Month', 'SUB-004'),
       ('COU-003', 'DEP', '120000', ' 6 Month', 'SUB-005'),
       ('COU-004', 'RMAD', '40000', '6 Month', 'SUB-003'),
       ('COU-005', 'Web Development', '20000', '6 Month', 'SUB-001');

DROP TABLE IF EXISTS Intake;
CREATE TABLE IF NOT EXISTS Intake
(
    intakeId    VARCHAR(45),
    startDate   DATE,
    description VARCHAR(45),
    courseId    VARCHAR(45),
    CONSTRAINT PRIMARY KEY (intakeId, courseId),
    CONSTRAINT FOREIGN KEY (courseId) REFERENCES Course (courseId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Intake;

USE IJSE;
INSERT INTO Intake
VALUES ('INT-001', '2022-06-01', 'Registration are Opened.', 'COU-001'),
       ('INT-002', '2022-07-30', 'Registration are Opened.', 'COU-002'),
       ('INT-003', '2022-07-30', 'Registration are Opened.', 'COU-003'),
       ('INT-004', '2022-08-01', 'Registration are Opened.', 'COU-004'),
       ('INT-005', '2022-12-30', 'Registration are Opened.', 'COU-005');


DROP TABLE IF EXISTS Registration;
CREATE TABLE IF NOT EXISTS Registration
(
    registrationId VARCHAR(45),
    regDate        DATE,
    studentId      VARCHAR(45),
    intakeId       VARCHAR(45),
    CONSTRAINT PRIMARY KEY (registrationId, studentId, intakeId),
    CONSTRAINT FOREIGN KEY (studentId) REFERENCES Student (studentId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (intakeId) REFERENCES Intake (intakeId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Registration;

USE IJSE;
INSERT INTO Registration
VALUES ('REG-001', '2022-05-11', 'STU-002', 'INT-001'),
       ('REG-002', '2022-06-16', 'STU-001', 'INT-002'),
       ('REG-003', '2022-06-19', 'STU-003', 'INT-003'),
       ('REG-004', '2022-07-21', 'STU-004', 'INT-004'),
       ('REG-005', '2022-11-25', 'STU-005', 'INT-005');


DROP TABLE IF EXISTS Payment;
CREATE TABLE IF NOT EXISTS Payment
(
    paymentId      VARCHAR(45),
    date           DATE,
    cost           DOUBLE,
    registrationId VARCHAR(45),
    CONSTRAINT PRIMARY KEY (paymentId, registrationId),
    CONSTRAINT FOREIGN KEY (registrationId) REFERENCES Registration (registrationId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Payment;

USE IJSE;
INSERT INTO Payment
VALUES ('PAY-001', '2022-05-11', '420000', 'REG-001'),
       ('PAY-002', '2022-06-16', '60000', 'REG-002'),
       ('PAY-003', '2022-06-19', '120000', 'REG-003'),
       ('PAY-004', '2022-07-21', '40000', 'REG-004'),
       ('PAY-005', '2022-11-25', '20000', 'REG-005');