package Model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student {

    private long id;
    private String first_name;
    private String Last_name;
    private String email;
    private String phone_number;
    private Date dob;
    private LocalDateTime date_created;


   public Student(){this.date_created = LocalDateTime.now();
   }

    public Student(long id, String first_name, String last_name, String email, String phone_number, LocalDateTime dob) {
        this.id = id;
        this.first_name = first_name;
        Last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.dob = Date.valueOf(LocalDate.from(dob));

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }



    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", Last_name='" + Last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", dob=" + dob +
                ", date_created=" + date_created +
                '}';
    }
}

