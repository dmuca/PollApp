-- Fill users table.
INSERT INTO appuser(user_id_hash, name, last_name, password_hash, email) VALUES (1, 'Karol', 'Wojciechowski', '1', 'KarolW@gmail.com');
INSERT INTO appuser(user_id_hash, name, last_name, password_hash, email) VALUES (2, 'Bartek', 'Mazurkiewicz', '2', 'Bartosz@gmail.com');
INSERT INTO appuser(user_id_hash, name, last_name, password_hash, email) VALUES (3, 'Joanna', 'Kowal', '3', 'JoannaKow@gmail.com');
INSERT INTO appuser(user_id_hash, name, last_name, password_hash, email) VALUES (4, 'Katarzyna', 'Grzeszczuk', '4', 'KatarzynaG@gmail.com');

-- Poll nr.1
INSERT INTO poll(poll_id, owner_user_id, name) VALUES (1, 1, 'Wrażenia z użytkownika aplikacji PollApp');
-- Question nr.1
INSERT INTO question(question_id, poll_id, content) VALUES (1, 1, 'Jak oceniasz aplikację?');
INSERT INTO answer(answer_id, question_id, content) VALUES (1, 1, 'Super');
INSERT INTO answer(answer_id, question_id, content) VALUES (2, 1, 'Odlotowa');
INSERT INTO answer(answer_id, question_id, content) VALUES (3, 1, 'Świetna');
INSERT INTO answer(answer_id, question_id, content) VALUES (4, 1, 'Najlepsza');
INSERT INTO answer(answer_id, question_id, content) VALUES (5, 1, 'Niesamowita');
INSERT INTO useranswer(user_id, question_id, answer_chosen) VALUES (1, 1, 3);
INSERT INTO useranswer(user_id, question_id, answer_chosen) VALUES (2, 1, 1);
INSERT INTO useranswer(user_id, question_id, answer_chosen) VALUES (3, 1, 5);
-- Question nr.2
INSERT INTO question(question_id, poll_id, content) VALUES (2, 1, 'Czy poleciłbyś aplikacje znajomym?');
INSERT INTO answer(answer_id, question_id, content) VALUES (6, 2, 'Tak');
INSERT INTO answer(answer_id, question_id, content) VALUES (7, 2, 'Owszem');
INSERT INTO answer(answer_id, question_id, content) VALUES (8, 2, 'Polecę');
INSERT INTO useranswer(user_id, question_id, answer_chosen) VALUES (1, 2, 6);
INSERT INTO useranswer(user_id, question_id, answer_chosen) VALUES (2, 2, 6);
INSERT INTO useranswer(user_id, question_id, answer_chosen) VALUES (3, 2, 8);
-- Question nr.3
INSERT INTO question(question_id, poll_id, content) VALUES (3, 1, 'Z jakiego powodu zainstalowałeś aplikację?');
INSERT INTO answer(answer_id, question_id, content) VALUES (9, 3, 'Potrzebuję do nauki');
INSERT INTO answer(answer_id, question_id, content) VALUES (10, 3, 'Potrzebuję do pracy');
INSERT INTO answer(answer_id, question_id, content) VALUES (11, 3, 'Damian mi kazał');
INSERT INTO answer(answer_id, question_id, content) VALUES (12, 3, 'Z ciekawości');
INSERT INTO answer(answer_id, question_id, content) VALUES (13, 3, 'To był missclick');
INSERT INTO useranswer(user_id, question_id, answer_chosen) VALUES (1, 3, 11);
INSERT INTO useranswer(user_id, question_id, answer_chosen) VALUES (2, 3, 9);
INSERT INTO useranswer(user_id, question_id, answer_chosen) VALUES (3, 3, 12);
