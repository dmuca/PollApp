SELECT COUNT(*) FROM question
INNER JOIN poll
ON poll.poll_id = question.poll_id
WHERE question.question_id = :QuestionId AND poll.owner_user_id = :UserId;
