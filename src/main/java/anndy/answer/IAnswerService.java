package anndy.answer;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;

public interface IAnswerService {
    Answer findById(long id);

    @Transactional
    Answer save(String description);

    void deleteById(long id);

    Set<Answer> answerSetByNumberPageListAndRowOnPageAndDescriptionPart(
            long numberPageList
            , long rowOnPage
            , String descriptionPart
    );

    Set<Answer> answerSetByNumberPageListAndRowOnPage(long numberPageList, long rowOnPage);

    Set<Answer> answerSetByNumberPageList(long numberPageList);

    Answer findAnswerByDescription(String description);

    Long pageCount(long rowOnPage);

    Long pageCount();

    ArrayList<Answer> answerSetByNumberPageListAndRowOnPageAndTaskId(
            long numberPageList
            , long rowOnPage
            , long taskId
    );

    ArrayList<Answer> answerSetByNumberPageListAndTaskId(
            long numberPageList
            , long taskId
    );

    Long pageCountByTaskId(long rowOnPage, long taskId);

    Long pageCountByTaskId(long taskId);


    Set<Answer> answerSetByNumberPageListAndRowOnPageAndNotTaskId(
            long numberPageList
            , long rowOnPage
            , long taskId
    );

    Set<Answer> answerSetByNumberPageListAndNotTaskId(
            long numberPageList
            , long taskId
    );

    Long pageCountByNotTaskId(long rowOnPage, long taskId);

    Long pageCountByNotTaskId(long taskId);
}
