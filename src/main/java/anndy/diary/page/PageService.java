package anndy.diary.page;

import anndy.diary.Diary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class PageService implements IPageService {
    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Override
    public Page findById(long id){
        return pageRepository.getById(id);
    }

    public Page save(Diary diary, String content){
        Page page = new Page();
        page.setContent(content);
        page.setDiary(diary);
        page.setDate(LocalDate.now());
        return pageRepository.save(page);
    }

    //----- получение набора страниц -----
    @Override
    public Set<Page> pageSetByDiaryIdAndNumberPageListAndRowOnPage(long diaryId, long numberPageList, long rowOnPage) {
        return pageRepository.getPagesByDiaryIdAndLimitOffsetAndContentPart(diaryId, rowOnPage, (numberPageList - 1) * rowOnPage);
    }

    @Override
    public Set<Page> pageSetByDiaryIdAndNumberPageList(long diaryId, long numberPageList) {
        return pageSetByDiaryIdAndNumberPageListAndRowOnPage(diaryId, numberPageList, 10L);
    }

    //----- рассчёт количества страниц -----
    @Override
    public Long pageCount(long diaryId, long rowOnPage) {
        long countPages = pageRepository.countPageByDiaryId(diaryId) / rowOnPage;
        long nPage = countPages + (countPages % rowOnPage == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }
    @Override
    public Long pageCount(long diaryId) {
        return pageCount(diaryId,10);
    }
}
