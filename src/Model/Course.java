package Model;

public final class Course extends BaseEntyty {

    private String name;
    private Double price;
    private CourseFormat courseFormat;


    public Course() {
        super();
    }

    public Course(Long id, String name, Double price, CourseFormat courseFormat) {
        super(id);
        this.name = name;
        this.price = price;
        this.courseFormat = courseFormat;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CourseFormat getCourseFormat() {
        return courseFormat;
    }

    public void setCourseFormat(CourseFormat courseFormat) {
        this.courseFormat = courseFormat;
    }




    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", date_created=" + date_created +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", courseFormat=" + courseFormat +
                '}';
    }
}
