package org.blitmatthew.service;

import java.util.List;
import java.util.stream.Collectors;

import org.blitmatthew.data.DataRetriever;
import org.blitmatthew.entity.Student;

public class StudentOps {
    private List<Student> students = DataRetriever.getStudents();
    
    public Integer getStudentCount() {
        return students.size();
    }
    // Find all students with GPA above 3.5
    // public List<Student> getStudentsGPA35List(){

    //     final List<Student> studentsWithGPA35 = new ArrayList<Student>() ;

    //     for (Student student : students) {
    //         if(student.getGpa() >= 3.5){
    //             studentsWithGPA35.add(student);
    //         }
    //     }

    //     return studentsWithGPA35;

    // }

    public List<Student> getStudentsGPA35List(){
        return students.stream().filter(x -> x.getGpa() >= 3.5).collect(Collectors.toList());
    }

    // List students under 20 years old
    public List<Student> getStudentsYoungerThan20(){
        return students.stream().filter(x -> x.getAge() < 20).collect(Collectors.toList());
    }

    //Identify all international students
    public List<Student>  getInternationalStudent(){
        return students.stream().filter(x -> x.isInternational()).collect(Collectors.toList());
    }

    // Find students in Computer Science major
    public List<Student> getCSStudents(){
        return students.stream().filter(x -> x.getMajor().equals("Computer Science")).collect(Collectors.toList());
    }

    // List students from specific universities
    public List<Student> geStudentsFromUni(String uniName){
        return students.stream().filter(x -> x.getUniversity().equals(uniName)).collect(Collectors.toList());
    }
    
    // Identify students with scholarship
    public List<Student> geScholarshipStudents(){
        return students.stream().filter(x -> x.isScholarshipRecipient()).collect(Collectors.toList());
    }
    
    //Filter students by graduation year
    public List<Student> geStudentsByYear(int graduationYear){
        return students.stream().filter(x -> x.getGraduationYear() == graduationYear).collect(Collectors.toList());
    }
    
    //Find students with exactly 60 credit hours
    public List<Student> geStudents60CreditList( ){
        return students.stream().filter(x -> x.getCreditHours() == 60).collect(Collectors.toList());
    }




    

}
