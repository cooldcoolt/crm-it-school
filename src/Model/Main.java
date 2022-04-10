package Model;
import Main.DaoFactoryUtil.DaoFactory;
import Main.ManagerDao;
import Main.StudentDao;
import Main.MentorDao;
import Main.CourseDao;
import Main.impl.MentorImp;
import Main.impl.StudentImpl;
import Main.CourseFormatDao;
import Model.Builder.CourseBuilder;
import Model.Builder.CourseFormatBuilder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        CourseFormat courseFormat = CourseFormatBuilder.builder()
                .format("BOOTCAMP")
                .course_duration_weeks(3)
                .lesson_per_week(5)
                .lesson_duration(LocalTime.of(3,0))
                .is_online(false)
                .build();
        CourseFormatDao courseFormatDao = (CourseFormatDao) DaoFactory.autowired("CourseFormatDao","singleton");
        courseFormatDao.save(courseFormat);


        Course course = CourseBuilder.builder()
                .name("Java")
                .price(1500.0)
                .courseFormat(new CourseFormat())
                .build();

        CourseDao courseDao = (CourseDao) DaoFactory.autowired("CourseDao", "singleton");
        courseDao.save(course);
//        List<Course> courseList = new ArrayList<>();
//        courseList.add(course);






//        CourseFormatDao courseFormatDao = (CourseFormatDao) DaoFactory.autowired("CourseFormatDao","singleton");
//        CourseFormat courseFormat = new CourseFormat();
//        courseFormat.setFormat("BootCamp");
//        courseFormat.setLesson_per_week(5);
//        courseFormat.setCourse_duration_weeks(5);
//        courseFormat.setLesson_duration(LocalTime.ofSecondOfDay(3));
//        courseFormat.setIs_online(false);
//        courseFormat.setId(10l);
//        courseFormatDao.save(courseFormat);





//  CourseDao courseDao = (CourseDao) DaoFactory.autowired("CourseDao", "singleton");
//        Course course = new Course();
//        course.setName("JavaBootCamp");
//        course.setPrice(150.00);
//        course.setCourseFormat(courseFormat);
//        courseDao.save(course);
//       // managerDao= DaoFactory.getManagerDaoSql("prototype");

    //    System.out.println(courseDao.findAll());



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
       // ManagerDao managerDao = DaoFactory.getManagerDaoSql();
        //System.out.println("From database: " + managerDao.save(manager));
        //System.out.println(managerDao.findById(1L));
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
        //  StudentDao student = DaoFactory.getStudentDaoSql();
        //System.out.println(student.findById(2L));

        ////////////////////////MENTOR/////////////////

        //




    }
}
