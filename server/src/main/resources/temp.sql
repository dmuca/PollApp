SELECT * FROM poll
INNER JOIN session
ON session.access_token = :SessionToken
WHERE poll.owner_user_id = session.user_id_hash;


SELECT * FROM poll
INNER JOIN session
ON session.access_token = 'd28f74e4-e77b-4ecb-b494-cad9bf5f4e73'
WHERE poll.owner_user_id = session.user_id_hash;

