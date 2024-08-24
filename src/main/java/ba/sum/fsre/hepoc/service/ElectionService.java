package ba.sum.fsre.hepoc.service;

import ba.sum.fsre.hepoc.entity.Election;
import ba.sum.fsre.hepoc.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {
    @Autowired
    private final ElectionRepository electionRepository;

    public ElectionService(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    public Election getElectionById(Integer id) {
        return electionRepository.findById(id).orElse(null);
    }

    public void save(Election election) {
        electionRepository.save(election);
    }

    public void deleteById(Integer id) {
        electionRepository.deleteById(id);
    }
}
