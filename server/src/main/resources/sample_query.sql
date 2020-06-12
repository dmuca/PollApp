SELECT question.content, question.poll_id, question.question_id FROM question
WHERE question.poll_id = :PollId;
