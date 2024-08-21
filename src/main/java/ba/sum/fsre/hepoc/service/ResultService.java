package ba.sum.fsre.hepoc.service;

import ba.sum.fsre.hepoc.repository.ResultRepository;
import ba.sum.fsre.hepoc.entity.Result;
import com.n1analytics.paillier.EncryptedNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {
    @Autowired
    private final ResultRepository resultRepository;

    @Autowired
    private final EncryptionService encryptionService;

    public ResultService(ResultRepository resultRepository, EncryptionService encryptionService) {
        this.resultRepository = resultRepository;
        this.encryptionService = encryptionService;
    }

    public void addVote(Integer candidateId, Integer electionId, String encryptedVote) {
        Result result = resultRepository.findByElectionIdAndCandidateId(electionId, candidateId);
        if (result == null) {
            Result newResult = new Result();
            newResult.setCandidateId(candidateId);
            newResult.setElectionId(electionId);
            newResult.setEncryptedTally(encryptedVote);
            resultRepository.save(newResult);
            return;
        }

        String encryptedTallyString = result.getEncryptedTally();

        if (encryptedTallyString == null) {
            encryptedTallyString = encryptionService.encryptNumberToString(1);
        }

        EncryptedNumber tally = encryptionService.encryptedTextToEncryptedNumber(encryptedTallyString);
        EncryptedNumber vote = encryptionService.encryptedTextToEncryptedNumber(encryptedVote);

        EncryptedNumber addition = tally.add(vote);

        String newTallyString = encryptionService.encryptedNumberToString(addition);
        result.setEncryptedTally(newTallyString);

        resultRepository.save(result);
    }
}
