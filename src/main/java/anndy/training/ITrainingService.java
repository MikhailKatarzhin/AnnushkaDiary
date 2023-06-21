package anndy.training;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ITrainingService {
    Training findById(long id);

    @Transactional
    Training save(String title, String description);

    @Transactional
    Training changeDescription(Long id, String description);

    @Transactional
    Training changeTitle(Long id, String title);

    void deleteById(long id);

    Set<Training> trainingSetByNumberPageListAndRowOnPageAndDescriptionPart(
            long numberPageList
            , long rowOnPage
            , String descriptionPart
    );

    Set<Training> trainingSetByNumberPageListAndRowOnPage(long numberPageList, long rowOnPage);

    Set<Training> trainingSetByNumberPageListAndRowOnPage(long numberPageList);

    Training findTaskByTitle(String title);

    Long pageCount(long rowOnPage);

    Long pageCount();
}
