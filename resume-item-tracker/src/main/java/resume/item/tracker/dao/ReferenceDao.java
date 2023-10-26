package resume.item.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import resume.item.tracker.entity.Reference;

public interface ReferenceDao extends JpaRepository<Reference, Object> {

}
