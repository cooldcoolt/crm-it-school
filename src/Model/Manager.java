package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Manager {

    private Long id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private LocalDate dob;
    private Double salary;
    private LocalDateTime date_created;

    public Manager() {
        this.date_created = LocalDateTime.now();
    }
    public Manager (Long id, String first_name, String last_name, String phone_number, String email, LocalDateTime dob, Double salary){

        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
        this.dob = LocalDate.from(dob);
        this.salary  = salary;
        this.date_created = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", salary=" + salary +
                ", date_created=" + date_created +
                '}';
    }
}


