package resume.item.tracker.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import resume.item.tracker.entity.Skill;
import resume.item.tracker.entity.Reference;
import resume.item.tracker.entity.Job;

@Data
@NoArgsConstructor
public class JobData {

private Long jobId;
	
	private String jobTitle; 
	private String jobCompany; 
	private String jobStartDate; 
	private String jobEndDate;
	Set<JobReference> jobReference = new HashSet<>();
	Set<JobSkill> jobSkill = new HashSet<>();

	public JobData(Job job) {//creates a new constructor for JobData that takes in a Job object as a parameter 
		//these statements set the values of the job data pieces to the values stored in the jobData object 
		this.jobId = job.getJobId();
		this.jobTitle = job.getJobTitle();
		this.jobCompany = job.getJobCompany();
		this.jobStartDate = job.getJobStartDate(); 
		this.jobEndDate = job.getJobEndDate();
	
		for(Skill skill: job.getSkills()) {//for loops are needed to set the sets to contain the information from the job object 
			this.jobSkill.add(new JobSkill(skill));
		}
		
		for(Reference reference: job.getReferences()) {
			this.jobReference.add(new JobReference(reference));
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class JobSkill {//creates JobSkill as an internal class to JobData
		
		private Long skillId;
		private String skillName; 
		private String skillLevel; 
	
		public JobSkill (Skill skill) {//constructor for JobSkill DTO that takes in a skill object 
			this.skillId = skill.getSkillId(); 
			this.skillName = skill.getSkillName(); 
			this.skillLevel = skill.getSkillLevel();
		}

	}
	
	@Data
	@NoArgsConstructor
	public static class JobReference {//creates JobReference as an internal class to JobData
		
		private Long referenceId; 
		private String referenceName; 
		private String referencePhoneNumber; 
		private String referenceEmail;

		public JobReference(Reference reference) { //constructor for JobReference DTO that takes in a reference object 
			this.referenceId = reference.getReferenceId(); 
			this.referenceName = reference.getReferenceName(); 
			this.referencePhoneNumber = reference.getReferencePhoneNumber(); 
			this.referenceEmail = reference.getReferenceEmail(); 
		}
			
	}
	
}
