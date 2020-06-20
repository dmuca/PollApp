package pl.com.muca.server.dao.useranswer;

import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.dao.poll.PollDaoImpl;
import pl.com.muca.server.dao.question.QuestionDao;
import pl.com.muca.server.dao.question.QuestionDaoImpl;
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.dao.user.UserDaoImpl;
import pl.com.muca.server.dao.userswhoansweredpoll.UserWhoAnsweredPollDao;
import pl.com.muca.server.dao.userswhoansweredpoll.UserWhoAnsweredPollDaoImpl;
import pl.com.muca.server.entity.Answer;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.Question;
import pl.com.muca.server.entity.UserAnswer;

@Repository
public class UserAnswerDaoImpl implements UserAnswerDao {
  private final NamedParameterJdbcTemplate template;
  private final UserDao userDao;
  private final QuestionDao questionDao;
  private final UserWhoAnsweredPollDao userWhoAnsweredPollDao;

  public UserAnswerDaoImpl(NamedParameterJdbcTemplate template, PollDaoImpl pollDao) {
    this.template = template;
    this.userDao = new UserDaoImpl(template);
    this.questionDao = new QuestionDaoImpl(template);
    this.userWhoAnsweredPollDao =
        new UserWhoAnsweredPollDaoImpl(template, pollDao, userDao);
  }

  @Override
  public int saveUserAnswers(UserAnswer[] userAnswers, String token) throws Exception {
    for (UserAnswer userAnswer : userAnswers) {
      saveUserAnswer(userAnswer, token);
    }
    return this.userWhoAnsweredPollDao
        .insertUserWhoAnsweredPoll(userAnswers, token);
  }

  @Override
  public void saveUserAnswer(UserAnswer userAnswer, String token) throws Exception {
    String userIdHash = this.userDao.getUserHashIdFromToken(token);
    final String sql =
        "INSERT INTO useranswer(user_id_hash, question_id, answer_chosen) "
            + "VALUES (:UserIdHash, :QuestionId, :AnswerChosen)";
    SqlParameterSource param =
        new MapSqlParameterSource()
            .addValue("UserIdHash", userIdHash)
            .addValue("QuestionId", userAnswer.getQuestionId())
            .addValue("AnswerChosen", userAnswer.getAnswerChosen());
    template.update(sql, param);
  }

  @Override
  public Answer[] getAnswersCounterForPollOwner(int questionId, String token) throws Exception {
    String sql =
        "SELECT answer.answer_id, answer.question_id, answer.content "
            + "FROM answer "
            + "WHERE answer.question_id = :QuestionId;";
    SqlParameterSource answerParameters =
        new MapSqlParameterSource().addValue("QuestionId", questionId);
    Answer[] answers =
        template.query(sql, answerParameters, new AnswerRowMapper()).toArray(Answer[]::new);

    boolean isUserOwnerOfTheQuestion =
        this.questionDao.isUserACreatorOfTheQuestion(token, questionId);

    for (Answer answer : answers) {
      answer.setMarkedByUser(isAnswerMarkedByUser(answer.getAnswerId(), token));
      if (isUserOwnerOfTheQuestion) {
        answer.setAnswersCounter(countAnswers(answer.getAnswerId()));
      }
    }
    return answers;
  }

  @Override
  public boolean isAnswerMarkedByUser(int answerId, String token) throws Exception {
    String userIdHash = this.userDao.getUserHashIdFromToken(token);
    String sql =
        "SELECT COUNT(*) AS markedCounter "
            + "FROM useranswer "
            + "INNER JOIN answer "
            + "ON useranswer.answer_chosen = :AnswerId "
            + "WHERE useranswer.user_id_hash = :UserIdHash";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource()
            .addValue("AnswerId", answerId)
            .addValue("UserIdHash", userIdHash);
    Integer value = template.queryForObject(sql, sqlParameterSource, Integer.class);
    return Optional.ofNullable(value).orElse(0) > 0;
  }

  @Override
  public int countAnswers(int answerId) {
    String sql = "SELECT COUNT(*) FROM useranswer WHERE answer_chosen = :AnswerId;";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource().addValue("AnswerId", answerId);
    Optional<Integer> howManyCheckedAnswers =
        Optional.ofNullable(template.queryForObject(sql, sqlParameterSource, Integer.class));
    return howManyCheckedAnswers.orElse(0);
  }

  @Override
  public Integer getLatestAnswerId() {
    final String latestAnswerIdSql = "SELECT MAX(answer.answer_id) " + "FROM answer;";
    return Optional.ofNullable(
            template.queryForObject(latestAnswerIdSql, new MapSqlParameterSource(), Integer.class))
        .orElse(0);
  }

  @Override
  public void insertAnswerTableData(Poll poll) {
    final String sql =
        "INSERT INTO answer(answer_id, question_id, content) "
            + "VALUES (:answer_id, :question_id, :content)";

    for (Question question : poll.getQuestions()) {
      for (Answer answer : question.getAnswers()) {
        SqlParameterSource param =
            new MapSqlParameterSource()
                .addValue("answer_id", answer.getAnswerId())
                .addValue("question_id", answer.getQuestionId())
                .addValue("content", answer.getContent());
        template.update(sql, param);
      }
    }
  }

  @Override
  public UserAnswer[] getUserAnswersForPoll(String userToken, int pollId) throws Exception {
    final String sql =
        "SELECT * FROM useranswer "
            + "INNER JOIN question "
            + "ON question.question_id = useranswer.question_id "
            + "WHERE useranswer.user_id_hash = :UserIdHash "
            + "AND question.poll_id = :PollId";
    SqlParameterSource parameters =
        new MapSqlParameterSource()
            .addValue("UserIdHash", this.userDao.getUserHashIdFromToken(userToken))
            .addValue("PollId", pollId);
    return template.query(sql, parameters, new UserANswerMapper()).toArray(UserAnswer[]::new);
  }
}
