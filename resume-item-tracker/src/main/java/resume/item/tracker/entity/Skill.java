package resume.item.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class Skill {//generates the customer table with the columns listed 

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long skillId;
	
	private String skillName; 
	private String skillLevel; 
	private String customerEmail;
	
	@EqualsAndHashCode.Exclude //prevents recursion when .equals() and .hashCode() methods are called
	@ToString.Exclude //prevents recursion when .toString() is called 
	@ManyToMany(mappedBy = "skill", cascade = CascadeType.PERSIST)//creates the many to many relationship between skill and job 
	private Set<Job> jobs = new HashSet<>();
	
}
