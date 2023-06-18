package anndy.phrase;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/management")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    public String managementPage(){
        return "administration/phrase_management";
    }

    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public String removePhraseById(@RequestParam("id") Long id){
        if (phraseService.findById(id) == null)
            return "Ошибка: Фраза [id:"+ id +"] не найдена!";
        phraseService.deleteById(id);
        return "Фраза [id:"+ id +"] удалена!";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public String addNewPhrase(@RequestParam("inputPhrase") String inputPhrase){
        if (inputPhrase == null || inputPhrase.isBlank())
            return "Ошибка: Содержимое фразы не может быть пустым";
        inputPhrase = inputPhrase.trim();
        Phrase phrase = phraseService.findPhraseByContent(inputPhrase);
        if (phrase == null) {
            phrase = phraseService.save(inputPhrase);
            return "Фраза [id:" + phrase.getId() + "] : |"+ inputPhrase +"| добавлена!";
        }
        return "Ошибка: Фраза [id:"+ phrase.getId() +"] : |"+ inputPhrase +"| уже  существует!";
    }

    @GetMapping("/get_records")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public List<Phrase> getPhrases(@RequestParam(value = "page", defaultValue = "1") int page) {
        return new ArrayList<>(phraseService.phraseSetByNumberPageList(page));
    }

    @GetMapping("/get_page_count")
    @PreAuthorize("hasAuthority('АДМИНИСТРАТОР')")
    @ResponseBody
    public long getPhrasesPageCount() {
        return phraseService.pageCount();
    }
}
