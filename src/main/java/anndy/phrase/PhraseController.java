package anndy.phrase;

import anndy.model.Phrase;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/phrase")
public class PhraseController {

    private final IPhraseService phraseService;

    public PhraseController(IPhraseService phraseService) {
        this.phraseService = phraseService;
    }

    @GetMapping("/random")
    public @ResponseBody String getRandomPhraseContentAsString(){
        Phrase phrase = phraseService.findRandomPhrase();
        if (phrase != null)
            return phrase.getContent();
        return "Фраз не обнаружено";
    }

    @PostMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public @ResponseBody String removePhraseById(@PathVariable Long id){
        if (phraseService.findById(id) == null)
            return "Фраза [id:"+ id +"] не найдена!";
        phraseService.deleteById(id);
        return "Фраза [id:"+ id +"] удалена!";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public @ResponseBody String removePhraseById(ModelMap model){
        String content = (String) model.getAttribute("Content");
        if (content == null || content.isBlank())
            return "Содержимое фразы не может быть пустым";
        content = content.trim();
        Phrase phrase = phraseService.findPhraseByContent(content);
        if (phrase == null) {
            phrase = phraseService.save(content);
            return "Фраза [id:" + phrase.getId() + "] добавлена!";
        }
        return "Фраза [id:"+ phrase.getId() +"] уже  существует!";
    }


}
