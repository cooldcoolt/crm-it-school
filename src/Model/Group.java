package Model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Group extends BaseEntyty {
    private String name;
    private LocalTime group_time;
    private Course course;
    private Mentor mentor;

    public Group() {
        super();
    }

    public Group(Long id, String name, LocalTime group_time, Course course, Mentor mentor) {
        super(id);
        this.name = name;
        this.group_time = group_time;
        this.course = course;
        this.mentor = mentor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalTime getGroup_time() {
        return group_time;
    }
    public void setGroup_time(LocalTime group_time) {
        this.group_time = group_time;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", date_created=" + date_created +
                ", name='" + name + '\'' +
                ", group_time=" + group_time +
                ", course=" + course +
                ", mentor=" + mentor +
                '}';
        }

    }


