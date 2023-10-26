package resume.item.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import resume.item.tracker.entity.Job;

public interface JobDao extends JpaRepository<Job, Long> {

}
