package Main.impl;

import Main.DaoFactoryUtil.Error_LOG;
import Main.StudentDao;
import Model.Manager;
import Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.postgresql.util.JdbcBlackHole.close;

public class StudentImpl implements StudentDao {

    public StudentImpl() {
        Connection connection = null;
        Statement statement = null;

        try {
            System.out.println("Connecting to DATABASE: ");
            connection = getConnection();
            System.out.println("DATABASE OK: ");

            String ddlQuery_Student = "CREATE TABLE IF NOT EXISTS tb_student (" +
                    "id SERIAL NOT NULL PRIMARY KEY, " +
                    "first_name VARCHAR(50) NOT NULL CHECK(LENGTH(first_name)>2), " +
                    "last_name VARCHAR (50), " +
                    "email VARCHAR(50), " +
                    "phone_number VARCHAR(50), " +
                    "dob DATE NOT NULL, " +
                    "date_created TIMESTAMP NOT NULL DEFAULT NOW());";

            System.out.println("Creating statement tb_student");
            statement = connection.createStatement();
            System.out.println("OK");
            statement.execute(ddlQuery_Student);

        } catch (SQLException e) {
            System.out.println("Oshibka v ddlQuery_Student");
            e.printStackTrace();
        } finally {
            close(statement);
            close(connection);
        }
    }


    @Override
    public Student save(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetStudent = null;
        Student savedStudent = null;
        try {
            System.out.println("Connecting to DATABSE: ");
            connection = getConnection();
            System.out.println("OK");

            String createDdlQuery_Student = "INSERT INTO tb_student(first_name, last_name, email, phone_number, dob, date_created )" +
                    "VALUES(?, ?, ?, ?, ?, ?)";



            preparedStatement = connection.prepareStatement(createDdlQuery_Student);
            preparedStatement.setString(1, student.getFirst_name());
            preparedStatement.setString(2, student.getLast_name());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getPhone_number());
            preparedStatement.setDate(5, student.getDob());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(student.getDate_created()));

            preparedStatement.executeQuery();
            close(preparedStatement);

            String readDdlQuery_Student = "SELECT * FROM tb_student ORDER BY id DESC LIMIT 1";
            preparedStatement = connection.prepareStatement(readDdlQuery_Student);
            resultSetStudent = preparedStatement.executeQuery();
            resultSetStudent.next();

            savedStudent = new Student();
            savedStudent.setId(resultSetStudent.getLong("id"));
            savedStudent.setFirst_name(resultSetStudent.getString("first_name"));
            savedStudent.setLast_name(resultSetStudent.getString("last_name"));
            savedStudent.setEmail(resultSetStudent.getString("email"));
            savedStudent.setPhone_number(resultSetStudent.getString("phone_number"));
            savedStudent.setDob(resultSetStudent.getDate("dob"));
            savedStudent.setDate_created(resultSetStudent.getTimestamp("date_created").toLocalDateTime());

        } catch (SQLException e) {
            System.out.println("Oshibka v metode save ili......");
            e.printStackTrace();
        } finally {
            close(resultSetStudent);
            close(preparedStatement);
            close(connection);
        }
        return savedStudent;
    }

    @Override
    public Student findById(Long id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Student student = null;

        try {
            connection = getConnection();
            String readQuery_student = "SELECT * FROM tb_student WHERE id = ?";
            preparedStatement = connection.prepareStatement(readQuery_student);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setFirst_name(resultSet.getString("first_name"));
            student.setLast_name(resultSet.getString("last_name"));
            student.setEmail(resultSet.getString("email"));
            student.setPhone_number(resultSet.getString("phone_number"));
            student.setDob(Date.valueOf(resultSet.getDate("dob").toLocalDate()));
            student.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Student> students = new ArrayList<>();

        try {
            Error_LOG.info(this.getClass().getSimpleName() + " findAll()", Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();

            String readQueryStudent = "SELECT * FROM tb_student;";

            preparedStatement = connection.prepareStatement(readQueryStudent);

            resultSet = preparedStatement.executeQuery();

            for (int i = 0; i <= students.size() && resultSet.next(); i++) {

                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setFirst_name(resultSet.getString("first_name"));
                student.setLast_name(resultSet.getString("last_name"));
                student.setEmail(resultSet.getString("email"));
                student.setPhone_number(resultSet.getString("phone_number"));

                student.setDob(Date.valueOf(resultSet.getDate("dob").toLocalDate()));
                student.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
                students.add(student);
            }
            return students;
        } catch (Exception e) {
            Error_LOG.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClassName(), e.getMessage());
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return null;
    }

    @Override
    public List<Student> saveAll(List<Student> students) {
        return null;
    }
}



