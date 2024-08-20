package ba.sum.fsre.hepoc.repository;

import ba.sum.fsre.hepoc.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Integer> {
    Optional<Election> findById(Integer id);
}
