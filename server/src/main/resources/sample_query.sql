SELECT appuser.name     AS imię,
       poll.name        AS ankieta,
       question.content AS pytanie,
       answer.content   AS odpowiedź
FROM poll
         INNER JOIN question
                    ON poll.poll_id = question.poll_id
         INNER JOIN answer
                    ON question.question_id = answer.question_id
         INNER JOIN useranswer
                    ON answer.answer_id = useranswer.answer_chosen
         INNER JOIN appuser
                    ON useranswer.user_id = appuser.user_id_hash
ORDER BY appuser.name, poll.name, question.content, answer.content;
