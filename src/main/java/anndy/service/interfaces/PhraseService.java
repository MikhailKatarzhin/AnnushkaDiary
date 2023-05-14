package anndy.service.interfaces;

import anndy.model.Phrase;

public interface PhraseService {
    Phrase findById(long id);

    Phrase findRandomPhrase();
}
