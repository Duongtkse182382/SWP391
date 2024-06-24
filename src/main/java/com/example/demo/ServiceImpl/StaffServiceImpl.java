package com.example.demo.ServiceImpl;

import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.StaffService;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Staff;
@Service
public class StaffServiceImpl implements StaffService {
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private  BCryptPasswordEncoder bCryptPasswordEncoder;


	
	public StaffServiceImpl (StaffRepository staffRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.staffRepository = staffRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	  @Override
	    public Page<Staff> findAllExcludingManager(Pageable pageable) {
	        return staffRepository.findAllExcludingRole(pageable, "manager");
	    }
	  
	   
	  @Override
	  @Transactional
	    public Staff save(Staff staff) {
		   if (staffRepository.existsByEmail(staff.getEmail())) {
	            throw new IllegalArgumentException("Email '" + staff.getEmail() + "' is already in use.");
	        }else {
		        staff.setPassword(bCryptPasswordEncoder.encode(staff.getPassword()));
		        staff.setActive(true);
		        return staffRepository.save(staff);
	        }
	    }
	  
	  @Override
	  public List<Staff> getAllStaff(){
		  return staffRepository.findAll();
	  }
	  @Override
	  public Optional<Staff> findStaffById(Integer id){
		  return staffRepository.findById(id);
	  }
	    @Override
	    public boolean emailExists(String email) {
	        return staffRepository.existsByEmail(email);
	    }
	    @Override
	    public boolean isEmailExistsForOtherStaff(String email, Integer staffID) {
	        List<Staff> staffList = staffRepository.findByEmailAndIdNot(email, staffID);
	        return !staffList.isEmpty();
	    }
	    @Override
	    public void update(Staff staff) throws Exception {
	        Integer staffId = staff.getStaffID();
	        String newEmail = staff.getEmail();
	        Staff existingStaff = staffRepository.findById(staffId)
	                .orElseThrow(() -> new RuntimeException("Staff not found"));

	        // Check if the email is the same or not assigned yet
	        if (existingStaff.getEmail() == null || existingStaff.getEmail().equals(newEmail)) {
	            // Check if the email exists for another staff excluding the current staff
	        	 staff.setPassword(existingStaff.getPassword());
	                staffRepository.save(staff);
	        } else {
	            // Check if the new email exists for another staff
	            Staff staffWithSameEmail = staffRepository.findByEmail(newEmail);
	            if (staffWithSameEmail == null || staffWithSameEmail.getStaffID() == staffId) {
	                // Check if the email exists for another staff excluding the current staff
	                if (!isEmailExistsForOtherStaff(newEmail, staffId)) {
	                	 staff.setPassword(existingStaff.getPassword());
	                    staffRepository.save(staff);
	                } else {
	                    throw new Exception("Email đã tồn tại. Vui lòng chọn email khác.");
	                }
	            } else {
	                throw new Exception("Email đã tồn tại. Vui lòng chọn email khác.");
	            }
	        }
	    }


	   
}
	    
	   

