package anndy.training.trainingTask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TrainingTaskRepository extends JpaRepository<TrainingTask, Long> {
    @Query(
            value = "SELECT * FROM \"Training_Task\" WHERE training_id = ?",
            nativeQuery = true
    )
    TrainingTask findByTrainingId(long trainingId);

    @Query(
            value = "SELECT * FROM \"Training_Task\" WHERE task_id = ?",
            nativeQuery = true
    )
    TrainingTask findByTaskId(long taskId);

    @Query(
            value = "SELECT * FROM \"Training_Task\" WHERE training_id = ?1 AND task_id = ?2",
            nativeQuery = true
    )
    TrainingTask findByTrainingIdAndTaskId(long training, long taskId);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM \"Training_Task\" WHERE training_id = ?1 AND task_id = ?2",
            nativeQuery = true
    )
    void deleteByTrainingIdAndTaskId(long trainingId, long taskId);
}