package resume.item.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import resume.item.tracker.entity.Skill;

public interface SkillDao extends JpaRepository<Skill, Object> {

}
