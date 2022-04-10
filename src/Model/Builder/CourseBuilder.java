package Model.Builder;

import Model.Course;
import Model.CourseFormat;

public class CourseBuilder {

    private Long id;
    private String name;
    private Double price;
    private CourseFormat courseFormat;

    private CourseBuilder() {
    }

    public static CourseBuilder builder() {
        return new CourseBuilder();
    }
    public CourseBuilder id(Long id){
        this.id = id;
        return this;
    }

    public Course build (){
        return new Course(id,name, price, courseFormat);
    }

    public CourseBuilder name(String name) {
        this.name = name;
        return this;
    }

   public CourseBuilder price(Double price) {
        this.price = price;
        return this;

    }

    public CourseBuilder courseFormat(CourseFormat courseFormat) {
        this.courseFormat = courseFormat;
        return this;
    }
}
