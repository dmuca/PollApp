-- SELECT appuser.name, question.content, answer.content
SELECT question.content, answer.content
FROM question
INNER JOIN answer answer
on question.question_id = answer.question_id;

