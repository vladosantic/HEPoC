package ba.sum.fsre.hepoc.service;

import ba.sum.fsre.hepoc.entity.Candidate;
import ba.sum.fsre.hepoc.entity.Election;
import ba.sum.fsre.hepoc.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {
    @Autowired
    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<Candidate> findAllFromElection (Election election) {
        return candidateRepository.findAllByElection(election);
    }

    public Candidate findCandidateById (Integer id) {
        return candidateRepository.findCandidateById(id);
    }

    public List<Candidate> findAll () {
        return candidateRepository.findAll();
    }

    public void save (Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public void deleteById(Integer id) {
        candidateRepository.deleteById(id);
    }
}
