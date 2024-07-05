package model;

import java.io.Serializable;

public class Employee implements Serializable {
    private String name;
    private String department;
    private int hoursWorked;
    private double salary;

    public Employee(String name, String department, int hoursWorked, double salary) {
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return name + " (" + department + "), Salary: $" + salary;
    }
}
