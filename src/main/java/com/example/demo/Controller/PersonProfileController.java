package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonProfileController {
   @GetMapping("/cashier-profile")
   public String CashierProfile() {
	   return "cashier/profile";
   }
   
   @GetMapping("/seller-profile")
   public String SellerProfile() {
	   return "seller/personalProfile";
   }
   @GetMapping("/manager-profile")
   public String MangagerProfile() {
	   return "manager/personalProfile";
   }

   @GetMapping("/warranty")
   public String Warranty() {
	   return "cashier/warranty";
   }
}
