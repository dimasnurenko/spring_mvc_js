package com.dmitry.shnurenko.spring.mvc.controllers;

import com.dmitry.shnurenko.spring.mvc.dao.EmployeeDao;
import com.dmitry.shnurenko.spring.mvc.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final EmployeeDao employeeDao;

    @Autowired
    public MainController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome() {
        return "index";
    }

    @RequestMapping(value = "add/employee",
                    method = RequestMethod.POST,
                    produces = "application/json")
    public @ResponseBody Employee addEmployee(@RequestParam("id") int id,
                                              @RequestParam("name") String name) throws SQLException {
        Employee employee = new Employee(id, name);

        boolean isSaved = employeeDao.save(employee);

        if (isSaved) {
            return employee;
        } else {
            throw new SQLException("Can't save employee to database...");
        }
    }

    @RequestMapping(value = "get/all",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public @ResponseBody List<Employee> getAll() {
        return employeeDao.getAllEmployees();
    }

    @RequestMapping(value = "delete/employee{id}",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public void removeEmployee(@PathVariable("id") int id) throws SQLException {

        boolean isRemoved = employeeDao.delete(id);

        if (!isRemoved) {
            throw new SQLException("Employee can't be deleted...");
        }
    }
}