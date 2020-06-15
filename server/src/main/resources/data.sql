-- Clear tables
DELETE FROM session WHERE TRUE=TRUE;
DELETE FROM appuser WHERE TRUE=TRUE;
DELETE FROM useranswer WHERE TRUE=TRUE;
DELETE FROM answer WHERE TRUE=TRUE;
DELETE FROM question WHERE TRUE=TRUE;
DELETE FROM poll WHERE TRUE=TRUE;

-- Fill users table.
INSERT INTO appuser(user_id, name, last_name, password, email) VALUES (3242531, 'Karol', 'Wojciechowski', 'karol', 'KarolW@gmail.com');
INSERT INTO appuser(user_id, name, last_name, password, email) VALUES (1164223, 'Bartek', 'Mazurkiewicz', 'bartek', 'Bartosz@gmail.com');
INSERT INTO appuser(user_id, name, last_name, password, email) VALUES (73453445, 'Joanna', 'Kowal', 'joanna', 'JoannaKow@gmail.com');
INSERT INTO appuser(user_id, name, last_name, password, email) VALUES (21213213, 'Katarzyna', 'Grzeszczuk', 'katarzyna', 'KatarzynaG@gmail.com');

-- Poll nr.1
INSERT INTO poll(poll_id, owner_user_id, name) VALUES (1, 3242531, 'Wrażenia z użytkownika aplikacji PollApp');
-- Question nr.1
INSERT INTO question(question_id, poll_id, content) VALUES (1, 1, 'Jak oceniasz aplikację?');
INSERT INTO answer(answer_id, question_id, content) VALUES (1, 1, 'Super');
INSERT INTO answer(answer_id, question_id, content) VALUES (2, 1, 'Odlotowa');
INSERT INTO answer(answer_id, question_id, content) VALUES (3, 1, 'Świetna');
INSERT INTO answer(answer_id, question_id, content) VALUES (4, 1, 'Najlepsza');
INSERT INTO answer(answer_id, question_id, content) VALUES (5, 1, 'Niesamowita');
-- Question nr.2
INSERT INTO question(question_id, poll_id, content) VALUES (2, 1, 'Czy poleciłbyś aplikacje znajomym?');
INSERT INTO answer(answer_id, question_id, content) VALUES (6, 2, 'Tak');
INSERT INTO answer(answer_id, question_id, content) VALUES (7, 2, 'Owszem');
INSERT INTO answer(answer_id, question_id, content) VALUES (8, 2, 'Polecę');
-- Question nr.3
INSERT INTO question(question_id, poll_id, content) VALUES (3, 1, 'Z jakiego powodu zainstalowałeś aplikację?');
INSERT INTO answer(answer_id, question_id, content) VALUES (9, 3, 'Potrzebuję do nauki');
INSERT INTO answer(answer_id, question_id, content) VALUES (10, 3, 'Potrzebuję do pracy');
INSERT INTO answer(answer_id, question_id, content) VALUES (11, 3, 'Damian mi kazał');
INSERT INTO answer(answer_id, question_id, content) VALUES (12, 3, 'Z ciekawości');
INSERT INTO answer(answer_id, question_id, content) VALUES (13, 3, 'To był missclick');

-- Poll nr.2
INSERT INTO poll(poll_id, owner_user_id, name) VALUES (2, 1164223, 'Moje miasto - Kraków');
-- Question nr.1
INSERT INTO question(question_id, poll_id, content) VALUES (4, 2, 'Co myślisz o swoim mieście?');
INSERT INTO answer(answer_id, question_id, content) VALUES (14, 4, 'Super');
INSERT INTO answer(answer_id, question_id, content) VALUES (15, 4, 'Odlotowe');
INSERT INTO answer(answer_id, question_id, content) VALUES (16, 4, 'Świetne');
INSERT INTO answer(answer_id, question_id, content) VALUES (17, 4, 'Najlepsze');
INSERT INTO answer(answer_id, question_id, content) VALUES (18, 4, 'Niesamowite');
-- Question nr.2
INSERT INTO question(question_id, poll_id, content) VALUES (5, 2, 'Czy poleciłbyś Kraków znajomym?');
INSERT INTO answer(answer_id, question_id, content) VALUES (19, 5, 'Tak');
INSERT INTO answer(answer_id, question_id, content) VALUES (20, 5, 'Owszem');
INSERT INTO answer(answer_id, question_id, content) VALUES (21, 5, 'Polecę');

-- -- DROP ALL TABLES
-- DROP TABLE useranswer;
-- DROP TABLE answer;
-- DROP TABLE question;
-- DROP TABLE poll;
-- DROP TABLE appuser;
-- DROP TABLE session;
