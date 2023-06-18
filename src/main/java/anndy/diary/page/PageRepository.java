package anndy.diary.page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PageRepository extends JpaRepository<Page, Long> {

    @Query(
            value = "SELECT COUNT(*) FROM \"Page\" WHERE diary_id = ?"
            , nativeQuery = true
    )
    Long countPageByDiaryId(long diaryId);


    @Query(value =
            "SELECT * FROM \"Page\" " +
                    " WHERE diary_id = ?1 " +
                    " ORDER BY id " +
                    " LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    Set<Page> getPagesByDiaryIdAndLimitOffsetAndContentPart(long diaryId, long limit, long offset);
}