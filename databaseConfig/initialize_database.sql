USE database_project;

CREATE TABLE employee (
	employee_id INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    employee_fname VARCHAR(128) NOT NULL,
    employee_lname VARCHAR(128) NOT NULL,
	employee_email VARCHAR(360) NOT NULL
);

CREATE TABLE plan (
	plan_id INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    plan_type VARCHAR(20) NOT NULL
);

CREATE TABLE student (
	client_id INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    client_fname VARCHAR(128) NOT NULL,
    client_lname VARCHAR(128) NOT NULL,
	client_email VARCHAR(360) NOT NULL,
    client_plan INT UNSIGNED NOT NULL
);

CREATE TABLE times (
	time_id INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    time_time TIME NOT NULL
);

CREATE TABLE lesson (
	lesson_id INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    lesson_studentId INT NOT NULL,
    lesson_employeeId INT NOT NULL,
    lesson_date DATE NOT NULL,
    lesson_time INT UNSIGNED NOT NULL
);

