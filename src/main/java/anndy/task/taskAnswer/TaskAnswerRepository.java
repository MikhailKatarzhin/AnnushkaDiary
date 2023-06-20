package anndy.task.taskAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskAnswerRepository extends JpaRepository<TaskAnswer, Long> {
    @Query(
            value = "SELECT * FROM \"Task_Answer\" WHERE answer_id = ?",
            nativeQuery = true
    )
    TaskAnswer findByAnswerId(long answerId);

    @Query(
            value = "SELECT * FROM \"Task_Answer\" WHERE task_id = ?",
            nativeQuery = true
    )
    TaskAnswer findByTaskId(long taskId);

    @Query(
            value = "SELECT * FROM \"Task_Answer\" WHERE answer_id = ?1 AND task_id = ?2",
            nativeQuery = true
    )
    TaskAnswer findByAnswerIdAndTaskId(long answerId, long taskId);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM \"Task_Answer\" WHERE answer_id = ?1 AND task_id = ?2",
            nativeQuery = true
    )
    void deleteByAnswerIdAndTaskId(long answerId, long taskId);
}