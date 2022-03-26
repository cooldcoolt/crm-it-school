package Main.impl;

import Main.MentorDao;
import Model.Mentor;
import Model.Student;

import java.sql.*;

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
        Mentor savedMentor =null;

        try{
            connection = getConnection();

            String createDdlQuery = "INSERT INTO tb_mentor (fist_name, last_name, email, phone_number, dob, salary, date_created)"+
                                    "VALUES (?, ?,  ?, ?, ?, MONEY(?), ?)";

            preparedStatement = connection.prepareStatement(createDdlQuery);
            preparedStatement.setString(1, mentor.getFirst_name());
            preparedStatement.setString(2, mentor.getLast_name());
            preparedStatement.setString(3, mentor.getEmail());
            preparedStatement.setString(4, mentor.getPhone_number());
            preparedStatement.setDate(5, Date.valueOf(mentor.getDob()));
            preparedStatement.setString(6, (mentor.getSalary()  + "").replace(".", ","));

            preparedStatement.executeQuery();
            close(preparedStatement);

            savedMentor = new Mentor();

            savedMentor.setId(resultSet.getLong("id"));
            savedMentor.setFirst_name(resultSet.getString("first_name"));


            savedMentor.setLast_name(resultSet.getString("last_name"));
            savedMentor.setEmail(resultSet.getString("email"));
            savedMentor.setPhone_number(resultSet.getString("phone_number"));
            savedMentor.setDob(resultSet.getDate("dob").toLocalDate());
            savedMentor.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime())

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(preparedStatement);
            close(connection);
            close(resultSet);
        }


    @Override
    public Mentor findById(Long id) {
        return null;
    }
}
