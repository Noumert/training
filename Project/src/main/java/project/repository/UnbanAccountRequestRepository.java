package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.entity.UnbanAccountRequest;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UnbanAccountRequestRepository extends JpaRepository<UnbanAccountRequest, Long> {
    List<UnbanAccountRequest> findByResolved(boolean resolved);
}
