package anndy.training.trainingTask;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ITrainingTaskService {
    TrainingTask getByTrainingIdAndTaskId(long trainingId, long taskId);

    @Modifying
    @Transactional
    void deleteByTrainingIdAndTaskId(long trainingId, long taskId);

    TrainingTask save(long trainingId, long taskId);
}
