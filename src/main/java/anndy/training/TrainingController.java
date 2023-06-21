package anndy.training;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/training")
public class TrainingController {

    private final ITrainingService trainingService;

    public TrainingController(ITrainingService trainingService) {
        this.trainingService = trainingService;
    }


    @GetMapping("/management")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String managementPage(){
        return "administration/training_management";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String editById(@RequestParam("idTraining") Long idTraining, ModelMap modelMap){
        Training training = trainingService.findById(idTraining);
        if (training != null) {
            modelMap.addAttribute("currentTrainingId", training.getId());
            modelMap.addAttribute("title", training.getTitle());
            modelMap.addAttribute("description", training.getDescription());
            return "administration/training/training_view";
        }
        return managementPage();
    }

    @PostMapping("/edit/description")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String editDescriptionById(@RequestParam("idTraining") Long idTraining, @RequestParam("description") String description, ModelMap modelMap){
        Training training = trainingService.changeDescription(idTraining, description);
        if (training == null)
            return managementPage();
        return editById(idTraining, modelMap);
    }

    @PostMapping("/edit/title")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String editTitleById(@RequestParam("idTraining") Long idTraining, @RequestParam("title") String title, ModelMap modelMap){
        Training training = trainingService.changeTitle(idTraining, title);
        if (training == null)
            return managementPage();
        return editById(idTraining, modelMap);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String removeById(@RequestParam("idTraining") Long idTraining){
        if (trainingService.findById(idTraining) != null)
            trainingService.deleteById(idTraining);
        return managementPage();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public String addNew(
            @RequestParam("inputTrainingTitle") String inputTrainingTitle
    ){
        if (inputTrainingTitle == null || inputTrainingTitle.isBlank())
            return "Ошибка: Название не может быть пустым";
        inputTrainingTitle = inputTrainingTitle.trim();
        Training training = trainingService.findTaskByTitle(inputTrainingTitle);
        if (training == null) {
            training = trainingService.save(inputTrainingTitle, "");
            return "Тренинг [id:" + training.getId() + "] : |"+ inputTrainingTitle +"| создан!";
        }
        return "Ошибка: Тренинг [id:"+ training.getId() +"] : |"+ inputTrainingTitle +"| уже  существует!";
    }

    @GetMapping("/get_records")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public List<Training> getTrainings(@RequestParam(value = "page", defaultValue = "1") int page) {
        return new ArrayList<>(trainingService.trainingSetByNumberPageListAndRowOnPage(page));
    }

    @GetMapping("/get_page_count")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public long getPageCount() {
        return trainingService.pageCount();
    }
}
