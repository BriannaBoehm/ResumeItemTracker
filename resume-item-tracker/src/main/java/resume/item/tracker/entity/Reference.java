package resume.item.tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class Reference {//generates the employee table with the columns listed 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long referenceId; 
	
	private String referenceName; 
	private String referencePhoneNumber; 
	private String referenceEmail; 
	
	@EqualsAndHashCode.Exclude //prevents recursion when .equals() and .hashCode() methods are called
	@ToString.Exclude //prevents recursion when .toString() is called 
	@ManyToOne 
	@JoinColumn(name = "job_id")
	private Job job; //creates the many to one relationship between reference and job
}
