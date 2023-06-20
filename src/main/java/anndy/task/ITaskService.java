package anndy.task;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ITaskService {
    Task findById(long id);

    @Transactional
    Task save(String description);
    @Transactional
    Task changeDescription(Long id, String description);
    void deleteById(long id);

    Set<Task> taskSetByNumberPageListAndRowOnPageAndDescriptionPart(
            long numberPageList
            , long rowOnPage
            , String descriptionPart
    );

    Set<Task> taskSetByNumberPageListAndRowOnPage(long numberPageList, long rowOnPage);

    Set<Task> taskSetByNumberPageList(long numberPageList);

    Task findTaskByDescription(String description);

    Long pageCount(long rowOnPage);

    Long pageCount();
}
