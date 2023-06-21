package anndy.training;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class TrainingService implements ITrainingService {
    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Training findById(long id){
        return trainingRepository.getById(id);
    }

    @Override
    @Transactional
    public Training save(String title, String description){
        Training training = trainingRepository.getTrainingByTitleEquals(title);
        if (training == null) {
            training = new Training();
            training.setTitle(title);
            training.setDescription(description);
            training = trainingRepository.save(training);
        }
        return training;
    }

    @Override
    @Transactional
    public Training changeDescription(Long id, String description){
        Training training = findById(id);
        if (training == null) {
            return null;
        }
        training.setDescription(description);
        return trainingRepository.save(training);
    }

    @Override
    @Transactional
    public Training changeTitle(Long id, String title){
        Training training = trainingRepository.getTrainingByTitleEquals(title);
        if (training != null) {
            return null;
        }
        training = trainingRepository.getById(id);
        if (training == null)
            return null;
        training.setTitle(title);
        return trainingRepository.save(training);
    }

    @Override
    public void deleteById(long id){
        trainingRepository.deleteById(id);
    }

    @Override
    public Set<Training> trainingSetByNumberPageListAndRowOnPageAndDescriptionPart(
            long numberPageList
            , long rowOnPage
            , String descriptionPart
    ) {
        if (descriptionPart == null)
            descriptionPart = "";
        descriptionPart = "%"+descriptionPart.trim()+"%";
        return trainingRepository.getTrainingsByLimitOffsetAndDescriptionPart(rowOnPage, (numberPageList - 1) * rowOnPage, descriptionPart);
    }

    @Override
    public Set<Training> trainingSetByNumberPageListAndRowOnPage(long numberPageList, long rowOnPage) {
        return trainingRepository.getTrainingsByLimitOffset(rowOnPage, (numberPageList - 1) * rowOnPage);
    }

    @Override
    public Set<Training> trainingSetByNumberPageListAndRowOnPage(long numberPageList) {
        return trainingSetByNumberPageListAndRowOnPage(numberPageList, 10L);
    }

    @Override
    public Training findTaskByTitle(String title){
        return trainingRepository.getTrainingByTitleEquals(title);
    }

    @Override
    public Long pageCount(long rowOnPage) {
        long nPage = trainingRepository.count() / rowOnPage + (trainingRepository.count() % rowOnPage == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }
    @Override
    public Long pageCount() {
        return pageCount(10);
    }
}
