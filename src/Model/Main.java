package Model;
import Main.DaoFactoryUtil.DaoFactory;
import Main.ManagerDao;
import Main.StudentDao;
import Main.impl.StudentImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
            Manager manager = new Manager();
//        Student student = new Student();


        /*System.out.print("First name: ");
        manager.setFirst_name(scan.nextLine());

        System.out.print("Last name: ");
        manager.setLast_name(scan.nextLine());

        System.out.print("Email: ");
        manager.setEmail(scan.nextLine());

        System.out.print("Phone number: ");
        manager.setPhone_number(scan.nextLine());

        System.out.print("Date of birth: ");
        manager.setDob(LocalDate.parse(scan.nextLine())); // yyyy-MM-dd

        System.out.print("Salary: ");
        manager.setSalary(scan.nextDouble());
        System.out.println("Input: " + manager);

     //   FIND BY ID POISK PO ID*/
        ManagerDao managerDao = DaoFactory.getManagerDaoSql();
        //System.out.println("From database: " + managerDao.save(manager));
        System.out.println(managerDao.findById(1L));
        //  System.out.println(managerDao.findById(1L));

        /////////STUDENT////////
//        System.out.print("Student first name: ");
//        student.setFirst_name(scan.nextLine());
//
//        System.out.print("Student last name: ");
//        student.setLast_name(scan.nextLine());
//
//        System.out.print("Student email: ");
//        student.setEmail(scan.nextLine());
//
//        System.out.print("Student phone number: ");
//        student.setPhone_number(scan.nextLine());
//
//        System.out.print("Student date of Birth: ");
//        student.setDob(Date.valueOf(LocalDate.parse(scan.nextLine())));
//        System.out.println("Student input " + student);
//
//        StudentImpl studentImp = new StudentImpl();
//        studentImpl.save(student);

        //FIND BY ID
          StudentDao student = DaoFactory.getStudentDaoSql();
        System.out.println(student.findById(2L));


    }
}
