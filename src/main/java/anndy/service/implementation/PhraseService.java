package anndy.service.implementation;

import anndy.model.Phrase;
import anndy.repo.PhraseRepository;
import org.springframework.stereotype.Service;

@Service
public class PhraseService implements anndy.service.interfaces.PhraseService {
    private final PhraseRepository phraseRepository;

    public PhraseService(PhraseRepository phraseRepository) {
        this.phraseRepository = phraseRepository;
    }

    @Override
    public Phrase findById(long id){
        return phraseRepository.getById(id);
    }

    @Override
    public Phrase findRandomPhrase(){
        if (0 == phraseRepository.count())
            return null;
        return phraseRepository.getById(phraseRepository.findRandomPhraseId());
    }
}
