package anndy.training.trainingTask;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainingTaskService implements ITrainingTaskService {
    private final TrainingTaskRepository trainingTaskRepository;

    public TrainingTaskService(TrainingTaskRepository trainingTaskRepository) {
        this.trainingTaskRepository = trainingTaskRepository;
    }

    @Override
    public TrainingTask getByTrainingIdAndTaskId(long trainingId, long taskId){
        return trainingTaskRepository.findByTrainingIdAndTaskId(trainingId, taskId);
    }

    @Override
    @Modifying
    @Transactional
    public void deleteByTrainingIdAndTaskId(long trainingId, long taskId){
        if (getByTrainingIdAndTaskId(trainingId, taskId) != null)
            trainingTaskRepository.deleteByTrainingIdAndTaskId(trainingId, taskId);
    }

    @Override
    public TrainingTask save(long trainingId, long taskId){
        return trainingTaskRepository.save(new TrainingTask(trainingId, taskId));
    }
}
