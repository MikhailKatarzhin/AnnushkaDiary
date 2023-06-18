package anndy.phrase;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface IPhraseService {
    Phrase findById(long id);

    Phrase findRandomPhrase();
    @Transactional
    Phrase save(String content);

    @Transactional
    @Modifying
    void deleteById(long id);

    Set<Phrase> phraseSetByNumberPageListAndRowOnPageAndContentPart(
            long numberPageList
            , long rowOnPage
            , String contentPart
    );

    Set<Phrase> phraseSetByNumberPageListAndRowOnPage(long numberPageList, long rowOnPage);

    Set<Phrase> phraseSetByNumberPageList(long numberPageList);

    Phrase findPhraseByContent(String content);

    Long pageCount(long rowOnPage);

    Long pageCount();
}
