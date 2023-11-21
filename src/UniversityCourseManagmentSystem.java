import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        return enrolledStudents.size() == CAPACITY;
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
    boolean drop(Course c);
    boolean enroll(Course c);
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
    boolean add_course(){
        String courseName = s.next();
        for (int i=0;i<courseName.length();i++){ //check if name consist of english letters
            char c = courseName.charAt(i);
            if (!((c>='A' && c<='Z') || (c>='a'&&c<='z'))){
                if (!(i==0 || i==courseName.length()-1) || !(c == '_')) {
                    System.out.println("Wrong inputs");
                    return false;
                }
            }
        }
        String courseLevel = s.next();
        courseLevel = testCourseLevel(CourseLevel.valueOf(courseLevel));
        if (courseLevel==null){
            System.out.println("Wrong inputs");
            return false;
        }
        allCourses.add(new Course(courseName, courseLevel));
        System.out.println("Added successfully");
        return true;
    }
    boolean add_student(){
        String memberName = s.next();
        for (int i=0;i<memberName.length();i++){ //check if name consist of english letters
            char c = memberName.charAt(i);
            if (!((c>='A' && c<='Z') || (c>='a'&&c<='z'))){
                System.out.println("Wrong inputs");
                return false;
            }
        }
        allMembers.add(new Student(memberName));
        System.out.println("Added successfully");
        return true;
    }
    boolean add_professor(){
        String memberName = s.next();
        for (int i=0;i<memberName.length();i++){ //check if name consist of english letters
            char c = memberName.charAt(i);
            if (!((c>='A' && c<='Z') || (c>='a'&&c<='z'))){
                System.out.println("Wrong inputs");
                return false;
            }
        }
        if (memberName == "professor"){
            System.out.println("Wrong inputs");
            return false;
        }
        allMembers.add(new Professor(memberName));
        System.out.println("Added successfully");
        return true;
    }
    static void enroll_to_course(){
        int memberId = s.nextInt();
        int courseId = s.nextInt();
        Student st = (Student) allMembers.get(memberId);
        st.enroll(allCourses.get(courseId));
        System.out.println("Enrolled successfully");
    }
    void drop_from_course(){
        int memberId = s.nextInt();
        int courseId = s.nextInt();
        Student st = (Student) allMembers.get(memberId);
        st.drop(allCourses.get(courseId));
        System.out.println("Dropped successfully");

    }
    void assign_to_course(){
        int memberId = s.nextInt();
        int courseId = s.nextInt();
        Professor prof = (Professor) allMembers.get(memberId);
        prof.teach(allCourses.get(courseId));
        System.out.println("Professor is successfully assigned to teach this course\n");
    }
    void exempt_from_course(){
        int memberId = s.nextInt();
        int courseId = s.nextInt();
        Professor prof = (Professor) allMembers.get(memberId);
        prof.exempt(allCourses.get(courseId));
        System.out.println("Professor is exempted\n");
    }
    public static void main(String[] args) {

        allCourses.add(new Course("java_beginner","bachelor"));
        allCourses.add(new Course("java_intermediate","bachelor"));
        allCourses.add(new Course("python_basics","bachelor"));
        allCourses.add(new Course("algorithms","master"));
        allCourses.add(new Course("advanced_programming","master"));
        allCourses.add(new Course("mathematical_analysis","master"));
        allCourses.add(new Course("computer_vision","master"));
        allMembers.add(new Student("Alice"));
        allMembers.add(new Student("Bob"));
        allMembers.add(new Student("Alex"));
        Student st = (Student) allMembers.get(0);
        st.enroll((Course) allMembers.get(0));

    }
    public static void fillinitialData(){

    }
}