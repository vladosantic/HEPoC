package ba.sum.fsre.hepoc.repository;

import ba.sum.fsre.hepoc.entity.Candidate;
import ba.sum.fsre.hepoc.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    List<Candidate> findAllByElection(Election election);
}
