package anndy.diary.page;

import anndy.diary.Diary;
import anndy.diary.IDiaryService;
import anndy.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/page")
public class PageController {

    private final IPageService pageService;
    private final UserService userService;
    private final IDiaryService diaryService;

    public PageController(IPageService pageService, UserService userService, IDiaryService diaryService) {
        this.pageService = pageService;
        this.userService = userService;
        this.diaryService = diaryService;
    }

    @GetMapping("/pages")
    public String pageList(){
        return "diary/page_list";
    }

    @GetMapping("/add")
    public String add(){
        return "diary/page_new";
    }

    @PostMapping("/add")
    public String add(@RequestParam("content") String content){
        if (content != null)
            if (!content.isBlank()) {
                content = content.trim();
                Diary diary = diaryService.getDiaryByUserId(userService.getRemoteUserId());
                pageService.save(diary, content);
            }
        return "redirect:/page/pages";
    }

    @GetMapping("/page/view")
    public String pageView(@RequestParam(value = "pageId") int pageId){
        return "diary/page_view";
    }

    @GetMapping("/get_pages")
    @ResponseBody
    public List<Page> getPages(@RequestParam(value = "page", defaultValue = "1") int page) {
        return new ArrayList<>(pageService.pageSetByDiaryIdAndNumberPageList(diaryService.getDiaryIdByUserId(userService.getRemoteUserId()), page));
    }

    @GetMapping("/get_page_count")
    @ResponseBody
    public long getPagesPageCount() {
        return pageService.pageCount(diaryService.getDiaryIdByUserId(userService.getRemoteUserId()));
    }
}