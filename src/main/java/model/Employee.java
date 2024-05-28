package model;

import javax.persistence.*;

@Entity
@Table(name = "Employee")
public class Employee extends BaseEntity {
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "salary")
    private double salary;
    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    public Employee(String name , String lastName , double salary, Location l) {
        this.firstName = name;
        this.lastName = lastName;
        this.salary = salary;
        this.location = l;
    }

    public Employee() {
        this.firstName = null;
        this.lastName = null;
        this.salary = 0.0;
        this.location = new Location();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public Location getLocation() {
        return location;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void displayDetails() {
        System.out.println("Employee Name: " + firstName);
        System.out.println("Employee Last Name: " + lastName);
        System.out.println("Salary: " + salary);
    }

    public void updateSalary(double newSalary) {
        this.salary = newSalary;
    }

}
