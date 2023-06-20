package anndy.task.taskCorrectAnswer;

import anndy.answer.IAnswerService;
import anndy.task.ITaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/task_correct_answer")
public class TaskCorrectAnswerController {

    private final ITaskCorrectAnswerService taskCorrectAnswerService;
    private final IAnswerService answerService;
    private final ITaskService taskService;

    public TaskCorrectAnswerController(ITaskCorrectAnswerService taskCorrectAnswerService, IAnswerService answerService, ITaskService taskService) {
        this.taskCorrectAnswerService = taskCorrectAnswerService;
        this.answerService = answerService;
        this.taskService = taskService;
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    @Transactional
    public String removeById(@RequestParam("taskId") Long taskId){
        if (taskCorrectAnswerService.getByTaskId(taskId) == null)
            return "Ошибка: Верного Ответа уже нет в задаче[id:"+ taskId +"]!";
        taskCorrectAnswerService.deleteByTaskId(taskId);
        return "Верный Ответ удалён из задачи[id:"+ taskId +"]!";
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
        TaskCorrectAnswer taskAnswer = taskCorrectAnswerService.getByAnswerIdAndTaskId(answerId, taskId);
        if (taskAnswer == null) {
            removeById(taskId);
            taskAnswer = taskCorrectAnswerService.save(answerId, taskId);
            return "Ответ [idA:"+ taskAnswer.getAnswerId() +"] добавлен в Задачу [idT:"+ taskAnswer.getTaskId() +"] Как верный!";
        }
        return "Ошибка: Ответ [idA:"+ taskAnswer.getAnswerId() +"] уже  выбран верным в Задаче [idT:"+ taskAnswer.getTaskId() +"]!";
    }

    @GetMapping("/getByTaskId")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public String getByTaskId(@RequestParam("taskId") long taskId){
        return answerService.findById(taskCorrectAnswerService.getByTaskId(taskId).getAnswerId()).getDescription();
    }
}
