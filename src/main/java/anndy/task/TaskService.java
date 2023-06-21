package anndy.task;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;

@Service
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task findById(long id){
        return taskRepository.getById(id);
    }

    @Override
    @Transactional
    public Task save(String description){
        Task task = taskRepository.getTaskByDescriptionEquals(description);
        if (task == null) {
            task = new Task();
            task.setDescription(description);
            task = taskRepository.save(task);
        }
        return task;
    }

    @Override
    @Transactional
    public Task changeDescription(Long id, String description){
        Task task = taskRepository.getTaskByDescriptionEquals(description);
        if (task == null) {
            task = taskRepository.getById(id);
            if (task != null) {
                task.setDescription(description);
                return taskRepository.save(task);
            }
        }
        return task;
    }

    @Override
    public void deleteById(long id){
        taskRepository.deleteById(id);
    }

    @Override
    public Set<Task> taskSetByNumberPageListAndRowOnPageAndDescriptionPart(
            long numberPageList
            , long rowOnPage
            , String descriptionPart
    ) {
        if (descriptionPart == null)
            descriptionPart = "";
        descriptionPart = "%"+descriptionPart.trim()+"%";
        return taskRepository.getTasksByLimitOffsetAndDescriptionPart(rowOnPage, (numberPageList - 1) * rowOnPage, descriptionPart);
    }

    @Override
    public Set<Task> taskSetByNumberPageListAndRowOnPage(long numberPageList, long rowOnPage) {
        return taskSetByNumberPageListAndRowOnPageAndDescriptionPart(numberPageList, rowOnPage, "");
    }

    @Override
    public Set<Task> taskSetByNumberPageList(long numberPageList) {
        return taskSetByNumberPageListAndRowOnPage(numberPageList, 10L);
    }

    @Override
    public Task findTaskByDescription(String content){
        return taskRepository.getTaskByDescriptionEquals(content);
    }

    @Override
    public Long pageCount(long rowOnPage) {
        long nPage = taskRepository.count() / rowOnPage + (taskRepository.count() % rowOnPage == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }
    @Override
    public Long pageCount() {
        return pageCount(10);
    }

    @Override
    public ArrayList<Task> taskSetByNumberPageListAndRowOnPageAndTrainingId(long numberPageList, long rowOnPage, long trainingId) {
        long offset = (numberPageList - 1) * rowOnPage;
        return taskRepository.getTasksByLimitOffsetAndTrainingId(trainingId, rowOnPage, offset);
    }

    @Override
    public ArrayList<Task> taskSetByNumberPageListAndTrainingId(long numberPageList, long trainingId) {
        return taskSetByNumberPageListAndRowOnPageAndTrainingId(numberPageList, 10L, trainingId);
    }

    @Override
    public Long pageCountByTrainingId(long rowOnPage, long trainingId) {
        long count = taskRepository.countTaskByTrainingId(trainingId);
        long nPage = count / rowOnPage + (count % rowOnPage == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }

    @Override
    public Long pageCountByTrainingId(long trainingId) {
        return pageCountByTrainingId(10L, trainingId);
    }

    @Override
    public Set<Task> taskSetByNumberPageListAndRowOnPageAndNotTrainingId(long numberPageList, long rowOnPage, long trainingId) {
        return taskRepository.getTasksByLimitOffsetAndNotTrainingId(rowOnPage, (numberPageList - 1) * rowOnPage, trainingId);
    }

    @Override
    public Set<Task> taskSetByNumberPageListAndNotTrainingId(long numberPageList, long trainingId) {
        return taskSetByNumberPageListAndRowOnPageAndNotTrainingId(numberPageList, 10L , trainingId);
    }

    @Override
    public Long pageCountByNotTrainingId(long rowOnPage, long trainingId) {
        long count = taskRepository.countTaskByNotTrainingId(trainingId);
        long nPage = count / rowOnPage + (count % rowOnPage == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }

    @Override
    public Long pageCountByNotTrainingId(long trainingId) {
        return pageCountByNotTrainingId(10L, trainingId);
    }
}
