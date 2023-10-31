package resume.item.tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import resume.item.tracker.controller.model.JobData;
import resume.item.tracker.controller.model.JobData.JobSkill;
import resume.item.tracker.controller.model.JobData.JobReference;
import resume.item.tracker.service.ResumeItemTrackerService;

@RestController
@RequestMapping("/resume_item_tracker") 
@Slf4j
public class ResumeItemTrackerController {

	
	@Autowired
	private ResumeItemTrackerService resumeItemTrackerService;
	
	@PostMapping("/job") //maps post verbs with url values that end in /job to this method 
	@ResponseStatus(code = HttpStatus.CREATED)
	public JobData insertJob (@RequestBody JobData jobData) {
		log.info("Creating job!");
		return resumeItemTrackerService.saveJob(jobData); //calls on savevJob method from ResumeItemTrackerService to save the post info to the database 
	}
	
	@PostMapping("/{jobId}/skill") //maps post verbs with url values that end in /{jobId}/skill to this method 
	@ResponseStatus(code = HttpStatus.CREATED)
	public JobSkill insertSkill (@RequestBody JobSkill jobSkill, @PathVariable Long jobId) {
		log.info("Creating skill!");
		return resumeItemTrackerService.saveSkill(jobId, jobSkill);
	}
	
	@PostMapping("/{jobId}/reference")
	@ResponseStatus(code = HttpStatus.CREATED)
	public JobReference insertReference (@RequestBody JobReference jobReference, @PathVariable Long jobId) {
		log.info("Creating reference.");
		return resumeItemTrackerService.saveReference(jobId, jobReference);
	}
	
	@PutMapping("/job/{jobId}") //maps put verbs with url values that end in /job/{jobId} to this method 
	public JobData modifyJob(@PathVariable Long jobId, @RequestBody JobData jobData) {
		jobData.setJobId(jobId);
		log.info("Modifying job.");
		return resumeItemTrackerService.saveJob(jobData); //calls on saveJob method from ResumeItemTrackerService to save the post info to the database
	}
	
	@PutMapping("/{jobId}/skill/{skillId}") //maps put verbs with url values that end in /{jobId}/skill/{skillId} to this method 
	public JobSkill modifySkill(@PathVariable Long skillId, @PathVariable Long jobId, @RequestBody JobSkill jobSkill) {
		jobSkill.setSkillId(skillId);
		log.info("Modifying skill.");
		return resumeItemTrackerService.saveSkill(jobId, jobSkill); //calls on saveSkill method from ResumeItemTrackerService to save the post info to the database
	}
	
	@PutMapping("/{jobId}/reference/{referenceId}") //maps put verbs with url values that end in /{jobId}/reference/{referenceId} to this method 
	public JobReference modifyReference(@PathVariable Long referenceId, @PathVariable Long jobId, @RequestBody JobReference jobReference) {
		jobReference.setReferenceId(referenceId);
		log.info("Modifying reference.");
		return resumeItemTrackerService.saveReference(jobId, jobReference); //calls on saveReference method from ResumeItemTrackerService to save the post info to the database
	}
	
//	@PostMapping("/{petStoreId}/employee") //maps the post verbs with url values that end in /pet_store/{petStoreId}/employee to this method 
//	@ResponseStatus(code = HttpStatus.CREATED)
//	public PetStoreEmployee insertEmployee (@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
//		log.info("Adding new employee!");
//		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
//	}
//	
//	@PostMapping("/{petStoreId}/customer") //maps the post verbs with url values that end in /pet_store/{petStoreId}/employee to this method 
//	@ResponseStatus(code = HttpStatus.CREATED)
//	public PetStoreCustomer insertCustomer (@PathVariable Long petStoreId, @RequestBody PetStoreCustomer petStoreCustomer) {
//		log.info("Adding new customer!");
//		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
//	}
//	
//	@GetMapping("/pet_store") //maps get verbs with /pet_store/pet_store to this method 
//	public List<JobData> listPetStores(){
//		return petStoreService.retrieveAllPetStores();
//	}
//	
//	@GetMapping("/{petStoreId}") //maps get verbs with /pet_store/{petStoreId} to this method 
//	public JobData getPetStoreById(@PathVariable Long petStoreId) {
//		return petStoreService.getPetStoreById(petStoreId);
//	}
//	
//	@DeleteMapping("/{petStoreId}") //maps delete verbs with /pet_store/{petStoreId} to this method 
//	public Map<String,String> deletePetStoreById(@PathVariable Long petStoreId) {
//		log.info("Deleted pet store with ID=" + petStoreId); 
//		return petStoreService.deletePetStoreById(petStoreId);
//	}
}
