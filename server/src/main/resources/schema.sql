CREATE TABLE appuser (
  user_id_hash int PRIMARY KEY,
  name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  password_hash varchar(100) NOT NULL,
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
  content varchar(200) NOT NULL
);

CREATE TABLE answer (
  answer_id SERIAL PRIMARY KEY,
  question_id int NOT NULL,
  content varchar(300) NOT NULL
);

CREATE TABLE useranswer (
  user_id int,
  question_id int,
  answer_chosen int,
  PRIMARY KEY (user_id, question_id)
);

CREATE TABLE session (
  user_id SERIAL PRIMARY KEY,
  access_token UUID DEFAULT uuid_generate_v4() NOT NULL UNIQUE,
  granted TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp
);

ALTER TABLE question ADD FOREIGN KEY (poll_id) REFERENCES poll (poll_id);
ALTER TABLE answer ADD FOREIGN KEY (question_id) REFERENCES question (question_id);
ALTER TABLE useranswer ADD FOREIGN KEY (question_id) REFERENCES question (question_id);
ALTER TABLE useranswer ADD FOREIGN KEY (answer_chosen) REFERENCES answer (answer_id);
ALTER TABLE session ADD FOREIGN KEY (user_id) REFERENCES appuser (user_id_hash);
