package anndy.answer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;

@Service
public class AnswerService implements IAnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Answer findById(long id){
        return answerRepository.getById(id);
    }

    @Override
    @Transactional
    public Answer save(String description){
        Answer answer = answerRepository.getAnswerByDescriptionEquals(description);
        if (answer == null) {
            answer = new Answer();
            answer.setDescription(description);
            answer = answerRepository.save(answer);
        }
        return answer;
    }

    @Override
    public void deleteById(long id){
        answerRepository.deleteById(id);
    }

    @Override
    public Set<Answer> answerSetByNumberPageListAndRowOnPageAndDescriptionPart(
            long numberPageList
            , long rowOnPage
            , String descriptionPart
    ) {
        if (descriptionPart == null)
            descriptionPart = "";
        descriptionPart = "%"+descriptionPart.trim()+"%";
        return answerRepository.getAnswersByLimitOffsetAndDescriptionPart(rowOnPage, (numberPageList - 1) * rowOnPage, descriptionPart);
    }

    @Override
    public Set<Answer> answerSetByNumberPageListAndRowOnPage(long numberPageList, long rowOnPage) {
        return answerSetByNumberPageListAndRowOnPageAndDescriptionPart(numberPageList, rowOnPage, "");
    }

    @Override
    public Set<Answer> answerSetByNumberPageList(long numberPageList) {
        return answerSetByNumberPageListAndRowOnPage(numberPageList, 10L);
    }

    @Override
    public Answer findAnswerByDescription(String content){
        return answerRepository.getAnswerByDescriptionEquals(content);
    }

    @Override
    public Long pageCount(long rowOnPage) {
        long nPage = answerRepository.count() / rowOnPage + (answerRepository.count() % rowOnPage == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }
    @Override
    public Long pageCount() {
        return pageCount(10);
    }

    @Override
    public ArrayList<Answer> answerSetByNumberPageListAndRowOnPageAndTaskId(
            long numberPageList
            , long rowOnPage
            , long taskId
    ) {
        long offset = (numberPageList - 1) * rowOnPage;
        return answerRepository.getAnswersByLimitOffsetAndTaskId(taskId, rowOnPage, offset);
    }
    @Override
    public ArrayList<Answer> answerSetByNumberPageListAndTaskId(
            long numberPageList
            , long taskId
    ) {
        return answerSetByNumberPageListAndRowOnPageAndTaskId(numberPageList, 10L, taskId);
    }

    @Override
    public Long pageCountByTaskId(long rowOnPage, long taskId) {
        long count = answerRepository.countAnswerByTaskId(taskId);
        long nPage = count / rowOnPage + (count % rowOnPage == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }

    @Override
    public Long pageCountByTaskId(long taskId) {
        return pageCountByTaskId(10, taskId);
    }

    @Override
    public Set<Answer> answerSetByNumberPageListAndRowOnPageAndNotTaskId(long numberPageList, long rowOnPage, long taskId) {
        return answerRepository.getAnswersByLimitOffsetAndNotTaskId(rowOnPage, (numberPageList - 1) * rowOnPage, taskId);
    }

    @Override
    public Set<Answer> answerSetByNumberPageListAndNotTaskId(long numberPageList, long taskId) {
        return answerSetByNumberPageListAndRowOnPageAndNotTaskId(numberPageList, 10L, taskId);
    }

    @Override
    public Long pageCountByNotTaskId(long rowOnPage, long taskId) {
        long count = answerRepository.countAnswerByNotTaskId(taskId);
        long nPage = count / rowOnPage + (count % rowOnPage == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }

    @Override
    public Long pageCountByNotTaskId(long taskId) {
        return pageCountByNotTaskId(10L, taskId);
    }
}
