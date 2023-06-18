package anndy.diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query( value = "SELECT * FROM \"Diary\" WHERE user_id = ?"
            ,nativeQuery = true)
    Diary findDiaryByUserId(long userId);
}