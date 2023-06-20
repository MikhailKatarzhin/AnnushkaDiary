package anndy.task.taskCorrectAnswer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "\"Task_Correct_Answer\"")
@IdClass(TaskAnswerId.class)
public class TaskCorrectAnswer {

    public TaskCorrectAnswer(long answerId, long taskId) {
        this.answerId = answerId;
        this.taskId = taskId;
    }

    @Id
    @Column(name = "answer_id")
    private Long answerId;

    @Id
    @Column(name = "task_id")
    private Long taskId;
}

@Getter
@Setter
@RequiredArgsConstructor
class TaskAnswerId implements Serializable {
    private Long answerId;
    private Long taskId;

    public TaskAnswerId(long answerId, long taskId) {
        this.answerId = answerId;
        this.taskId = taskId;
    }
}