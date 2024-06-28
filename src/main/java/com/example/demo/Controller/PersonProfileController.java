package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Staff;
import com.example.demo.Repository.StaffRepository;

@Controller
public class PersonProfileController {
	   @Autowired
	    private StaffRepository staffRepository;
	  
	   @Autowired
	    private BCryptPasswordEncoder bCryptPasswordEncoder;
	   

   @GetMapping("/cashier-profile")
   public String CashierProfile(Model model) {
	   String email = SecurityContextHolder.getContext().getAuthentication().getName();
       Staff staff = staffRepository.findByEmail(email);
       model.addAttribute("staff", staff);
	   return "cashier/profile";
   }
   @PostMapping("/cashier-profile")
   public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
       Staff staff = staffRepository.findByEmail(email);
       if (!bCryptPasswordEncoder.matches(currentPassword, staff.getPassword())) {
           model.addAttribute("currentPasswordError", "Current password is incorrect.");
       } else if (!newPassword.equals(confirmPassword)) {
           model.addAttribute("confirmPasswordError", "New password and confirmation do not match.");
       } else {
           String encodedNewPassword = bCryptPasswordEncoder.encode(newPassword);
           staffRepository.updatePasswordByEmail(email, encodedNewPassword);
           model.addAttribute("passwordUpdateSuccess", "Update password successfully.");
       }
       model.addAttribute("staff", staff);
       return "cashier/profile";
   }

   @PostMapping("/cashier-profile/save")
   public String updateCashierProfile(@RequestParam("address") String address,
                               @RequestParam("phoneNumber") String phoneNumber,
                               Model model) {
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
       Staff staff = staffRepository.findByEmail(email);
       staff.setAddress(address);
       staff.setPhoneNumber(phoneNumber);
       staffRepository.save(staff);
       model.addAttribute("staff", staff);
       model.addAttribute("updateSuccess", "Profile updated successfully.");
       return "cashier/profile";
   }
   @GetMapping("/seller-profile")
   public String SellerProfile(Model model) {
	   String email = SecurityContextHolder.getContext().getAuthentication().getName();
       Staff staff = staffRepository.findByEmail(email);
       model.addAttribute("staff", staff);
	   return "seller/personalProfile";
   }
 
   
   @PostMapping("/seller-profile")
   public String changePasswordSeller(@RequestParam("currentPassword") String currentPassword,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
       Staff staff = staffRepository.findByEmail(email);
       if (!bCryptPasswordEncoder.matches(currentPassword, staff.getPassword())) {
           model.addAttribute("currentPasswordError", "Current password is incorrect.");
       } else if (!newPassword.equals(confirmPassword)) {
           model.addAttribute("confirmPasswordError", "New password and confirmation do not match.");
       } else {
           String encodedNewPassword = bCryptPasswordEncoder.encode(newPassword);
           staffRepository.updatePasswordByEmail(email, encodedNewPassword);
           model.addAttribute("passwordUpdateSuccess", "Update password successfully.");
       }
       model.addAttribute("staff", staff);
       return "seller/personalProfile";
   }
   @PostMapping("/seller-profile/save")
   public String updateStaffProfile(@RequestParam("address") String address,
                               @RequestParam("phoneNumber") String phoneNumber,
                               Model model) {
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
       Staff staff = staffRepository.findByEmail(email);
       staff.setAddress(address);
       staff.setPhoneNumber(phoneNumber);
       staffRepository.save(staff);
       model.addAttribute("staff", staff);
       model.addAttribute("updateSuccess", "Profile updated successfully.");
       return "seller/personalProfile";
   }
   @GetMapping("/manager-profile")
   public String MangagerProfile(Model model) {
	   String email = SecurityContextHolder.getContext().getAuthentication().getName();
       Staff staff = staffRepository.findByEmail(email);
       model.addAttribute("staff", staff);
	   return "manager/personalProfile";
   }
   @PostMapping("/manager-profile")
   public String changePasswordManager(@RequestParam("currentPassword") String currentPassword,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
       Staff staff = staffRepository.findByEmail(email);
       if (!bCryptPasswordEncoder.matches(currentPassword, staff.getPassword())) {
           model.addAttribute("currentPasswordError", "Current password is incorrect.");
       } else if (!newPassword.equals(confirmPassword)) {
           model.addAttribute("confirmPasswordError", "New password and confirmation do not match.");
       } else {
           String encodedNewPassword = bCryptPasswordEncoder.encode(newPassword);
           staffRepository.updatePasswordByEmail(email, encodedNewPassword);
           model.addAttribute("passwordUpdateSuccess", "Update password successfully.");
       }
       model.addAttribute("staff", staff);
       return "manager/personalProfile";
   }
   @PostMapping("/manager-profile/save")
   public String updateProfile(@RequestParam("address") String address,
                               @RequestParam("phoneNumber") String phoneNumber,
                               Model model) {
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
       Staff staff = staffRepository.findByEmail(email);
       staff.setAddress(address);
       staff.setPhoneNumber(phoneNumber);
       staffRepository.save(staff);
       model.addAttribute("staff", staff);
       model.addAttribute("updateSuccess", "Profile updated successfully.");
       return "manager/personalProfile";
   }
 
   @GetMapping("/warranty")
   public String Warranty() {
	   return "cashier/warranty";
   }
}
