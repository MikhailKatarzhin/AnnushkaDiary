package anndy.task.taskAnswer;

import anndy.answer.Answer;
import anndy.answer.IAnswerService;
import anndy.task.ITaskService;
import anndy.task.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/task_answer")
public class TaskAnswerController {

    private final IAnswerService answerService;
    private final ITaskAnswerService taskAnswerService;
    private final ITaskService taskService;

    public TaskAnswerController(IAnswerService answerService, ITaskAnswerService taskAnswerService, ITaskService taskService) {
        this.answerService = answerService;
        this.taskAnswerService = taskAnswerService;
        this.taskService = taskService;
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    @Modifying
    @Transactional
    public String removeById(@RequestParam("answerId") Long answerId, @RequestParam("taskId") Long taskId){
        if (taskAnswerService.getByAnswerIdAndTaskId(answerId, taskId) == null)
            return "Ошибка: Ответа [id:"+ answerId +"] нет в задаче[id:"+ taskId +"]!";
        taskAnswerService.deleteByAnswerIdAndTaskId(answerId, taskId);
        return "Ответ [id:"+ answerId +"] из задачи[id:"+ taskId +"] убран!";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public String addNew(
            @RequestParam("answerId") long answerId
            , @RequestParam("taskId") long taskId
    ){
        if (answerService.findById(answerId) == null)
            return "Ошибка: Ответ [id:"+ answerId +"] не  существует!";
        if (taskService.findById(taskId) == null)
            return "Ошибка: Задача [id:"+ taskId +"] не  существует!";
        TaskAnswer taskAnswer = taskAnswerService.getByAnswerIdAndTaskId(answerId, taskId);
        if (taskAnswer == null) {
            taskAnswer = taskAnswerService.save(answerId, taskId);
            return "Ответ [idA:"+ taskAnswer.getAnswerId() +"] добавлен в Задачу [idT:"+ taskAnswer.getTaskId() +"]!";
        }
        return "Ошибка: Ответ [idA:"+ taskAnswer.getAnswerId() +"] уже  существует в Задаче [idT:"+ taskAnswer.getTaskId() +"]!";
    }

    @GetMapping("/get_records")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public List<Answer> getAnswers(
            @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam("taskId") long taskId
    ) {
        return new ArrayList<>(answerService.answerSetByNumberPageListAndNotTaskId(page, taskId));
    }

    @GetMapping("/get_page_count")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public long getPageCount(@RequestParam("taskId") long taskId) {
        return answerService.pageCountByNotTaskId(taskId);
    }

    @GetMapping("/get_records_selected")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public List<Answer> getAnswersSelected(
            @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam("taskId") long taskId
    ) {
        return answerService.answerSetByNumberPageListAndTaskId(page, taskId);
    }

    @GetMapping("/get_page_count_selected")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public long getPageCountSelected(@RequestParam("taskId") long taskId) {
        return answerService.pageCountByTaskId(taskId);
    }
}
