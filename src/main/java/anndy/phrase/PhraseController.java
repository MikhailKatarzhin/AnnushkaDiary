package anndy.phrase;

import anndy.model.Phrase;
import anndy.service.interfaces.PhraseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PhraseController {

    private final PhraseService phraseService;

    public PhraseController(PhraseService phraseService) {
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
