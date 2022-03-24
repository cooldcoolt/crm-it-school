package Main.DaoFactoryUtil;

import Main.CrudDao;
import Main.ManagerDao;
import Main.MentorDao;
import Main.StudentDao;
import Main.impl.ManagerImpl;
import Main.impl.MentorImp;
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
    private static ManagerDao managerDao;
    private static StudentDao studentDao;
    private static MentorDao mentorDao;

    public static CrudDao<?> autowired (String qualifier, String scope ) {
        if (!scope.equals("singleton") && !scope.equals("prototype")) {
            throw new RuntimeException("Invalid scope of bean: " + scope);

        }
        switch (qualifier) {
            case "ManagerDao":
                return getManagerDaoSql(scope);

            case "StudentDao":
                return getStudentDaoSql(scope);
            default:
                throw new RuntimeException("Can not find bean for autowiring: " + qualifier);
        }
    }

    private static ManagerDao getManagerDaoSql(String scope) {
      if (scope.equals("prototype")){
          return new ManagerImpl();
      }
      if (managerDao == null) {
          managerDao = new ManagerImpl();
        }
        return managerDao;
    }


    private static StudentDao getStudentDaoSql(String scope) {
      if (scope.equals("prototype")) {
          return new StudentImpl();
      }
      if (studentDao == null){
          studentDao = new StudentImpl();
      }
      return studentDao;
    }

    public static MentorDao getMentorDaoSql(String scope) {
        if (scope.equals("prototype")){
            return new MentorImp();
        }
        if (mentorDao == null){
            mentorDao = new MentorImp();
        }
        return mentorDao;
    }
}


