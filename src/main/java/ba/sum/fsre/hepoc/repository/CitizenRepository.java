package ba.sum.fsre.hepoc.repository;

import ba.sum.fsre.hepoc.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
    Optional<Citizen> findByJmbg(String jmbg);
}