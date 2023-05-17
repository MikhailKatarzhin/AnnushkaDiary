package anndy.phrase;

import anndy.model.Phrase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PhraseController {

    private final IPhraseService phraseService;

    public PhraseController(IPhraseService phraseService) {
        this.phraseService = phraseService;
    }

    @GetMapping("/phrase/random")
    private @ResponseBody String getRandomPhraseContentAsString(){
        Phrase phrase = phraseService.findRandomPhrase();
        if (phrase != null)
            return phrase.getContent();
        return "Фраз не обнаружено";
    }
}
