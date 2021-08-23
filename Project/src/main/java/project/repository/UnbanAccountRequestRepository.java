package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.entity.UnbanAccountRequest;

import java.util.List;

/**
 * Created by Noumert on 11.08.2021.
 */
@Repository
public interface UnbanAccountRequestRepository extends JpaRepository<UnbanAccountRequest, Long> {
    List<UnbanAccountRequest> findByResolved(boolean resolved);
}
