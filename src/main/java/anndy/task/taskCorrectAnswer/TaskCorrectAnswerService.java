package anndy.task.taskCorrectAnswer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskCorrectAnswerService implements ITaskCorrectAnswerService {
    private final TaskCorrectAnswerRepository taskCorrectAnswerRepository;

    public TaskCorrectAnswerService(TaskCorrectAnswerRepository taskCorrectAnswerRepository) {
        this.taskCorrectAnswerRepository = taskCorrectAnswerRepository;
    }

    @Override
    public TaskCorrectAnswer getByAnswerIdAndTaskId(long answerId, long taskId){
        return taskCorrectAnswerRepository.findByAnswerIdAndTaskId(answerId, taskId);
    }

    @Override
    public TaskCorrectAnswer getByTaskId(long taskId){
        return taskCorrectAnswerRepository.findByTaskId(taskId);
    }

    @Override
    @Modifying
    @Transactional
    public void deleteByTaskId(long taskId){
        if (getByTaskId(taskId) != null)
            taskCorrectAnswerRepository.deleteByTaskId(taskId);
    }

    @Override
    public TaskCorrectAnswer save(long answerId, long taskId){
        return taskCorrectAnswerRepository.save(new TaskCorrectAnswer(answerId, taskId));
    }
}
