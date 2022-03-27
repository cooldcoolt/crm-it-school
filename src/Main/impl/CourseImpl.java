package Main.impl;

import Main.CourseDao;
import Main.DaoFactoryUtil.Error_LOG;
import Model.Course;
import Model.CourseFormat;

import java.sql.*;

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

           connection = getConnection();

           String createQueryCourse = "INSERT INTO tb_courses ("+
                   "name, price, date_created, course_format_id)"+
                   "VALUES (?, ?, ?, ?)";

           preparedStatement = connection.prepareStatement(createQueryCourse);
           preparedStatement.setString(1,course.getName());
           preparedStatement.setString(2, String.valueOf(course.getPrice()));
           preparedStatement.setTimestamp(3,Timestamp.valueOf(course.getDate_created()));
           preparedStatement.setLong(4,course.getId());

           preparedStatement.execute();
           close(preparedStatement);

           String readQueryCourse = "SELECT c.id, c.name, c.price, c.date_created, f.* "+
                   "FROM tb_course AS c "+
                   "JOIN tb_course_format AS f "+
                   "ON c.course_format_id = f.id "+
                   "ORDER BY c.id DESC LIMIT 1";

           preparedStatement = connection.prepareStatement(readQueryCourse);
           resultSet = preparedStatement.executeQuery();

           resultSet.next();

           CourseFormat courseFormat = new CourseFormat();
           courseFormat.setId(resultSet.getLong("f.id"));
           courseFormat.setFormat(resultSet.getString("course_format"));
           courseFormat.setIs_online(resultSet.getBoolean("is_online"));
           courseFormat.setLesson_duration(resultSet.getTime("lesson_duration").toLocalTime());
           courseFormat.setCourse_duration_weeks(resultSet.getInt("course_duration_weeks"));
           courseFormat.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
           courseFormat.setLesson_per_week(resultSet.getInt("lesson_per_week"));

           savedCourse = new Course();
           savedCourse.setId(resultSet.getLong("id"));
           savedCourse.setName(resultSet.getString("name"));
           savedCourse.setPrice(Double.parseDouble(resultSet.getString("price")));
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
            CourseFormat courseFormat = new CourseFormat();


        }catch (SQLException e){
            e.printStackTrace();
        }
        return course;
    }
}
