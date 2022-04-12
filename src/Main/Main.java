package Main;
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
import Model.Course;
import Model.CourseFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

//        CourseFormat courseFormat = CourseFormatBuilder.builder()
//                .format("BOOTCAMP")
//                .course_duration_weeks(3)
//                .lesson_per_week(5)
//                .lesson_duration(LocalTime.of(3,0))
//                .is_online(false)
//                .build();
//        CourseFormatDao courseFormatDao = (CourseFormatDao) DaoFactory.autowired("CourseFormatDao","singleton");
//        courseFormatDao.save(courseFormat);
//
//
//        Course course = CourseBuilder.builder()
//                .name("Java")
//                .price(1500.0)
//                .courseFormat(courseFormatDao.findById(courseFormat.getId()))
//                .build();
//
//        CourseDao courseDao = (CourseDao) DaoFactory.autowired("CourseDao", "singleton");
//        courseDao.save(course);
//        List<Course> courseList = new ArrayList<>();
//        courseList.add(course);






       CourseFormatDao courseFormatDao = (CourseFormatDao) DaoFactory.autowired("CourseFormatDao","singleton");
      CourseFormat courseFormat = new CourseFormat();
       courseFormat.setFormat("BootCamp");
   courseFormat.setLesson_per_week(5);
   courseFormat.setCourse_duration_weeks(5);
    courseFormat.setLesson_duration(LocalTime.ofSecondOfDay(3));
   courseFormat.setIs_online(false);

      // courseFormat = courseFormatDao.save(courseFormat);


  CourseDao courseDao = (CourseDao) DaoFactory.autowired("CourseDao", "singleton");
      Course course = new Course();
   course.setName("PythonBootcamp");
     course.setPrice(160.00);
     course.setCourseFormat(courseFormatDao.findById(1L));
     course = courseDao.save(course);

        System.out.println(course);
//
//
//
//


    }
}
