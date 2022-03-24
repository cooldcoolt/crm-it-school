package Model;

import java.time.LocalTime;

public final class CourseFormat extends BaseEntyty {
    private String format;
    private int course_duration_weeks;
    private LocalTime lesson_duration;
    private int lesson_per_week;
    private boolean is_online;

    public CourseFormat() {
        super();
    }

    public CourseFormat(Long id, String format, int course_duration_weeks, LocalTime lesson_duration, int lesson_per_week, boolean is_online) {
        super(id);
        this.format = format;
        this.course_duration_weeks = course_duration_weeks;
        this.lesson_duration = lesson_duration;
        this.lesson_per_week = lesson_per_week;
        this.is_online = is_online;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getCourse_duration_weeks() {
        return course_duration_weeks;
    }

    public void setCourse_duration_weeks(int course_duration_weeks) {
        this.course_duration_weeks = course_duration_weeks;
    }

    public LocalTime getLesson_duration() {
        return lesson_duration;
    }

    public void setLesson_duration(LocalTime lesson_duration) {
        this.lesson_duration = lesson_duration;
    }

    public int getLesson_per_week() {
        return lesson_per_week;
    }

    public void setLesson_per_week(int lesson_per_week) {
        this.lesson_per_week = lesson_per_week;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    @Override
    public String toString() {
        return "CourseFormat{" +
                "id=" + id +
                ", date_created=" + date_created +
                ", format='" + format + '\'' +
                ", course_duration_weeks=" + course_duration_weeks +
                ", lesson_duration=" + lesson_duration +
                ", lesson_per_week=" + lesson_per_week +
                ", is_online=" + is_online +
                '}';
    }
}