package Main.DaoFactoryUtil;

import Main.*;
import Main.impl.*;
import Model.Group;

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
    private static GroupDao groupDao;
    private static CourseFormatDao courseFormatDao;
    private static CourseDao courseDao;

    public static CrudDao<?> autowired (String qualifier, String scope ) {
        if (!scope.equals("singleton") && !scope.equals("prototype")) {
            throw new RuntimeException("Invalid scope of bean: " + scope);

        }
        switch (qualifier) {
            case "ManagerDao":
                return getManagerDaoSql(scope);

            case "StudentDao":
                return getStudentDaoSql(scope);

            case "MentorDao":
                return getMentorDaoSql(scope);
            case "GroupDao":
                return getGroupDaoSql(scope);
            case "CourseFormatDao":
                return getCourseFormatDaoSql(scope);
            case "CourseDao":
                return  getCourseDaoSql(scope);

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
    public static GroupDao getGroupDaoSql(String scope){
        if(scope.equals("prototype")){
            return new GroupImpl();
        }
        if(groupDao ==null){
            groupDao = new GroupImpl();

        }
        return groupDao;
    }
    public static CourseFormatDao getCourseFormatDaoSql(String scope){
        if(scope.equals("prototype")){
            return new CourseFormatImpl();
        }
        if(courseFormatDao == null){
            courseFormatDao = new CourseFormatImpl();
        }
        return courseFormatDao;
    }
    public static CourseDao getCourseDaoSql(String scope){
        if(scope.equals("prototype")) {
            return new CourseImpl();
        }
        if(courseDao == null){
            courseDao = new CourseImpl();
        }
        return  courseDao;
    }

}


