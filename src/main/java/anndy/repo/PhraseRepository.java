package anndy.repo;

import anndy.model.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    @Query(value =
            "SELECT id FROM \"Phrase\" ORDER BY random() LIMIT 1"
            , nativeQuery = true
    )
    Long findRandomPhraseId();
}