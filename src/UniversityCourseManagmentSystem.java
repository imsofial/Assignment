import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    static final int CAPACITY = 3;
    static int numberOfCourses = 0;
    static int courseId;
    String courseName;
    List<Student> enrolledStudents = new ArrayList<>();
    CourseLevel courseLevel;
    public Course(String courseName0, CourseLevel courseLevel0){
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

    public static CourseLevel tryValueOf(String courseLevel){
        try {
            return CourseLevel.valueOf(courseLevel);
        } catch (Throwable th){
            return null;
        }
    }
    static boolean add_course(String courseName, String courseLevelAsString){
        if (courseName.charAt(0)=='_' || courseName.charAt(courseName.length()-1)=='_'){
            System.out.println("Wrong inputs");
            return false;
        }
        for (int i=0;i<courseName.length();i++){ //check if name consist of english letters
            char c = courseName.charAt(i);
            if (!(c>='A' && c<='Z') && !(c>='a'&&c<='z') && (c!='_')) {
                System.out.println("Wrong inputs");
                return false;
            }
        }
        CourseLevel courseLevel = tryValueOf(courseLevelAsString);
        if (courseLevel==null){
            System.out.println("Wrong inputs");
            return false;
        }
        allCourses.add(new Course(courseName, courseLevel));
        System.out.println("Added successfully");
        return true;
    }
    static boolean add_student(String memberName){
        for (int i=0;i<memberName.length();i++){ //check if name consist of english letters
            char c = memberName.charAt(i);
            if (!((c>='A' && c<='Z') || (c>='a'&&c<='z'))){
                System.out.println("Wrong inputs");
                return false;
            }
        }
        if (memberName == "student"){
            System.out.println("Wrong inputs");
            return false;
        }
        allMembers.add(new Student(memberName));
        System.out.println("Added successfully");
        return true;
    }
    static boolean add_professor(String memberName){
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
    static void enroll_to_course(int memberId, int courseId){
        Student st = (Student) allMembers.get(memberId-1);
        st.enroll(allCourses.get(courseId-1));
        System.out.println("Enrolled successfully");
    }
    static void drop_from_course(int memberId, int courseId){
        Student st = (Student) allMembers.get(memberId-1);
        st.drop(allCourses.get(courseId-1));
        System.out.println("Dropped successfully");

    }
    static void assign_to_course(int memberId, int courseId){
        Professor prof = (Professor) allMembers.get(memberId-1);
        prof.teach(allCourses.get(courseId-1));
        System.out.println("Professor is successfully assigned to teach this course\n");
    }
    static void exempt_from_course(int memberId, int courseId){
        Professor prof = (Professor) allMembers.get(memberId-1);
        prof.exempt(allCourses.get(courseId-1));
        System.out.println("Professor is exempted\n");
    }
    public static void main(String[] args) {

        add_course("java_beginner","bachelor");
        add_course("java_intermediate","bachelor");
        add_course("python_basics","bachelor");
        add_course("algorithms","master");
        add_course("advanced_programming","master");
        add_course("mathematical_analysis","master");
        add_course("computer_vision","master");
        add_student("Alice");
        add_student("Bob");
        add_student("Alex");
        enroll_to_course(1,1);
        enroll_to_course(1,2);
        enroll_to_course(1,3);
        enroll_to_course(2,1);
        enroll_to_course(2,4);
        enroll_to_course(3,5);
        add_professor("Ali");
        add_professor("Ahmed");
        add_professor("Andrey");
        while (s.hasNext()){
            String comand = s.next();
            if (comand == "course"){
                String courseName = s.next();
                String courseLevel = s.next();
                add_course(courseName,courseLevel);
            } else if (comand == "student") {
                String memberName = s.next();
                add_student(memberName);
            } else if (comand == "professor"){
                String memberName = s.next();
                add_student(memberName);
            }else if (comand == "enroll"){
                int memberId = s.nextInt();
                int courseId = s.nextInt();
                enroll_to_course(memberId,courseId);
            } else if (comand == "drop"){
                int memberId = s.nextInt();
                int courseId = s.nextInt();
                drop_from_course(memberId,courseId);
            }else if (comand == "teach"){
                int memberId = s.nextInt();
                int courseId = s.nextInt();
                assign_to_course(memberId,courseId);
            }else if (comand == "exempt"){
                int memberId = s.nextInt();
                int courseId = s.nextInt();
                exempt_from_course(memberId,courseId);
            }
        }

    }
    public static void fillinitialData(){

    }
}