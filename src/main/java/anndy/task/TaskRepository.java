package anndy.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value =
            "SELECT * FROM \"Task\" " +
                    "ORDER BY id " +
                    "LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Task> getTasksByLimitOffset(long limit, long offset);

    @Query(value =
            "SELECT * FROM \"Task\" " +
                    " WHERE description LIKE ?3 " +
                    " ORDER BY id " +
                    " LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Task> getTasksByLimitOffsetAndDescriptionPart(long limit, long offset, String descriptionPart);

    @Query(value =
        "SELECT * FROM \"Task\" WHERE description = ?1 ",
        nativeQuery = true
    )
    Task getTaskByDescriptionEquals(String description);

    @Query(value =
            "SELECT * FROM \"Task\" a WHERE a.id IN (SELECT task_id FROM \"Training_Task\" WHERE training_id = ?) ORDER BY a.id LIMIT ? OFFSET ?",
            nativeQuery = true
    )
    ArrayList<Task> getTasksByLimitOffsetAndTrainingId(long trainingId, long limit, long offset);

    @Query(value =
            "SELECT COUNT(*) FROM \"Task\" " +
                    "WHERE id IN (" +
                    "SELECT task_id FROM \"Training_Task\" " +
                    "WHERE training_id = ?" +
                    ") ",
            nativeQuery = true
    )
    Long countTaskByTrainingId(long trainingId);

    @Query(value =
            "SELECT * FROM \"Task\" " +
                    "WHERE id NOT IN (" +
                    "SELECT task_id FROM \"Training_Task\"" +
                    "WHERE training_id=?3" +
                    ")" +
                    "ORDER BY id " +
                    "LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Task> getTasksByLimitOffsetAndNotTrainingId(long limit, long offset, long trainingId);

    @Query(value =
            "SELECT COUNT(*) FROM \"Task\" " +
                    "WHERE id NOT IN (" +
                    "SELECT task_id FROM \"Training_Task\"" +
                    "WHERE training_id=?" +
                    ")",
            nativeQuery = true
    )
    Long countTaskByNotTrainingId(long trainingId);
}