CREATE TABLE "User" (
  "user_hash" SERIAL UNIQUE PRIMARY KEY,
  "name" varchar(50) NOT NULL,
  "last_name" varchar(50) NOT NULL,
  "password_hash" varchar(100) UNIQUE NOT NULL,
  "email" varchar(100) UNIQUE NOT NULL
);

CREATE TABLE "Poll" (
  "poll_id" SERIAL UNIQUE PRIMARY KEY,
  "owner_user_id" int NOT NULL,
  "name" varchar(200) NOT NULL
);

CREATE TABLE "Question" (
  "question_id" SERIAL PRIMARY KEY,
  "poll_id" int NOT NULL,
  "question" varchar(200) NOT NULL
);

CREATE TABLE "Answer" (
  "answer_id" SERIAL PRIMARY KEY,
  "question_id" int NOT NULL,
  "subquestion" varchar(300) NOT NULL
);

CREATE TABLE "UserAnswer" (
  "user_answer_id" SERIAL PRIMARY KEY,
  "user_id" int NOT NULL,
  "answer_chosen" int NOT NULL
);

ALTER TABLE "Poll" ADD FOREIGN KEY ("owner_user_id") REFERENCES "User" ("user_hash");

ALTER TABLE "Question" ADD FOREIGN KEY ("poll_id") REFERENCES "Poll" ("poll_id");

ALTER TABLE "UserAnswer" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("user_hash");

ALTER TABLE "Answer" ADD FOREIGN KEY ("question_id") REFERENCES "Question" ("question_id");

ALTER TABLE "UserAnswer" ADD FOREIGN KEY ("answer_chosen") REFERENCES "Answer" ("answer_id");
