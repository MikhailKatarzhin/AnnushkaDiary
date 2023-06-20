package anndy.task.taskAnswer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskAnswerService implements ITaskAnswerService {
    private final TaskAnswerRepository taskAnswerRepository;

    public TaskAnswerService(TaskAnswerRepository taskAnswerRepository) {
        this.taskAnswerRepository = taskAnswerRepository;
    }

    @Override
    public TaskAnswer getByAnswerIdAndTaskId(long answerId, long taskId){
        return taskAnswerRepository.findByAnswerIdAndTaskId(answerId, taskId);
    }

    @Override
    @Modifying
    @Transactional
    public void deleteByAnswerIdAndTaskId(long answerId, long taskId){
        if (getByAnswerIdAndTaskId(answerId, taskId) != null)
            taskAnswerRepository.deleteByAnswerIdAndTaskId(answerId, taskId);
    }

    @Override
    public TaskAnswer save(long answerId, long taskId){
        return taskAnswerRepository.save(new TaskAnswer(answerId, taskId));
    }
}
