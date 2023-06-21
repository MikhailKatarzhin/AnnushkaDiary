package anndy.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query(value =
            "SELECT * FROM \"Training\" " +
                    "ORDER BY id " +
                    "LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Training> getTrainingsByLimitOffset(long limit, long offset);

    @Query(value =
            "SELECT * FROM \"Training\" " +
                    " WHERE description LIKE ?3 " +
                    " ORDER BY id " +
                    " LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Training> getTrainingsByLimitOffsetAndDescriptionPart(long limit, long offset, String descriptionPart);

    @Query(value =
            "SELECT * FROM \"Training\" " +
                    " WHERE title LIKE ?3 " +
                    " ORDER BY id " +
                    " LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Training> getTrainingsByLimitOffsetAndTitlePart(long limit, long offset, String titlePart);

    @Query(value =
            "SELECT * FROM \"Training\" WHERE description = ?1 ",
            nativeQuery = true
    )
    Training getTrainingByDescriptionEquals(String description);

    @Query(value =
            "SELECT * FROM \"Training\" WHERE title = ?1 ",
            nativeQuery = true
    )
    Training getTrainingByTitleEquals(String title);
}