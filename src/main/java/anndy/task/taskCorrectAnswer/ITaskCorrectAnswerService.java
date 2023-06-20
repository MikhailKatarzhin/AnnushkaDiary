package anndy.task.taskCorrectAnswer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ITaskCorrectAnswerService {
    TaskCorrectAnswer getByAnswerIdAndTaskId(long answerId, long taskId);

    TaskCorrectAnswer getByTaskId(long taskId);

    @Modifying
    @Transactional
    void deleteByTaskId(long taskId);

    TaskCorrectAnswer save(long answerId, long taskId);
}
