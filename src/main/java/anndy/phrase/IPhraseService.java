package anndy.phrase;

import anndy.model.Phrase;

public interface IPhraseService {
    Phrase findById(long id);

    Phrase findRandomPhrase();
}
