package jobportal.repository;

import jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByJobIdAndJobSeekerId(Long jobId,Long jobSeekerId);

    List<Application> findByJobSeekerId(Long jobSeekerId);

}
