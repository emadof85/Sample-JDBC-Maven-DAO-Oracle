package org.example;

import java.util.List;

import static org.testng.Assert.*;

public class DepartmentDAOTest {

    private DepartmentDAO departmentDAO = null;
    @org.testng.annotations.BeforeMethod
    public void setUp() {
        departmentDAO = new DepartmentDAO();
    }

    @org.testng.annotations.AfterMethod
    public void tearDown() {
        departmentDAO.close();
    }

    @org.testng.annotations.Test
    public void testGetAllDepartments() {
        List<SampleDepartment> departments = departmentDAO.getAllDepartments();
        assertEquals( 29, departments.size(), "Wrong number of departments !!");
        SampleDepartment first = departments.get(departments.size()-1);
        assertEquals( "Payroll", first.getDepartment_name(), "last department does not match !!");
    }

    @org.testng.annotations.Test
    public void testGetDepartmentById() {
        SampleDepartment department = new SampleDepartment(270, "Payroll",0,1700);
        assertEquals( department, departmentDAO.getDepartmentById(270), "departments does not match !!");
    }

    @org.testng.annotations.Test
    public void testInsertDepartment() {
        SampleDepartment department = new SampleDepartment(500, "test_dept", 201, 1800);
        assertTrue(departmentDAO.insertDepartment(department),"Unable to insert department");
    }

    @org.testng.annotations.Test
    public void testUpdateDepartment() {
        SampleDepartment department = new SampleDepartment(500, "new_test_dept", 201, 1700);
        assertTrue(departmentDAO.updateDepartment(department),"Unable to update department");
    }
}