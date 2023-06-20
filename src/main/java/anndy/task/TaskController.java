package anndy.task;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/management")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String managementPage(){
        return "administration/task_management";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String editById(@RequestParam("idTask") Long idTask, ModelMap modelMap){
        Task task = taskService.findById(idTask);
        if (task != null) {
            Parser parser = Parser.builder().build();
            Node document = parser.parse(task.getDescription());
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            modelMap.addAttribute("currentTaskId", task.getId());
            modelMap.addAttribute("description", task.getDescription());
            modelMap.addAttribute("markdownTestDescription", renderer.render(document));
            return "administration/task/task_view";
        }
        return managementPage();
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String editDescriptionById(@RequestParam("idTask") Long idTask, @RequestParam("description") String description, ModelMap modelMap){
        Task task = taskService.changeDescription(idTask, description);
        if (task == null)
            return managementPage();
        return editById(idTask, modelMap);//"redirect:/task/edit?idTask="+idTask;
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String removeById(@RequestParam("idTask") Long idTask){
        if (taskService.findById(idTask) != null)
            taskService.deleteById(idTask);
        return managementPage();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public String addNew(@RequestParam("inputTask") String inputTask){
        if (inputTask == null || inputTask.isBlank())
            return "Ошибка: Содержимое не может быть пустым";
        inputTask = inputTask.trim();
        Task task = taskService.findTaskByDescription(inputTask);
        if (task == null) {
            task = taskService.save(inputTask);
            return "Задача [id:" + task.getId() + "] : |"+ inputTask +"| добавлена!";
        }
        return "Ошибка: Задача [id:"+ task.getId() +"] : |"+ inputTask +"| уже  существует!";
    }

    @GetMapping("/get_records")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public List<Task> getTasks(@RequestParam(value = "page", defaultValue = "1") int page) {
        return new ArrayList<>(taskService.taskSetByNumberPageList(page));
    }

    @GetMapping("/get_page_count")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public long getPageCount() {
        return taskService.pageCount();
    }
}
