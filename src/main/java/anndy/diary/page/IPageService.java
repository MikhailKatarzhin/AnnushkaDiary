package anndy.diary.page;

import anndy.diary.Diary;

import java.util.Set;

public interface IPageService {
    Page findById(long id);
    Long findPreviousId(long diaryId, long currentId);
    Long findNextId(long diaryId, long currentId);
    Page save(Diary diary, String content);
    Set<Page> pageSetByDiaryIdAndNumberPageListAndRowOnPage(long diaryId, long numberPageList, long rowOnPage);
    Set<Page> pageSetByDiaryIdAndNumberPageList(long diaryId, long numberPageList);
    Long pageCount(long diaryId, long rowOnPage);
    Long pageCount(long diaryId);
}
