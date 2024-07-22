package com.example.demo.Controller;

import com.example.demo.Entity.Counter;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.CounterService;
import com.example.demo.Service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final StaffService staffService;
    private final CounterService counterService;

    public StaffController(StaffService staffService, CounterService counterService) {
        this.staffService = staffService;
        this.counterService = counterService;
    }

    @GetMapping("/staff")
    public String showStaffList(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Staff> staffPage = staffService.findAllExcludingManager(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "staffID")));
        model.addAttribute("staffs", staffPage);
        model.addAttribute("currentPage", staffPage.getNumber());
        model.addAttribute("totalPages", staffPage.getTotalPages());
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        model.addAttribute("staff", staff);

        List<Object[]> topStaff = orderRepository.findTop5OrdersByTotal();
        model.addAttribute("topStaff", topStaff);

        return "manager/staffList";
    }

    @GetMapping("/staff/create-new-staff")
    public String createNewStaff(Model model) {
        model.addAttribute("staff", new Staff());
        
        List<Counter> counters = counterService.findAll();
        model.addAttribute("counters", counters);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staffs = staffRepository.findByEmail(email);
        model.addAttribute("staffs", staffs);

        return "manager/createNewStaff";
    }

    @PostMapping("/staff/create-new-staff/save")
    public String saveNewStaff(@ModelAttribute Staff staff, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staffs = staffRepository.findByEmail(email);
        model.addAttribute("staffs", staffs);

        try {
            staffService.save(staff);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("staff", staff);

            List<Counter> counters = counterService.findAll();
            model.addAttribute("counters", counters);

            return "manager/createNewStaff";
        }

        return "redirect:/staff";
    }

    @GetMapping("staff/edit-staff-profile/{staffID}")
    public String editStaffProfile(@PathVariable Integer staffID, Model model) {
        Staff staff = staffService.findStaffById(staffID)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        model.addAttribute("staff", staff);

        List<Counter> counters = counterService.findAll();
        model.addAttribute("counters", counters);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staffs = staffRepository.findByEmail(email);
        model.addAttribute("staffs", staffs);

        return "manager/editStaffProfile";
    }

    @PostMapping("/staff/edit-staff-profile/{staffID}/save")
    public String updateStaffProfile(@PathVariable Integer staffID, @ModelAttribute Staff staff, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staffs = staffRepository.findByEmail(email);
        model.addAttribute("staffs", staffs);

        try {
            staffService.update(staff);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("staff", staff);

            List<Counter> counters = counterService.findAll();
            model.addAttribute("counters", counters);

            return "manager/editStaffProfile";
        }

        return "redirect:/staff";
    }
}
