package anndy.answer;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    private final IAnswerService answerService;

    public AnswerController(IAnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/management")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String managementPage(){
        return "administration/answer_management";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public String removeById(@RequestParam("idAnswer") Long idAnswer){
        if (answerService.findById(idAnswer) == null)
            return "Ошибка: Ответ [id:"+ idAnswer +"] не найден!";
        answerService.deleteById(idAnswer);
        return "Ответ [id:"+ idAnswer +"] удалён!";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public String addNew(@RequestParam("inputAnswer") String inputAnswer){
        if (inputAnswer == null || inputAnswer.isBlank())
            return "Ошибка: Содержимое фразы не может быть пустым";
        inputAnswer = inputAnswer.trim();
        Answer answer = answerService.findAnswerByDescription(inputAnswer);
        if (answer == null) {
            answer = answerService.save(inputAnswer);
            return "Ответ [id:" + answer.getId() + "] : |"+ inputAnswer +"| добавлен!";
        }
        return "Ошибка: Ответ [id:"+ answer.getId() +"] : |"+ inputAnswer +"| уже  существует!";
    }

    @GetMapping("/get_records")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public List<Answer> getAnswers(@RequestParam(value = "page", defaultValue = "1") int page) {
        return new ArrayList<>(answerService.answerSetByNumberPageList(page));
    }

    @GetMapping("/get_page_count")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public long getPageCount() {
        return answerService.pageCount();
    }
}
