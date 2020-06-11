
SELECT COUNT(*) AS howManyAnswers
FROM useranswer
         INNER JOIN question
                    ON useranswer.question_id = question.question_id
         INNER JOIN session
                    ON session.access_token = :SessionToken
WHERE useranswer.user_id=session.user_id_hash AND question.poll_id=:PollId;



SELECT COUNT(*) AS howManyAnswers
FROM useranswer
         INNER JOIN question
                    ON useranswer.question_id = question.question_id
         INNER JOIN session
                    ON session.access_token = '05773786-bfa8-4eda-b392-b5f819ddc50e'
WHERE useranswer.user_id=1 AND question.poll_id=1;
