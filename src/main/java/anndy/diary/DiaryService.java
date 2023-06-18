package anndy.diary;

import org.springframework.stereotype.Service;

@Service
public class DiaryService implements IDiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override
    public Long getDiaryIdByUserId(Long userId){
        return getDiaryByUserId(userId).getId();
    }
    @Override
    public Diary getDiaryByUserId(Long userId){
        return diaryRepository.findDiaryByUserId(userId);
    }
}
