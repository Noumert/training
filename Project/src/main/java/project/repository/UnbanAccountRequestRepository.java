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

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("update UnbanAccountRequest u set u.resolved = ?1 where u.id = ?2")
    void setResolvedById(boolean resolved, Long requestId);
}
