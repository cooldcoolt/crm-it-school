package Main.DaoFactoryUtil;

import java.time.LocalDateTime;

public class Error_LOG {

    public static void  info(String className, String targetName, String message) {
        System.out.printf("%s [INFO] ------ %s ------- %s ------%s", LocalDateTime.now(), className, targetName, message);
    }

    public  static void  error (String className, String targetName, String message) {
        System.out.printf("%s [ERROR] ------ %s ------- %s ------%s", LocalDateTime.now(), className, targetName, message);
    }

}
