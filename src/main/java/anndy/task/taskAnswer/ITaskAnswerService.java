package anndy.task.taskAnswer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ITaskAnswerService {
    TaskAnswer getByAnswerIdAndTaskId(long answerId, long taskId);

    @Modifying
    @Transactional
    void deleteByAnswerIdAndTaskId(long answerId, long taskId);

    TaskAnswer save(long answerId, long taskId);
}
