package Model.Builder;

import Model.CourseFormat;

import java.time.LocalTime;

public class CourseFormatBuilder {

    private Long id;
    private String format;
    private int course_duration_weeks;
    private LocalTime lesson_duration;
    private int lesson_per_week;
    private boolean is_online;

    private CourseFormatBuilder(){}

    public static CourseFormatBuilder builder(){
        return new CourseFormatBuilder();
    }



    public CourseFormatBuilder format(String format) {
        this.format = format;
        return this;
    }
    public CourseFormat build(){
        return new CourseFormat(id,format,course_duration_weeks,lesson_duration,lesson_per_week,is_online);
    }
    public  CourseFormatBuilder id(Long id){
        this.id = id;
        return this;
    }

    public CourseFormatBuilder course_duration_weeks (int course_duration_weeks) {
        this.course_duration_weeks = course_duration_weeks;
        return  this;
    }

    public CourseFormatBuilder lesson_duration (LocalTime lesson_duration) {
        this.lesson_duration = lesson_duration;
        return this;
    }

    public CourseFormatBuilder lesson_per_week(int lesson_per_week) {
        this.lesson_per_week = lesson_per_week;
        return this;
    }

    public CourseFormatBuilder is_online(boolean is_online) {
        this.is_online = is_online;
        return this;
    }
}
