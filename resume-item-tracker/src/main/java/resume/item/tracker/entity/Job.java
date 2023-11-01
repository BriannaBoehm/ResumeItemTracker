package resume.item.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class Job {//generates the pet store table with the columns listed 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jobId;
	
	private String jobTitle; 
	private String jobCompany; 
	private String jobStartDate; 
	private String jobEndDate; 

	@EqualsAndHashCode.Exclude //prevents recursion when .equals() and .hashCode() methods are called
	@ToString.Exclude //prevents recursion when .toString() is called 
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "job_skill", joinColumns = 
	@JoinColumn(name = "job_id"), inverseJoinColumns = 
	@JoinColumn(name = "skill_id"))
	private Set<Skill> skills = new HashSet<>(); 
	
	@EqualsAndHashCode.Exclude //prevents recursion when .equals() and .hashCode() methods are called 
	@ToString.Exclude //prevents recursion when .toString() is called 
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Reference> references = new HashSet<>();
	
}
