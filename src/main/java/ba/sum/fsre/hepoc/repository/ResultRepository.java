package ba.sum.fsre.hepoc.repository;

import ba.sum.fsre.hepoc.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    Result findByElectionIdAndCandidateId (Integer electionId, Integer candidateId);
}
