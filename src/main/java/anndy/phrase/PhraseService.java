package anndy.phrase;

import anndy.model.Phrase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class PhraseService implements IPhraseService {
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

    @Override
    @Transactional
    public Phrase save(String content){
        Phrase phrase = phraseRepository.getPhraseByContentEquals(content);
        if (phrase == null) {
            phrase = new Phrase();
            phrase.setContent(content);
            phrase = phraseRepository.save(phrase);
        }
        return phrase;
    }

    @Override
    @Transactional
    @Modifying
    public void deleteById(long id){
        phraseRepository.deleteById(id);
    }

    @Override
    public Set<Phrase> phraseSetByNumberPageListAndRowOnPageAndContentPart(
            long numberPageList
            , long rowOnPage
            , String contentPart
    ) {
        if (contentPart == null)
            contentPart = "";
        contentPart = "%"+contentPart.trim()+"%";
        return phraseRepository.getPhrasesByLimitOffsetAndContentPart(rowOnPage, (numberPageList - 1) * rowOnPage, contentPart);
    }

    @Override
    public Set<Phrase> phraseSetByNumberPageListAndRowOnPage(long numberPageList, long rowOnPage) {
        return phraseSetByNumberPageListAndRowOnPageAndContentPart(rowOnPage, (numberPageList - 1) * rowOnPage, "");
    }

    @Override
    public Set<Phrase> phraseSetByNumberPageList(long numberPageList) {
        return phraseSetByNumberPageListAndRowOnPage(numberPageList, 10L);
    }

    @Override
    public Phrase findPhraseByContent(String content){
        return phraseRepository.getPhraseByContentEquals(content);
    }

    @Override
    public Long pageCount(long rowOnPage) {
        long nPage = phraseRepository.count() / rowOnPage + (phraseRepository.count() % rowOnPage == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }
}
