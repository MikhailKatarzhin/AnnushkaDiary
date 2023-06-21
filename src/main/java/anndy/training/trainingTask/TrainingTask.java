package anndy.training.trainingTask;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "\"Training_Task\"")
@IdClass(TrainingTaskId.class)
public class TrainingTask {

    public TrainingTask(long trainingId, long taskId) {
        this.trainingId = trainingId;
        this.taskId = taskId;
    }

    @Id
    @Column(name = "training_id")
    private Long trainingId;

    @Id
    @Column(name = "task_id")
    private Long taskId;
}

@Getter
@Setter
@RequiredArgsConstructor
class TrainingTaskId implements Serializable {
    private Long trainingId;
    private Long taskId;

    public TrainingTaskId(long trainingId, long taskId) {
        this.trainingId = trainingId;
        this.taskId = taskId;
    }
}