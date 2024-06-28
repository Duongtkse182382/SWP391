package com.example.demo.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
        Staff findByEmail(String email);
        boolean existsByEmail(String email);  
        
        @Query("SELECT s FROM Staff s WHERE s.role.roleName != :roleName")
        Page<Staff> findAllExcludingRole(Pageable pageable, @Param("roleName") String roleName);
        
        @Modifying
	    @Query("UPDATE Staff s SET s.password = :password WHERE s.email = :email")
	    @Transactional
	    void updatePasswordByEmail(String email, String password);      
        @Query("SELECT s FROM Staff s WHERE s.email = :email AND s.staffID <> :staffID")
        List<Staff> findByEmailAndIdNot(@Param("email") String email, @Param("staffID") Integer staffID);
}
