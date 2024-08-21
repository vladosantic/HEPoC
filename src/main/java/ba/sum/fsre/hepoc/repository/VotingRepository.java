package ba.sum.fsre.hepoc.repository;

import ba.sum.fsre.hepoc.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends JpaRepository<Vote, Integer> {
}
