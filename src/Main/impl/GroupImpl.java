package Main.impl;

import Main.CourseDao;
import Main.CourseFormatDao;
import Main.DaoFactoryUtil.Error_LOG;
import Main.GroupDao;
import Model.Course;
import Model.CourseFormat;
import Model.Group;
import Model.Mentor;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class GroupImpl implements GroupDao {

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Group savedGroup = null;

    public GroupImpl(){

        try{
            Error_LOG.info(this.getClass().getSimpleName() + "GroupImpl() ", Connection.class.getSimpleName(),"esteblishing connection: ");
            connection = getConnection();
            String ddlQueryGroupImpl = "CREATE TABLE IF NOT EXISTS tb_groups ("+
                    "id SERIAL, "+
                    "name VARCHAR(50) NOT NULL, "+
                    "group_time TIMESTAMP NOT NULL, "+
                    "date_created TIMESTAMP NOT NULL DEFAULT NOW(), "+
                    "course_id INT NOT NULL, "+
                    "mentor_id INT NOT NULL,"+
                    ""+
                    "CONSTRAINT pk_group_id PRIMARY KEY(id), "+
                    "CONSTRAINT fk_course_id FOREIGN KEY(course_id) REFERENCES tb_course (id), "+
                    "CONSTRAINT fk_metor_id FOREIGN KEY (mentor_id) REFERENCES tb_mentor (id)"+
                    ");";
            Error_LOG.info(this.getClass().getSimpleName() + "GroupImpl() ", Connection.class.getSimpleName(),"Creating tb_groups: ");
            statement=connection.createStatement();
            statement.execute(ddlQueryGroupImpl);

        } catch (SQLException e){
            Error_LOG.info(this.getClass().getSimpleName() + "GroupImpl() ", Connection.class.getSimpleName(), e.getMessage());
            e.printStackTrace();
        }finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Group save(Group group) {

        try{
            Error_LOG.info(this.getClass().getSimpleName() + "GroupImpl() ", Connection.class.getSimpleName(),"esteblishing connection: ");
            connection = getConnection();

            String createQueryGroupImpl = "INSERT INTO tb_groups("+
                    "name, group_time, date_created, mentor_id, course_id) "+
                    "VALUES(?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(createQueryGroupImpl);
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, String.valueOf(group.getGroup_time()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(group.getDate_created()));
            preparedStatement.setLong(4,group.getMentor().getId());
            preparedStatement.setLong(5, group.getCourse().getId());

            preparedStatement.execute();
            close(preparedStatement);

            String subQueryGroutImpl = "SELECT c.id AS course_id, c.name, c.price, c.date_created, "+
                    "f.id AS format_id, f.course_format, f.course_duration_weeks, f.lesson_duration, "+
                    "f.lessons_per_week, f.is_online, f.date_created AS format_date_created "+
                    "FROM tb_course AS c "+
                    "ON c.course_format_id = f.id";

            String readQueryGroupImpl = "SELECT g.id AS group_id, g.group_time, "+
                    "g.date_created AS group_date_created, g.course_id, g.mentor_id "+
                    "FROM tb_groups AS g "+
                    "JOIN ("+subQueryGroutImpl+"WHERE course_id= g.course_id) AS c "+
                    "ON g.course_id = c.id "+
                    "JOIN tb_mentor AS m "+
                    "ON g.mentor_id = m.id "+
                    "ORDER BY g.id DESC LIMIT 1";
            preparedStatement = connection.prepareStatement(readQueryGroupImpl);
            resultSet=preparedStatement.executeQuery();
            resultSet.next();

            savedGroup = new Group();
            savedGroup.setId(resultSet.getLong("id"));
            savedGroup.setName(resultSet.getString("name"));
            savedGroup.setGroup_time(LocalTime.parse(resultSet.getString("group_time")));
            savedGroup.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());

        }catch (SQLException e) {
            Error_LOG.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(),"metot SAVE: ");
            e.printStackTrace();
            Error_LOG.error(this.getClass().getSimpleName(), Connection.class.getSimpleName(), e.getMessage());
        } finally {
            close(preparedStatement);
            close(statement);
            close(resultSet);
            close(connection);
        }
        return savedGroup;
    }

    @Override
    public Group findById(Long id) {

        Group group = null;

       try{
           connection = getConnection();
           
           String  readQueryFindByIdGroupImpl = "SELECT * FROM tb_groups WHERE id = ? ";

           preparedStatement= connection.prepareStatement(readQueryFindByIdGroupImpl);
           preparedStatement.setLong(1,id);

           resultSet = preparedStatement.executeQuery();
           resultSet.next();

           group = new Group();
           group.setId(resultSet.getLong("id"));
           group.setName(resultSet.getString("name"));
           group.setGroup_time(LocalTime.parse(resultSet.getString("group_time")));
           group.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());



       }catch (SQLException e){
           Error_LOG.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(),"metod GroupImpl FindByiD: ");
           e.printStackTrace();

       }

        return group;
    }

    @Override
    public List<Group> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Group> groups = null;
        try{
            connection = getConnection();

           String ddlQueryFindByAll  =
                    "SELECT  g.id AS group_id, g.name AS group_name, g.group_time, " +
                            "    g.date_created AS group_dc, " +
                            "        g.course_id, c.name AS course_name, c.price, course_dc, " +
                            "    format_id, course_format, course_duration_weeks, lesson_duration, " +
                            "    lessons_per_week, is_online, format_dc, " +
                            "        g.mentor_id, m.first_name, m.last_name, m.email, m.phone_number, m.salary, m.dob, m.date_created AS mentor_dc " +
                            "    FROM tb_groups AS g " +
                            "" +
                            "    JOIN (SELECT c.id AS course_id, c.name, c.price, c.date_created AS course_dc, " +
                            "    f.id AS format_id, f.course_format, f.course_duration_weeks, f.lesson_duration, " +
                            "    f.lessons_per_week, f.is_online, f.date_created AS format_dc " +
                            "    FROM tb_courses AS c " +
                            "    JOIN tb_course_format AS f " +
                            "    ON c.course_format_id = f.id) AS c " +
                            "    ON g.course_id = c.course_id " +
                            "" +
                            "    JOIN tb_mentors AS m " +
                            "    ON g.mentor_id = m.id; " ;

           preparedStatement = connection.prepareStatement(ddlQueryFindByAll);
           resultSet = preparedStatement.executeQuery();
           groups = new ArrayList<>();

           while(resultSet.next()){
               CourseFormat courseFormat = new CourseFormat();
               courseFormat.setId(resultSet.getLong("format_id"));
               courseFormat.setFormat(resultSet.getString("course_format"));
               courseFormat.setCourse_duration_weeks(resultSet.getInt("course_duration_weeks"));
               courseFormat.setLesson_duration(resultSet.getTime("lesson_duration").toLocalTime());
               courseFormat.setLesson_per_week(resultSet.getInt("lessons_per_week"));
               courseFormat.setIs_online(resultSet.getBoolean("is_online"));
               courseFormat.setDate_created(resultSet.getTimestamp("format_dc").toLocalDateTime());

               Course course = new Course();
               course.setId(resultSet.getLong("course_id"));
               course.setName(resultSet.getString("course_name"));
               course.setPrice(Double.parseDouble(resultSet.getString("price").replaceAll("[^\\d\\.]+", "")) / 100);
               course.setCourseFormat(courseFormat);
               course.setDate_created(resultSet.getTimestamp("course_dc").toLocalDateTime());

               Mentor mentor = new Mentor();
               mentor.setId(resultSet.getLong("mentor_id"));
               mentor.setFirst_name(resultSet.getString("first_name"));
               mentor.setLast_name(resultSet.getString("last_name"));
               mentor.setEmail(resultSet.getString("email"));
               mentor.setPhone_number(resultSet.getString("phone_number"));
               mentor.setSalary(Double.parseDouble(resultSet.getString("salary").replaceAll("[^\\d\\.]", "")) / 100);
               mentor.setDob(resultSet.getDate("dob").toLocalDate());
               mentor.setDate_created(resultSet.getTimestamp("mentor_dc").toLocalDateTime());

               Group group = new Group();
               group.setId(resultSet.getLong("group_id"));
               group.setName(resultSet.getString("group_name"));
               group.setGroup_time(LocalTime.parse(resultSet.getString("group_time")));
               group.setDate_created(resultSet.getTimestamp("group_dc").toLocalDateTime());
               group.setCourse(course);
               group.setMentor(mentor);
               groups.add(group);
           }
        }catch (Exception e){
            Error_LOG.error(this.getClass().getSimpleName(), Connection.class.getSimpleName(), e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Group> saveAll(List<Group> groups) {
        return null;
    }


}
