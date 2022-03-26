package Main.DaoFactoryUtil;

import Main.ManagerDao;
import Main.StudentDao;
import Main.impl.ManagerImpl;
import Main.impl.StudentImpl;

public abstract class DaoFactory {

    static {
        try {
            System.out.println("Loading driver...");
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver loading failed");
            e.printStackTrace();
        }
    }

    public static ManagerDao getManagerDaoSql() {
        return new ManagerImpl();
    }
    public static StudentDao getStudentDaoSql() {
        return  new StudentImpl();
    }
    }


