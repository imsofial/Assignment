import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;

class Course {
    static final int CAPACITY = 3;
    static int numberOfCourses = 0;
    static int courseId;
    String courseName;
    List<Student> enrolledStudents = new ArrayList<>();
    String courseLevel;
    public Course(String courseName0, String courseLevel0){
        numberOfCourses++;
        courseName = courseName0;
        courseLevel = courseLevel0;
        courseId = numberOfCourses;
    }
    public boolean isFull(){
        if (enrolledStudents.size()==CAPACITY) {
            return true;
        }
        return false;
    }
}
class Student extends UniversityMember implements Enrollable{
    int MAX_ENROLLMENT = 3;
    List<Course> enrolledCourses = new ArrayList<>();
    public Student(String memberName0) { //creating a new element of student class
        numberOfMemners++;
        memberId = numberOfMemners;
        memberName = memberName0;

    }
    @Override
    public boolean drop(Course c) {
        for (int i=0; i<enrolledCourses.size();i++){
            if (enrolledCourses.get(i)==c){
                enrolledCourses.remove(c);
                System.out.println("Dropped successfully");
                return true;
            }
        }
        System.out.println("Student is not enrolled in this course");
        return false;
    }

    @Override
    public boolean enroll(Course c) {
        if (enrolledCourses.size()==MAX_ENROLLMENT){
            System.out.println("Maximum enrollment is reached for the studen");
            return false;
        }
        if (c.isFull()){
            System.out.println("Course is full");
            return false;
        }
        for (int i=0;i<enrolledCourses.size();i++){
            if (enrolledCourses.get(i)==c){
                System.out.println("Student is already enrolled in this course");
                return false;
            }
        }
        enrolledCourses.add(c);
        System.out.println("Enrolled successfully");
        return false;
    }
}
class Professor extends UniversityMember{
    int MAX_LOAD = 2;
    List<Course> assignedCourses = new ArrayList<>();
    public Professor(String memberName0){
        numberOfMemners++;
        memberId = numberOfMemners;
        memberName = memberName0;
    }
    public boolean teach(Course c){
        //new course for him
        if (assignedCourses.size()==MAX_LOAD){
            System.out.println("Professor's load is complete");
            return false;
        }
        for (int i=0; i<assignedCourses.size();i++){
            if (assignedCourses.get(i) == c){
                System.out.println("Professor is already teaching this course");
                return false;
            }
        }
        assignedCourses.add(c);
        System.out.println("Professor is successfully assigned to teach this course");
        return true;
    }
    public boolean exempt(Course c){
        //exempt the professor from teaching a course
        for (int i=0; i< assignedCourses.size();i++){
            if (assignedCourses.get(i)==c){
                assignedCourses.remove(c);
                System.out.println("Professor is exempted");
                return true;
            }
        }
        System.out.println("Professor is not teaching this course");
        return false;
    }
}
abstract class UniversityMember{
    public static int numberOfMemners = 0;
    int memberId;
    String memberName;
}
interface Enrollable{
    public boolean drop(Course c);
    public boolean enroll(Course c);
}
enum CourseLevel{
    BACHELOR,
    MASTER
}
public class UniversityCourseManagmentSystem {
    static Scanner s = new Scanner(System.in);
    static List<Course> allCourses = new ArrayList<>();
    static List<UniversityMember> allMembers = new ArrayList<>();

    String testCourseLevel(CourseLevel courseLevel){
        switch (courseLevel){
            case MASTER:
                return "MASTER";
            case BACHELOR:
                return "BACHELOR";
        }
        return null;
    }
    void add_course(){
        String courseName = s.next();
        //check if normal name
        String courseLevel = s.next();
        allCourses.add(new Course(courseName, courseLevel));
        System.out.println("Added successfully");
    }
    void add_student(){
        String memberName = s.next();
        //check if normal name
        allMembers.add(new Student(memberName));
        System.out.println("Added successfully");
    }
    void add_professor(){
        String memberName = s.next();
        //check if normal
        allMembers.add(new Professor(memberName));
        System.out.println("Added successfully");
    }

    public static void main(String[] args) {
        void enroll_to_course(){
            int memberId = s.nextInt();
            int courseId = s.nextInt();

            allMembers.get(memberId).enroll(allCourses.get(courseId));

        }

        String coursename1 = "java_beginner";
        Course java_beginner = new Course("java_beginner","bachelor");
        //Course java_intermediate = new Course("java_intermediate","bachelor");
        Course python_basics = new Course("python_basics","bachelor");
        Course algorithms = new Course("algorithms","master");
        Course advanced_programming = new Course("advanced_programming","master");
        Course mathematical_analysis = new Course("mathematical_analysis","master");
        Course computer_vision = new Course("computer_vision","master");
        Student student_one = new Student("Alice");
        Student student_two = new Student("Bob");
        Student student_three = new Student("Alex");
        allCourses.add(java_beginner);
        allCourses.add(new Course("java_intermediate","bachelor"));
        System.out.println(allCourses.get(0).courseName);
        System.out.println(allCourses.get(1).courseName);

    }
    public static void fillinitialData(){

    }
}