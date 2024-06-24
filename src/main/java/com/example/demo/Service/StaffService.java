package com.example.demo.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.Entity.Staff;


public interface StaffService {
	 Page<Staff> findAllExcludingManager(Pageable pageable);
	Staff save(Staff staff);
	List<Staff> getAllStaff();
	Optional<Staff> findStaffById(Integer id);
	boolean emailExists(String email);
	 void update(Staff staff) throws Exception;
	    boolean isEmailExistsForOtherStaff(String email, Integer staffID);
	
}
