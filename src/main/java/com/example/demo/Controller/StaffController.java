package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffController {
	   @GetMapping("/staff")
	   public String ListStaff() {
		   return "manager/staffList";
	   }
	   @GetMapping("staff/create-new-staff")
	   public String CreateNewStaff() {
		   return "manager/createNewStaff";
	   }
	   @GetMapping("staff/edit-staff-profile")
	   public String EditStaffProfile() {
		   return"manager/editStaffProfile";
	   }

}
