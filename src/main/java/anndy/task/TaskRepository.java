package anndy.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}