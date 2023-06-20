package anndy.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}