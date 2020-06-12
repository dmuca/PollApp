SELECT COUNT(*) AS markedCounter
FROM useranswer
INNER JOIN answer
ON useranswer.answer_chosen = :AnswerId
WHERE useranswer.user_id_hash = :UserId AND useranswer.question_id;

SELECT COUNT(*) AS markedCounter
FROM useranswer
INNER JOIN answer
ON useranswer.answer_chosen = :AnswerId
WHERE useranswer.user_id_hash = :UserId;


SELECT COUNT(*) AS markedCounter
FROM useranswer
INNER JOIN answer
ON useranswer.answer_chosen = 2
WHERE useranswer.user_id_hash = 2;
