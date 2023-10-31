package resume.item.tracker.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import resume.item.tracker.entity.Skill;
import resume.item.tracker.entity.Reference;
import resume.item.tracker.dao.SkillDao;
import resume.item.tracker.dao.ReferenceDao;
import resume.item.tracker.controller.model.JobData;
import resume.item.tracker.controller.model.JobData.JobReference;
import resume.item.tracker.controller.model.JobData.JobSkill;
//import resume.item.tracker.controller.model.ResumeItemTrackerData.PetStoreCustomer;
//import resume.item.tracker.controller.model.ResumeItemTrackerData.PetStoreEmployee;
import resume.item.tracker.dao.JobDao;
import resume.item.tracker.entity.Job;

@Service
public class ResumeItemTrackerService {

	@Autowired
	private JobDao jobDao;
	
	@Autowired 
	private ReferenceDao referenceDao;
	
	@Autowired 
	private SkillDao skillDao;

	@Transactional(readOnly = false)
	public JobData saveJob(JobData jobData) { //creates a new JobData object from info put in the client 
		Long jobId = jobData.getJobId();
		Job job = findOrCreateJob(jobId);
		copyJobFields(job, jobData);
		return new JobData(jobDao.save(job));

	}

	private void copyJobFields(Job job, JobData jobData) {//sets the values of the parameters of the job object to the values received from the JSON 
		
		job.setJobTitle(jobData.getJobTitle());
		job.setJobCompany(jobData.getJobCompany());
		job.setJobStartDate(jobData.getJobStartDate());
		job.setJobEndDate(jobData.getJobEndDate());
	
	}

	private Job findOrCreateJob(Long jobId) {
		if (Objects.isNull(jobId)) {
			Job job = new Job();
			return job;
			// return new job object if jobId is null
		} else {
			return findJobById(jobId); //returns the job object identified by the jobId if not null 
		}
	}

	private Job findJobById(Long jobId) {
		// returns a Job object if the jobId exists or else returns a NSEE
		return jobDao.findById(jobId)
				.orElseThrow(() -> new NoSuchElementException("Job with ID=" + jobId + " was not found."));
	}

	@Transactional(readOnly = false)
	public JobSkill saveSkill(Long jobId, JobSkill jobSkill) {
		Job job = findJobById(jobId);
		Long skillId = jobSkill.getSkillId();
		Skill skill = findOrCreateSkill(skillId, jobId);
		
		copySkillFields(skill, jobSkill); 
		
		Set<Job> jobSet = skill.getJobs(); 
		jobSet.add(job); 
		skill.setJobs(jobSet); //adds the job to the skill's job set 
		
		Set<Skill> skillSet = job.getSkills(); 
		skillSet.add(skill);
		job.setSkills(skillSet); //adds the skill to the job's skill set 
		
		Skill dbSkill = skillDao.save(skill); 
				
		return new JobSkill(dbSkill);
	}
	
	private Skill findOrCreateSkill(Long skillId, Long jobId) {//finds a skill by their skillId or creates a new skill if it doesn't already exist 
		if (Objects.isNull(skillId)) {
			Skill skill = new Skill();
			return skill;
			//returns new Skill if skillId is null
		} else {
			return findSkillById(skillId, jobId);
		}
	}
	
	private Skill findSkillById(Long skillId, Long jobId) {//finds a skill by its skillId 
		Skill skill = skillDao.findById(skillId).orElseThrow(() -> new NoSuchElementException("Skill with ID=" + skillId +" was not found."));
		boolean found = false; 
		
		for(Job jobIdentified : skill.getJobs()) {
			if(jobIdentified.getJobId() == jobId) {
				found = true; 
				break;
			}
		}
		if(!found) { 
			throw new IllegalArgumentException("This skill does not exist.");
		}
		return skill;
	}

	
	private void copySkillFields(Skill skill, JobSkill jobSkill) {//sets the values of the Skill to a JobSkill object 
		skill.setSkillName(jobSkill.getSkillName());
		skill.setSkillLevel(jobSkill.getSkillLevel());
		skill.setSkillId(jobSkill.getSkillId());
	}

