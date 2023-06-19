package anndy.diary.page;

import anndy.diary.Diary;
import anndy.diary.IDiaryService;
import anndy.service.interfaces.UserService;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String add(@RequestParam("content") String content, ModelMap model){
        if (content != null)
            if (!content.isBlank()) {
                content = content.trim();
                Diary diary = diaryService.getDiaryByUserId(userService.getRemoteUserId());
                long pageId = pageService.save(diary, content).getId();
                return pageView(pageId, model);
            }
        return "redirect:/page/pages";
    }

    @GetMapping("/view")
    public String pageView(@RequestParam(value = "pageId") long pageId, ModelMap model){
        Page page = pageService.findById(pageId);
        Parser parser = Parser.builder().build();
        Node document = parser.parse(page.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        long diaryId = diaryService.getDiaryIdByUserId(userService.getRemoteUserId());
        Long previousId = pageService.findPreviousId(diaryId, pageId);
        Long nextId = pageService.findNextId(diaryId, pageId);
        String previous = (previousId == null ? "/page/pages" : ("/page/view?pageId=" + previousId));
        String next = (nextId == null ? "/page/pages" : ("/page/view?pageId=" + nextId));
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);
        model.addAttribute("currentPage", pageId);
        model.addAttribute("pageDate", page.getDate());
        model.addAttribute("content", renderer.render(document));
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
