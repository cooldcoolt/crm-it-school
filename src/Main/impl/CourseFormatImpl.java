package Main.impl;

import Main.CourseFormatDao;
import Main.DaoFactoryUtil.Error_LOG;
import Model.CourseFormat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class CourseFormatImpl implements CourseFormatDao {

    public CourseFormatImpl(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            Error_LOG.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();
            Error_LOG.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), "Connection established");

            String ddlQueryCourseFormatImpl = "CREATE TABLE IF NOT EXISTS tb_course_format ("+
                    "id SERIAL, "+
                    "course_format VARCHAR(50) NOT NULL, "+
                    "course_duration_weeks INT NOT NULL,"+
                    "lesson_duration TIME NOT NULL, "+
                    "lesson_per_week INT NOT NULL, "+
                    "is_online BOOLEAN NOT NULL, "+
                    "date_created TIMESTAMP NOT NULL DEFAULT NOW(), "+
                    ""+
                    "CONSTRAINT pk_course_format_id PRIMARY KEY(id), "+
                    "CONSTRAINT course_duration_weeks_NeMojetBytNolIliMensheNOlya CHECK (course_duration_weeks > 0),"+
                    "CONSTRAINT lesson_per_week_NeMojetBytNolIliMensheNOlya CHECK (lesson_per_week > 0),"+
                    ");";

            Error_LOG.info(this.getClass().getSimpleName(), PreparedStatement.class.getSimpleName(), "Creating tb_course_format");
            preparedStatement = connection.prepareStatement(ddlQueryCourseFormatImpl);
            preparedStatement.execute();


        }catch (SQLException e){
            Error_LOG.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        }finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public CourseFormat save(CourseFormat courseFormat) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CourseFormat savedCourseFormat = null;

        try{
            Error_LOG.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), " connecting to database...");
            connection= getConnection();

            String createQueryCourseFormatSave ="INSERT INTO tb_course_format ("+
                    "course_format, course_duration_weeks, lesson_duration, lesson_per_week, is_online, date_created )"+
                    "VALUES(?, ?, ?, ?, ?, ?)";

            preparedStatement.execute();
            close(preparedStatement);

            String readQueryCourseFormatSave = "SELECT * FROM tb_course_format ORDER BY id DESC LIMIT 1";
            preparedStatement = connection.prepareStatement(readQueryCourseFormatSave);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            savedCourseFormat = new CourseFormat();

            savedCourseFormat = new CourseFormat();

            savedCourseFormat.setId(resultSet.getLong("id"));
            savedCourseFormat.setFormat(resultSet.getString("course_format"));
            savedCourseFormat.setCourse_duration_weeks(resultSet.getInt("course_duration_weeks"));
            savedCourseFormat.setLesson_duration(LocalTime.parse(resultSet.getString("lesson_duration")));
            savedCourseFormat.setLesson_per_week(resultSet.getInt("lesson_per_week"));
            savedCourseFormat.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());


        }catch (SQLException e){
            Error_LOG.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        }finally {
            close(preparedStatement);
            close(resultSet);
            close(connection);
        }
        return savedCourseFormat;
    }

    @Override
    public CourseFormat findById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CourseFormat courseFormat = null;

        try{
            Error_LOG.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), " CourseFormat by ID: Something went wrong...");
            connection= getConnection();

            String readQueryFindByIdCourseFormat = "SELECT * FROM tb_course_format WHERE id=?";
            preparedStatement = connection.prepareStatement(readQueryFindByIdCourseFormat);
            System.out.println("Podozritelno: ");
            preparedStatement.setLong(1, Long.parseLong("id"));
            System.out.println("OK");


            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            courseFormat = new CourseFormat();

            courseFormat.setId(resultSet.getLong("id"));
            courseFormat.setFormat(resultSet.getString("course_format"));
            courseFormat.setCourse_duration_weeks(resultSet.getInt("course_duration_weeks"));
            courseFormat.setLesson_duration(LocalTime.parse(resultSet.getString("lesson_duration")));
            courseFormat.setLesson_per_week(resultSet.getInt("lesson_per_week"));
            courseFormat.setIs_online(resultSet.getBoolean("is_online"));
            courseFormat.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());

        }catch (SQLException e) {
            Error_LOG.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        }
        return courseFormat;
    }
}
