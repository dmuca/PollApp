SELECT answer.answer_id, answer.question_id, answer.content FROM answer
WHERE answer.question_id = :QuestionId;
