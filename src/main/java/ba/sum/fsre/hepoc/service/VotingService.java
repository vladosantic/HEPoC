package ba.sum.fsre.hepoc.service;

import ba.sum.fsre.hepoc.entity.Vote;
import ba.sum.fsre.hepoc.repository.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotingService {
    @Autowired
    private final VotingRepository votingRepository;

    public VotingService(VotingRepository votingRepository) {
        this.votingRepository = votingRepository;
    }

    public void saveVote(Vote vote) {
        votingRepository.save(vote);
    }
}
