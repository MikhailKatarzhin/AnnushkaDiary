package anndy.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value =
            "SELECT * FROM \"Answer\" " +
                    "ORDER BY id " +
                    "LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Answer> getAnswersByLimitOffset(long limit, long offset);

    @Query(value =
            "SELECT * FROM \"Answer\" " +
                    " WHERE description LIKE ?3 " +
                    " ORDER BY id " +
                    " LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Answer> getAnswersByLimitOffsetAndDescriptionPart(long limit, long offset, String descriptionPart);

    @Query(value =
        "SELECT * FROM \"Answer\" WHERE description = ?1 ",
        nativeQuery = true
    )
    Answer getAnswerByDescriptionEquals(String description);

    @Query(value =
            "SELECT * FROM \"Answer\" a WHERE a.id IN (SELECT answer_id FROM \"Task_Answer\" WHERE task_id = ?) ORDER BY a.id LIMIT ? OFFSET ?",
            nativeQuery = true
    )
    ArrayList<Answer> getAnswersByLimitOffsetAndTaskId(long taskId, long limit, long offset);

    @Query(value =
            "SELECT COUNT(*) FROM \"Answer\" " +
                    "WHERE id IN (" +
                    "SELECT answer_id FROM \"Task_Answer\" " +
                    "WHERE task_id = ?" +
                    ") ",
            nativeQuery = true
    )
    Long countAnswerByTaskId(long taskId);

    @Query(value =
            "SELECT * FROM \"Answer\" " +
                    "WHERE id NOT IN (" +
                    "SELECT answer_id FROM \"Task_Answer\"" +
                    "WHERE task_id=?3" +
                    ")" +
                    "ORDER BY id " +
                    "LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Answer> getAnswersByLimitOffsetAndNotTaskId(long limit, long offset, long taskId);

    @Query(value =
            "SELECT COUNT(*) FROM \"Answer\" " +
                    "WHERE id NOT IN (" +
                    "SELECT answer_id FROM \"Task_Answer\"" +
                    "WHERE task_id=?" +
                    ")",
            nativeQuery = true
    )
    Long countAnswerByNotTaskId(long taskId);

    @Query(value =
            "SELECT * FROM \"Answer\" " +
                    "INNER JOIN \"Task_Correct_Answer\" ta on \"Answer\".id = ta.answer_id " +
                    "WHERE task_id = ?3 " +
                    "ORDER BY id " +
                    "LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Answer> getCorrectAnswersByLimitOffsetAndTaskId(long limit, long offset, long taskId);

    @Query(value =
            "SELECT COUNT(*) FROM \"Answer\" " +
                    "INNER JOIN \"Task_Correct_Answer\" ta on \"Answer\".id = ta.answer_id " +
                    "WHERE task_id = ? ",
            nativeQuery = true
    )
    Long countCorrectAnswerByTaskId(long taskId);
}