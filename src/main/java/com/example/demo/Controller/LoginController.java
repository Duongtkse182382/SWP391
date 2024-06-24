package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Service.PasswordEncoderService;

@Controller
public class LoginController {
    @Autowired
    private PasswordEncoderService passwordEncoderService;

  @GetMapping("/login")
  public String loginPage() {
	  return "login/login";
  }
  
  @GetMapping("/encode-password")
  @ResponseBody
  public String encodePassword(@RequestParam String password) {
      String encodedPassword = passwordEncoderService.encodePassword(password);
      return "Encoded Password: " + encodedPassword;
  }
}
