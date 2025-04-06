package com.example.employeeapi.controller;

import com.example.employeeapi.model.Employee;
import com.example.employeeapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Serve the landing page with the form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Employee employee = (Employee) employeeService.findById(id).orElse(null);
        model.addAttribute("employee", employee);
        return "edit";
    }

    // Handle form submission and save employee
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/displayAll";
    }

    // Display all employees
    @GetMapping("/displayAll")
    public String displayAll(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "displayAll";
    }

    // Display a specific employee by ID
    @GetMapping("/display/{id}")
    public String displayEmployee(@PathVariable String id, Model model) {
        Optional<Employee> employeeOpt = employeeService.getEmployeeById(id);
        if (employeeOpt.isPresent()) {
            model.addAttribute("employee", employeeOpt.get());
            return "display";
        } else {
            return "error";
        }
    }
}