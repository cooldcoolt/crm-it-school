package Main.impl;

import Main.DaoFactoryUtil.Error_LOG;
import Main.ManagerDao;
import Model.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerImpl implements ManagerDao {
    public ManagerImpl() {
        Connection connection = null;
        Statement statement = null;

        try {
            System.out.println("Connecting to DATABASE: ");
            connection = getConnection();
            System.out.println("DATABASE OK: ");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_managers(" +
                    "id SERIAL NOT NULL, " +
                    "first_name VARCHAR(50) NOT NULL, " +
                    "last_name VARCHAR(50) NOT NULL, " +
                    "email VARCHAR(50) NOT NULL, " +
                    "phone_number CHAR(13) NOT NULL, " +
                    "salary MONEY NOT NULL, " +
                    "dob DATE NOT NULL, " +
                    "date_created TIMESTAMP NOT NULL DEFAULT NOW(), " +
                    "CONSTRAINT pk_manager_id PRIMARY KEY(id), " +
                    "CONSTRAINT pk_manager_first_name CHECK(LENGTH(first_name)>2));";

            System.out.println("Creating statement tb_manager: ");
            statement = connection.createStatement();
            System.out.println("OK");
            statement.execute(ddlQuery);


        } catch (SQLException e) {
            System.out.println("Ooops something went wrong");
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Manager save (Manager manager) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Manager savedManager = null;
        try {
            System.out.println("Connecting to DATABSE: ");
            connection = getConnection();
            System.out.println("OK");

            String createQuery = "INSERT INTO tb_managers(first_name, last_name,email, phone_number, salary, dob, date_created)" +
                    "VALUES(?,?, ?, ?, MONEY(?), ?, ?)";
            String readQuery = "SELECT * FROM tb_managers ORDER BY id DESC LIMIT 1";

            preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setString(1, manager.getFirst_name());
            preparedStatement.setString(2, manager.getLast_name());
            preparedStatement.setString(4, manager.getPhone_number());
            preparedStatement.setString(3, manager.getEmail());
            preparedStatement.setString(5, (manager.getSalary()  + "").replace(".", ","));
            preparedStatement.setDate(6, Date.valueOf(manager.getDob()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(manager.getDate_created()));

            preparedStatement.execute();
            close(preparedStatement);

            preparedStatement = connection.prepareStatement(readQuery);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            savedManager = new Manager();
            savedManager.setId(resultSet.getLong("id"));
            savedManager.setFirst_name(resultSet.getString("first_name"));
            savedManager.setLast_name(resultSet.getString("last_name"));
            savedManager.setEmail(resultSet.getString("email"));
            savedManager.setPhone_number(resultSet.getString("phone_number"));
            savedManager.setSalary(Double.valueOf(resultSet.getString("salary").replaceAll("[^\\d\\. ]+", "")));
            savedManager.setDob(resultSet.getDate("dob").toLocalDate());
            savedManager.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Osibka v save");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return savedManager;
    }

    @Override
    public Manager findById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Manager manager = null;

        try{
            connection = getConnection();
            String readQuery = "SELECT * FROM tb_managers WHERE id = ?";
            preparedStatement= connection.prepareStatement(readQuery);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            manager = new Manager();
            manager.setId(resultSet.getLong("id"));
            manager.setFirst_name(resultSet.getString("first_name"));
            manager.setLast_name(resultSet.getString("last_name"));
            manager.setEmail(resultSet.getString("email"));
            manager.setPhone_number(resultSet.getString("phone_number"));
            manager.setSalary(Double.valueOf(resultSet.getString("salary").replaceAll("[^\\d\\.]+", "")));
            manager.setDob(resultSet.getDate("dob").toLocalDate());
            manager.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
        }  catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return manager;
    }

    @Override
    public List<Manager> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Manager> managers = new ArrayList<>();
        try {
            Error_LOG.info(this.getClass().getSimpleName() + " findAll()", Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();

            String readQueryFindAll  = "SELECT * FROM tb_managers;";
            preparedStatement =connection.prepareStatement(readQueryFindAll);
            resultSet = preparedStatement.executeQuery();

            for (int i = 0; i <= managers.size() && resultSet.next(); i++) {
                Manager manager = new Manager();
                manager.setId(resultSet.getLong("id"));
                manager.setFirst_name(resultSet.getString("first_name"));
                manager.setLast_name(resultSet.getString("last_name"));
                manager.setEmail(resultSet.getString("email"));
                manager.setPhone_number(resultSet.getString("phone_number"));
                manager.setSalary(Double.parseDouble(resultSet.getString("salary")));
                manager.setDob(resultSet.getDate("dob").toLocalDate());
                manager.setDate_created(resultSet.getTimestamp("date_created").toLocalDateTime());
                managers.add(manager);

            }
            return managers;
        }catch (SQLException e ){
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
    public List<Manager> saveAll(List<Manager> managers) {
        return null;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return ManagerDao.super.getConnection();
    }
}




