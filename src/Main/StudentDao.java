package Main;

import Main.DaoFactoryUtil.DaoFactory;
import Model.Student;

import javax.management.modelmbean.ModelMBean;

public interface   StudentDao extends CrudDao<Student> {
}
