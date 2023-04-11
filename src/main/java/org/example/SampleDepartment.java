package org.example;

import java.util.Objects;

public class SampleDepartment {
    private int department_id;
    private String department_name;
    private Integer manager_id;
    private Integer location_id;

    public SampleDepartment() {
    }

    public SampleDepartment(int department_id, String department_name, Integer manager_id, Integer location_id) {
        this.setDepartment_id(department_id);
        this.setDepartment_name(department_name);
        this.setManager_id(manager_id);
        this.setLocation_id(location_id);
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public Integer getManager_id() {
        return manager_id;
    }

    public void setManager_id(Integer manager_id) {
        this.manager_id = manager_id;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SampleDepartment that = (SampleDepartment) o;
        return department_id == that.department_id && manager_id.intValue() == that.manager_id.intValue() && location_id.intValue() == that.location_id.intValue() && department_name.equals(that.department_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department_id, department_name);
    }

    @Override
    public String toString() {
        return "SampleDepartment{" +
                "department_id=" + department_id +
                ", department_name='" + department_name + '\'' +
                ", manager_id=" + manager_id +
                ", location_id=" + location_id +
                '}';
    }
}
