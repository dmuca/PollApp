CREATE TABLE appuser (
  user_hash int PRIMARY KEY,
  name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  password_hash varchar(100) UNIQUE NOT NULL,
  email varchar(100) UNIQUE NOT NULL
);

CREATE TABLE poll (
  poll_id SERIAL PRIMARY KEY,
  owner_user_id int NOT NULL,
  name varchar(200) NOT NULL
);

CREATE TABLE question (
  question_id SERIAL PRIMARY KEY,
  poll_id int NOT NULL,
  question varchar(200) NOT NULL
);

CREATE TABLE answer (
  answer_id SERIAL PRIMARY KEY,
  question_id int NOT NULL,
  subquestion varchar(300) NOT NULL
);

CREATE TABLE useranswer (
  user_answer_id SERIAL PRIMARY KEY,
  user_id int NOT NULL,
  answer_chosen int NOT NULL
);

ALTER TABLE Question ADD FOREIGN KEY (poll_id) REFERENCES Poll (poll_id);
ALTER TABLE Answer ADD FOREIGN KEY (question_id) REFERENCES Question (question_id);
ALTER TABLE UserAnswer ADD FOREIGN KEY (answer_chosen) REFERENCES Answer (answer_id);
