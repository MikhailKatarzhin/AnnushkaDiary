package anndy.diary;

public interface IDiaryService {
    Long getDiaryIdByUserId(Long userId);
    Diary getDiaryByUserId(Long userId);
}