	@Transactional(readOnly = false)
	public JobReference saveReference(Long jobId, JobReference jobReference) { //saves the newly added reference to the database 
		Job job = findJobById(jobId);
		Long referenceId = jobReference.getReferenceId();
		Reference reference = findOrCreateReference(referenceId, jobId);
	
		copyReferenceFields(reference, jobReference);
		reference.setJob(job);
		
		Set<Reference> referenceSet = job.getReferences();
		referenceSet.add(reference); //adds the reference to the job reference set 
		job.setReferences(referenceSet); //sets this new set to the job reference set 
		
		Reference dbReference = referenceDao.save(reference);
		return new JobReference(dbReference); 
	}

	private Reference findOrCreateReference(Long referenceId, Long jobId) {//finds a reference or creates one if that referenceId does not exist 
		if (Objects.isNull(referenceId)) {
			Reference reference = new Reference();
			return reference;
			//returns new Reference if referenceId is null
		} else {
			return findReferenceById(jobId, referenceId);
		}
	}
	
	private Reference findReferenceById(Long jobId, Long referenceId) {//finds a reference by their referenceId
		Reference reference = referenceDao.findById(referenceId).orElseThrow(() -> new NoSuchElementException("Reference with ID=" + referenceId +" was not found."));
		if (reference.getJob().getJobId() == jobId) {
			return reference;
		} else {
			throw new IllegalArgumentException("This reference does not belong to the job with a job ID=" + referenceId);
		}
	}
	
	private void copyReferenceFields(Reference reference, JobReference jobReference) {//copies reference fields to a JobReference object 
		reference.setReferenceId(jobReference.getReferenceId());
		reference.setReferenceName(jobReference.getReferenceName());
		reference.setReferencePhoneNumber(jobReference.getReferencePhoneNumber());
		reference.setReferenceEmail(jobReference.getReferenceEmail());
	}
	
//	@Transactional(readOnly = false)
//	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {//saves the newly created customer to the database 
//		PetStore petStore = findPetStoreById(petStoreId);
//		Long customerId = petStoreCustomer.getCustomerId();
//		Customer customer = findOrCreateCustomer(customerId, petStoreId);
//	
//		copyCustomerFields(customer, petStoreCustomer);
//		
//		Set<PetStore> petStoreSet = customer.getPetStores();
//		petStoreSet.add(petStore); //adds the pet store to the customer petStore set 
//		customer.setPetStores(petStoreSet); //sets this new set to the petStore customer set 
//		
//		Set<Customer> customerSet = petStore.getCustomers();
//		customerSet.add(customer); //adds the customer to the petStore customer set 
//		petStore.setCustomers(customerSet); //sets this new set to the petStore customer set 
//		
//		Customer dbCustomer = customerDao.save(customer);
//		return new PetStoreCustomer(dbCustomer); 
//		
//	}
//

//
//	@Transactional(readOnly = false)
//	public List<PetStoreData> retrieveAllPetStores() { //uses findAll from the PetStoreDao class to retrieve all pet stores without their customer and employee data 
//		List<PetStore> petStores = petStoreDao.findAll();
//		List<PetStoreData> result = new LinkedList<>();
//		for(PetStore petStore : petStores) {
//			
//			PetStoreData petStoreData = new PetStoreData(petStore); 
//			petStoreData.getPetStoreCustomer().clear(); 
//			petStoreData.getPetStoreEmployee().clear();
//			
//			result.add(petStoreData);
//		}
//		return result;
//	}
//
//	@Transactional(readOnly = false)
//	public PetStoreData getPetStoreById(Long petStoreId) {//returns the pet store data of one pet store 
//		PetStore ps = findPetStoreById(petStoreId);
//		PetStoreData psd = new PetStoreData(ps);
//		return psd;
//	}
//
//	public Map<String, String> deletePetStoreById(Long petStoreId) {//deletes a pet store by their petStoreId and returns a message that it was deleted 
//		PetStore ps = findPetStoreById(petStoreId);
//		petStoreDao.delete(ps);
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("message","Pet store with ID=" + petStoreId + " was successfully deleted.");
//		return null;
//	}
}
