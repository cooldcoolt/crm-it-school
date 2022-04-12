package Main.impl;

import Main.CourseDao;
import Main.DaoFactoryUtil.Error_LOG;
import Model.Course;
import Model.CourseFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseImpl implements CourseDao {

    public CourseImpl (){

        Connection connection  = null;
        Statement statement = null;

        try{
            Error_LOG.info(this.getClass().getSimpleName() + "GroupImpl() ", Connection.class.getSimpleName(),"esteblishing connection: ");
            connection = getConnection();

            String ddlQueryCourseImpl=" CREATE TABLE IF NOT EXISTS tb_courses ("+
                    "id SERIAL, "+
                    "name VARCHAR(50) NOT NULL, "+
                    "price MONEY NOT NULL, "+
                    "date_created TIMESTAMP NOT NULL DEFAULT NOW(), "+
                    "course_format_id INT NOT NULL, "+
                    ""+
                    "CONSTRAINT pk_course_id PRIMARY KEY (id), "+
                    "CONSTRAINT fk_course_format_id FOREIGN KEY (course_format_id) "+
                    "REFERENCES tb_course_format(id)"+
                    ");";

            statement =connection.createStatement();

            statement.execute(ddlQueryCourseImpl);




        }catch (SQLException e){
            Error_LOG.info(this.getClass().getSimpleName() + "CourseImp:  ", Connection.class.getSimpleName(), e.getMessage());
            e.printStackTrace();
        }

    }


    @Override
    public Course save(Course course) {

       Connection connection = null;
       PreparedStatement preparedStatement = null;
       ResultSet resultSet = null;
       Course savedCourse = null;

       try{
           System.out.println("Esteblishing connection");
           connection = getConnection();
           System.out.println("CourseFormatImpl");

           String createQueryCourse = "INSERT INTO tb_courses ("+
                   "name, price, date_created, course_format_id )"+
                   "VALUES (?, MONEY(?), ?, ?)";

           preparedStatement = connection.prepareStatement(createQueryCourse);
           preparedStatement.setString(1,course.getName());
           preparedStatement.setString(2, (course.getPrice() + "").replace(".", ","));
           preparedStatement.setTimestamp(3,Timestamp.valueOf(course.getDate_created()));
           preparedStatement.setLong(4, course.getCourseFormat().getId());

           preparedStatement.execute();
           close(preparedStatement);

           String readQueryCourse = "SELECT c.id AS course_id, c.name, c.price, c.date_created, " +
                   " f.id, f.course_format, f.is_online, f.lesson_duration, f.course_duration_weeks,f.date_created, f.lesson_per_week "+
                   "FROM tb_courses AS c "+
                   "JOIN tb_course_format AS f "+
                   "ON c.course_format_id = f.id "+
                   "ORDER BY c.id DESC LIMIT 1";

           preparedStatement = connection.prepareStatement(readQueryCourse);
           resultSet = preparedStatement.executeQuery();

           resultSet.next();

           CourseFormat courseFormat = new CourseFormat();
           courseFormat.setId(resultSet.getLong("id"));
           courseFormat.setFormat(resultSet.getString("course_format"));
           courseFormat.setIs_online(resultSet.getBoolean("is_online"));
           courseFormat.setLesson_duration(resultSet.getTime("lesson_duration").toLocalTime());
           courseFormat.setCourse_duration_weeks(resultSet.getInt("course_duration_weeks"));
           courseFormat.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
           courseFormat.setLesson_per_week(resultSet.getInt("lesson_per_week"));

           savedCourse = new Course();
           savedCourse.setId(resultSet.getLong("course_id"));
           savedCourse.setName(resultSet.getString("name"));
           //savedManager.setSalary(Double.valueOf(resultSet.getString("salary").replaceAll("[^\\d\\. ]+", "")));
           savedCourse.setPrice(Double.parseDouble(resultSet.getString("price").replaceAll("[^\\d\\. ]+", "")));
           savedCourse.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
           savedCourse.setCourseFormat(courseFormat);


       }catch (SQLException e){
           Error_LOG.info(this.getClass().getSimpleName() + "CourseImpl", Connection.class.getSimpleName(), e.getMessage());

           e.printStackTrace();
       }finally {
           close(resultSet);
           close(preparedStatement);
           close(connection);
       }

        return savedCourse;
    }



    @Override
    public Course findById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Course course = null;

        try {
            connection = getConnection();

            String readQueryFindByID = "SELECT c.id, c.name, c.price, c.date_created, f.*"+
                    "FROM tb_course AS c "+
                    "JOIN tb_course_format AS f "+
                    "ON c.course_format_id = f.id "+
                    "WHERE c.id = ?;";
            preparedStatement = connection.prepareStatement(readQueryFindByID);
            preparedStatement.setLong(1,id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            course = new Course();
            course.setId(resultSet.getLong("id"));
            course.setName(resultSet.getString("name"));
            course.setPrice(Double.parseDouble(resultSet.getString("price")));
            course.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());


        }catch (SQLException e){
            e.printStackTrace();
        }
        return course;
    }

    @Override
    public List<Course> findAll() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List <Course> courses = new ArrayList<>();
        List <CourseFormat> courseFormats = new ArrayList<>();

        try {
            Error_LOG.info(this.getClass().getSimpleName() + " findAll()", Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();

            String readQuery = "SELECT c.id AS course_id, c.name, c.price, c.date_created, f.* " +
                    "FROM tb_courses AS c " +
                    "JOIN tb_course_format AS f " +
                    "ON c.course_format_id = f.id ";


            preparedStatement = connection.prepareStatement(readQuery);

            resultSet = preparedStatement.executeQuery();

            for (int i = 0; i <= courses.size() && resultSet.next(); i++) {
                Course course = new Course();
                course.setId(resultSet.getLong("course_id"));
                course.setName(resultSet.getString("name"));
                course.setPrice(Double.parseDouble(resultSet.getString("price").replaceAll("[^\\d.]", "")));
                courses.add(course);
            }
            return courses;
        } catch (Exception e) {
            Error_LOG.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClassName(), e.getMessage());
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return courses;

    }

    @Override
    public List<Course> saveAll(List<Course> courses) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Course> savedCourse = new ArrayList<>();
        try {

            connection = getConnection();

            String insertQuery = "INSERT INTO tb_courses (" +
                    "name, price, date_created, course_format_id )" +
                    "VALUES (?, MONEY(?), ?, ?)";

            connection.setAutoCommit(false);

            for (int i = 0; i < courses.size(); i++) {
                Course course = new Course();
                preparedStatement = connection.prepareStatement(insertQuery);

                preparedStatement.setString(1, course.getName());
                preparedStatement.setString(2, (course.getPrice() + "").replace(".", ","));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(course.getDate_created()));
                preparedStatement.setLong(4, course.getCourseFormat().getId());


                preparedStatement.addBatch();


                if (i % 20 == 0 || i == courses.size() - 1) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            String readQueryCourse = "SELECT c.id AS course_id, c.name, c.price, c.date_created, f.* " +
                    "FROM tb_courses AS c " +
                    "JOIN tb_course_format AS f " +
                    "ON c.course_format_id = f.id " +
                    "ORDER BY c.id DESC LIMIT 1" + courses.size();
            close(preparedStatement);
            preparedStatement = connection.prepareStatement(readQueryCourse);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                CourseFormat courseFormat = new CourseFormat();
                courseFormat.setId(resultSet.getLong("course_id"));
                courseFormat.setFormat(resultSet.getString("course_format"));
                courseFormat.setIs_online(resultSet.getBoolean("is_online"));
                courseFormat.setLesson_duration(resultSet.getTime("lesson_duration").toLocalTime());
                courseFormat.setCourse_duration_weeks(resultSet.getInt("course_duration_weeks"));
                courseFormat.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
                courseFormat.setLesson_per_week(resultSet.getInt("lesson_per_week"));

                Course course = new Course();
                course.setId(resultSet.getLong("course_id"));
                course.setName(resultSet.getString("name"));
                //savedManager.setSalary(Double.valueOf(resultSet.getString("salary").replaceAll("[^\\d\\. ]+", "")));
                course.setPrice(Double.parseDouble(resultSet.getString("price").replaceAll("[^\\d\\. ]+", "")));
                course.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
                course.setCourseFormat(courseFormat);
                savedCourse.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(resultSet);
        close(preparedStatement);
        close(connection);

        return courses;
    }

}
