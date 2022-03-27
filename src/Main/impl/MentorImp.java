package Main.impl;

import Main.DaoFactoryUtil.Error_LOG;
import Main.MentorDao;
//import Model.Mentors;
import Model.Mentor;
import Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorImp implements MentorDao {
    public MentorImp() {

        Connection connection = null;
        Statement statement = null;

        try {
            System.out.println("Connecting to DATA BASE: ");
            connection = getConnection();
            System.out.println("OK");

            String dllQuery_Mentor = "CREATE TABLE IF NOT EXISTS tb_mentor(" +
                    "id SERIAL NOT NULL, " +
                    "first_name VARCHAR(50) NOT NULL, " +
                    "last_name VARCHAR(50) NOT NULL, " +
                    "email VARCHAR(50) NOT NULL, " +
                    "phone_number CHAR(13), " +
                    "dob DATE," +
                    "salary MONEY, " +
                    "date_created TIMESTAMP DEFAULT NOW());";

            System.out.println("Creating table tb_mentor:");
            statement = connection.createStatement();
            System.out.println("OK");
            statement.execute(dllQuery_Mentor);

        } catch (SQLException e) {
            System.out.println("OShibka dllQuery: ");
            e.printStackTrace();
        } finally {
            close(statement);
            close(connection);
        }

    }

    @Override
    public Mentor save(Mentor mentor) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Mentor savedMentor = null;

        try {
            connection = getConnection();

            String createDdlQuery = "INSERT INTO tb_mentor (first_name, last_name, email, phone_number, dob, salary, date_created)" +
                    "VALUES (?, ?,  ?, ?, ?, MONEY(?), ?)";

            preparedStatement = connection.prepareStatement(createDdlQuery);
            preparedStatement.setString(1, mentor.getFirst_name());
            preparedStatement.setString(2, mentor.getLast_name());
            preparedStatement.setString(3, mentor.getEmail());
            preparedStatement.setString(4, mentor.getPhone_number());
            preparedStatement.setDate(5, Date.valueOf(mentor.getDob()));
            preparedStatement.setString(6, (mentor.getSalary() + "").replace(".", ","));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(mentor.getDate_created()));

            System.out.println("createDdlQuery: running");
            preparedStatement.execute();
            System.out.println("createDdlQuery: - OK ");
            close(preparedStatement);


            String readDdlQuery_Mentor = "SELECT * FROM tb_mentor ORDER BY id DESC LIMIT 1";
            preparedStatement = connection.prepareStatement(readDdlQuery_Mentor);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            savedMentor = new Mentor();

            savedMentor.setId(resultSet.getLong("id"));
            savedMentor.setFirst_name(resultSet.getString("first_name"));


            savedMentor.setLast_name(resultSet.getString("last_name"));
            savedMentor.setEmail(resultSet.getString("email"));
            savedMentor.setPhone_number(resultSet.getString("phone_number"));
            savedMentor.setDob(resultSet.getDate("dob").toLocalDate());
            savedMentor.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(connection);
            close(resultSet);
        }
        return savedMentor;
    }

    @Override
    public Mentor findById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Mentor mentor = null;
        try {
            connection = getConnection();
            String readQuery = "SELECT * FROM tb_managers WHERE id = ?";
            preparedStatement = connection.prepareStatement(readQuery);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();


            mentor = new Mentor();
            mentor.setId(resultSet.getLong("id"));
            mentor.setFirst_name(resultSet.getString("first_name"));
            mentor.setLast_name(resultSet.getString("last_name"));
            mentor.setEmail(resultSet.getString("email"));
            mentor.setPhone_number(resultSet.getString("phone_number"));
            mentor.setSalary(Double.valueOf(resultSet.getString("salary").replaceAll("[^\\d\\.]+", "")));
            mentor.setDob(resultSet.getDate("dob").toLocalDate());
            mentor.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return mentor;
    }

    @Override
    public List<Mentor> findAll() {

        Connection connection =null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet =null;

        List<Mentor> mentors = new ArrayList<>();
       try{
           Error_LOG.info(this.getClass().getSimpleName() + " findAll()", Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();
            String readQueryMentor = "SELECT * FROM tb_mentors;";
            preparedStatement = connection.prepareStatement(readQueryMentor);
            resultSet = preparedStatement.executeQuery();

           for (int i = 0; i <= mentors.size() && resultSet.next(); i++) {

               Mentor mentor = new Mentor();

               mentor.setId(resultSet.getLong("id"));
               mentor.setFirst_name(resultSet.getString("first_name"));
               mentor.setLast_name(resultSet.getString("last_name"));
               mentor.setEmail(resultSet.getString("email"));
               mentor.setPhone_number(resultSet.getString("phone_number"));
               mentor.setSalary(Double.valueOf(resultSet.getString("salary").replaceAll("[^\\d\\.]", "")));
               mentor.setDob(resultSet.getDate("dob").toLocalDate());
               mentor.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
               mentors.add(mentor);


           }
           return mentors;

       }catch (SQLException e){
           Error_LOG.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClassName(), e.getMessage());
           e.printStackTrace();
       }
        return null;
    }
}