package anndy.training.trainingTask;

import anndy.task.ITaskService;
import anndy.task.Task;
import anndy.training.ITrainingService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/training_task")
public class TrainingTaskController {

    private final ITrainingService trainingService;
    private final ITrainingTaskService trainingTaskService;
    private final ITaskService taskService;

    public TrainingTaskController(ITrainingService trainingService, ITrainingTaskService trainingTaskService, ITaskService taskService) {
        this.trainingService = trainingService;
        this.trainingTaskService = trainingTaskService;
        this.taskService = taskService;
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    @Modifying
    @Transactional
    public String removeById(@RequestParam("trainingId") Long trainingId, @RequestParam("taskId") Long taskId){
        if (trainingTaskService.getByTrainingIdAndTaskId(trainingId, taskId) == null)
            return "Ошибка: Задачи [id:"+ taskId +"] нет в тренинге[id:"+ trainingId +"]!";
        trainingTaskService.deleteByTrainingIdAndTaskId(trainingId, taskId);
        return "Задача [id:"+ taskId +"] из тренинга[id:"+ trainingId +"] убрана!";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public String addNew(
            @RequestParam("trainingId") long trainingId
            , @RequestParam("taskId") long taskId
    ){
        if (trainingService.findById(trainingId) == null)
            return "Ошибка: Тренинг [id:"+ trainingId +"] не  существует!";
        if (taskService.findById(taskId) == null)
            return "Ошибка: Задача [id:"+ taskId +"] не  существует!";
        TrainingTask trainingTask = trainingTaskService.getByTrainingIdAndTaskId(trainingId, taskId);
        if (trainingTask == null) {
            trainingTask = trainingTaskService.save(trainingId, taskId);
            return "Задача [idTa:"+ trainingTask.getTaskId() +"] добавлена в Тренинг [idTr:"+ trainingTask.getTrainingId() +"]!";
        }
        return "Ошибка: Задача [idTa:"+ trainingTask.getTaskId() +"] уже  существует в Тренинге [idTr:"+ trainingTask.getTrainingId() +"]!";
    }

    @GetMapping("/get_records")
    @ResponseBody
    public List<Task> getTasks(
            @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam("trainingId") long trainingId
    ) {
        return new ArrayList<>(taskService.taskSetByNumberPageListAndNotTrainingId(page, trainingId));
    }

    @GetMapping("/get_page_count")
    @ResponseBody
    public long getPageCount(@RequestParam("trainingId") long trainingId) {
        return taskService.pageCountByNotTrainingId(trainingId);
    }

    @GetMapping("/get_records_selected")
    @ResponseBody
    public List<Task> getTasksSelected(
            @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam("trainingId") long trainingId
    ) {
        return taskService.taskSetByNumberPageListAndTrainingId(page, trainingId);
    }

    @GetMapping("/get_page_count_selected")
    @ResponseBody
    public long getPageCountSelected(@RequestParam("trainingId") long trainingId) {
        return taskService.pageCountByTrainingId(trainingId);
    }
}
