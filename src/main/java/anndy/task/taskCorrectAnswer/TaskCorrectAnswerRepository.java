package anndy.task.taskCorrectAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskCorrectAnswerRepository extends JpaRepository<TaskCorrectAnswer, Long> {
    @Query(
            value = "SELECT * FROM \"Task_Correct_Answer\" WHERE answer_id = ?",
            nativeQuery = true
    )
    TaskCorrectAnswer findByAnswerId(long answerId);

    @Query(
            value = "SELECT * FROM \"Task_Correct_Answer\" WHERE task_id = ?",
            nativeQuery = true
    )
    TaskCorrectAnswer findByTaskId(long taskId);

    @Query(
            value = "SELECT * FROM \"Task_Correct_Answer\" WHERE answer_id = ?1 AND task_id = ?2",
            nativeQuery = true
    )
    TaskCorrectAnswer findByAnswerIdAndTaskId(long answerId, long taskId);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM \"Task_Correct_Answer\" WHERE answer_id = ?1 AND task_id = ?2",
            nativeQuery = true
    )
    void deleteByAnswerIdAndTaskId(long answerId, long taskId);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM \"Task_Correct_Answer\" WHERE task_id = ?",
            nativeQuery = true
    )
    void deleteByTaskId(long taskId);
}