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

    @Query(
            value = "SELECT id FROM \"Page\" " +
                    "WHERE diary_id = ?1 AND id < ?2 " +
                    "ORDER BY id DESC " +
                    "LIMIT 1 OFFSET 0"
            , nativeQuery = true
    )
    Long findPreviousPageId(long diaryId, long currentPageId);

    @Query(
            value = "SELECT id FROM \"Page\" " +
                    "WHERE diary_id = ?1 AND id > ?2 " +
                    "ORDER BY id " +
                    "LIMIT 1 OFFSET 0"
            , nativeQuery = true
    )
    Long findNextPageId(long diaryId, long currentPageId);

    @Query(value =
            "SELECT * FROM \"Page\" " +
                    " WHERE diary_id = ?1 " +
                    " ORDER BY id " +
                    " LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    Set<Page> getPagesByDiaryIdAndLimitOffsetAndContentPart(long diaryId, long limit, long offset);
}