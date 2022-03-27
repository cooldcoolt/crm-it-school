package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import Main.DaoFactoryUtil.Error_LOG;

public interface CrudDao<Model> {
    Model save(Model model);

    Model findById(Long id);

    List<Model> findAll();

    default Connection getConnection() throws SQLException {
        final String URL = "jdbc:postgresql://localhost:5432/crm";
        final String USERNAME = "postgres";
        final String PASSWORD = "Dana4815162342";
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);

    }

    default void close(AutoCloseable closeable) {

        try {
            Error_LOG.info(getClass().getSimpleName(),closeable.getClass().getSimpleName(),"Closing: ");
            System.out.println(getClass().getSimpleName());
            closeable.close();
            System.out.println((getClass().getSimpleName()));
        } catch (Exception e) {
            System.out.println("Can not close " + closeable.getClass().getSimpleName());
            e.printStackTrace();
        }
    }
}