package anndy.phrase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    @Query(value =
            "SELECT id FROM \"Phrase\" ORDER BY random() LIMIT 1"
            , nativeQuery = true
    )
    Long findRandomPhraseId();

    @Query(value =
            "SELECT * FROM \"Phrase\" " +
                    "ORDER BY id " +
                    "LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Phrase> getPhrasesByLimitOffset(long limit, long offset);

    @Query(value =
            "SELECT * FROM \"Phrase\" " +
                    " WHERE content LIKE ?3 " +
                    " ORDER BY id " +
                    " LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    Set<Phrase> getPhrasesByLimitOffsetAndContentPart(long limit, long offset, String contentPart);

    @Query(value =
        "SELECT * FROM \"Phrase\" WHERE content = ?1 ",
        nativeQuery = true
    )
    Phrase getPhraseByContentEquals(String content);
}