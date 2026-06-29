package jobportal.repository;

import jobportal.entity.Job;
import jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    long countByRecruiterId(Long recruiterId);

    List<Job> findByRecruiter(User recruiter);

}
