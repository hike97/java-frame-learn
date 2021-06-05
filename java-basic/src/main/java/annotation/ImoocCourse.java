package annotation;


@CourseInfoAnnotation (courseName = "剑指java面试", courseTag = "面试", courseIndex = 12)
public class ImoocCourse {

    @PersonInfoAnnotation (name = "air", gender = "nan", language = {"12"})
    private String author;

    @CourseInfoAnnotation (courseName = "校园商铺", courseTag = "实战", courseIndex = 144, courseProfile = "毕业设计")
    public void getCourseInfo () {

    }

}
