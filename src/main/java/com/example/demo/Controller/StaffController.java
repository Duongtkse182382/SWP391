package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.StaffService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Controller
public class StaffController {
	@Autowired
    private StaffRepository staffRepository;
	  private final StaffService staffService;
	  public StaffController(StaffService staffService) {
	         this.staffService = staffService;
	   }
	  
	   @GetMapping("/staff")
	    public String showStaffList(Model model, @RequestParam(defaultValue = "0") int page) {
	        Page<Staff> staffPage = staffService.findAllExcludingManager(PageRequest.of(page, 10));
	        model.addAttribute("staffs", staffPage);
	        model.addAttribute("currentPage", staffPage.getNumber());
	        model.addAttribute("totalPages", staffPage.getTotalPages());
	        String email = SecurityContextHolder.getContext().getAuthentication().getName();
		       Staff staff = staffRepository.findByEmail(email);
		       model.addAttribute("staff", staff);
	        return "manager/staffList";
	    }
	   
	  @GetMapping("/staff/create-new-staff")
	    public String createNewStaff(Model model) {
	          model.addAttribute("staff", new Staff());
	          String email = SecurityContextHolder.getContext().getAuthentication().getName();
		       Staff staffs = staffRepository.findByEmail(email);
		       model.addAttribute("staffs", staffs);
	        return "manager/createNewStaff";
	    }
	  
	    @PostMapping("/staff/create-new-staff/save")
	    public String saveNewStaff(@ModelAttribute Staff staff,Model model) {
	    	 String email = SecurityContextHolder.getContext().getAuthentication().getName();
	         Staff staffs = staffRepository.findByEmail(email);
	         model.addAttribute("staffs", staffs);
	        staffService.save(staff);
	        return "manager/createNewStaff";
	    }
	  
	   @GetMapping("staff/edit-staff-profile/{staffID}")
	   public String EditStaffProfile(@PathVariable Integer staffID, Model model) {
		   Staff staff = staffService.findStaffById(staffID).orElseThrow(() -> new RuntimeException("Staff not found")); 
	       model.addAttribute("staff", staff);
	       String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staffs = staffRepository.findByEmail(email);
	       model.addAttribute("staffs", staffs);
		   return"manager/editStaffProfile";
	   }
	   @PostMapping("/staff/edit-staff-profile/{staffID}/save")
	   public String updateStaffProfile(@PathVariable Integer staffID, @ModelAttribute Staff staff, Model model) throws Exception {
		   String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staffs = staffRepository.findByEmail(email);
	       model.addAttribute("staffs", staffs);
		    staffService.update(staff);
		    return"manager/editStaffProfile";
	   }



}
