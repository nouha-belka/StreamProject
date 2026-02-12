package org.blitmatthew.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.blitmatthew.data.DataRetriever;
import org.blitmatthew.entity.Student;

public class StudentOps {
    private final List<Student> students = DataRetriever.getStudents();
    
    public Integer getStudentCount() {
        return students.size();
    }

    /////////////////////////////////////////////////////////////// Basic Filtering Tasks ///////////////////////////////////////////////////////////////
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
    
    
    /////////////////////////////////////////////////////////////// Advanced Filtering Tasks ///////////////////////////////////////////////////////////////
    /// 
    /// 
    // Students with GPA between 3.0 and 3.5
    public List<Student> geStudentGPABetween3and35( ){
        return students.stream().filter(x -> x.getGpa() >= 3 && x.getGpa() <= 3.5).collect(Collectors.toList());
    }
    
    // International students in STEM majors
    public List<Student> geInternationalStemStudents( ){
        List<String> majors = Arrays.asList(
            "Biology",
            "Chemistry",
            "Physics",
            "Computer Science",
            "Mechanical Engineering",
            "Electrical Engineering"
        );
        return students.stream().filter(x -> x.isInternational() &&  majors.contains(x.getMajor())).collect(Collectors.toList());
    }

    // Scholarship recipients with high credit hours
    public List<Student> geScolarshipStudentsWithHighCredits( ){
        return students.stream().filter(x -> x.isScholarshipRecipient() && x.getCreditHours() >= 60).collect(Collectors.toList());
    }

    //Students in top 10% of their class by GPA (i went too far with everything and did some grouing and all meanwhile what was asked for is just a normal list)
    // public Map<String,Map<Integer, List<Student>>> getTop10StudentsInClass(){
    //     return students.stream()
    //     .collect(Collectors.groupingBy(
    //         Student::getUniversity, 
    //         Collectors.groupingBy(
    //             Student::getGraduationYear,
    //             Collectors.collectingAndThen(
    //                 Collectors.toList(),
    //                 list -> {
    //                     list.sort(Comparator.comparing(Student::getGpa).reversed());
    //                     int topCount = Math.max(1, (int) Math.ceil(list.size()*0.1));
    //                     return list.subList(0, topCount);
    //                 }
    //             )
    //         )
    //     ));

    // }

    //Students in top 10% of their class by GPA 
    public List<Student>getTop10StudentsInClass(String uni, int gradYear){
        return students.stream().filter(x -> x.getGraduationYear() == gradYear && x.getUniversity().equals(uni))
        .collect(Collectors.collectingAndThen(
            Collectors.toList(), 
            list -> {
                list.sort(Comparator.comparing(Student::getGpa).reversed());
                int topCount = Math.max(1, (int) Math.ceil(list.size()*0.1));
                return list.subList(0, topCount);
            }
        ));
    }

    // Students with phone numbers from specific area codes
    public List<Student> getStudentsFromAreaCode(String areaCode){
        return students.stream().filter(x -> x.getPhoneNumber().startsWith("(" + areaCode + ")")).collect(Collectors.toList());
    }

    // Find students born in specific months
     public List<Student> getStudentsByMonth(String... months){
        return students.stream().filter( x -> {
            for(String month : months){
                 if(x.getDateOfBirth().contains("-" +  month +  "-")){
                    return true;
                 }
            }
            return false;
        }).collect(Collectors.toList());
     }

    //  Identify students near graduation (within 2 years)
    public List<Student> getStudentNearGrad(int requestYear){
        return students.stream().filter( x -> x.getGraduationYear() >= requestYear -2 && x.getGraduationYear() <= requestYear +2 ).collect(Collectors.toList());
    }

    /////////////////////////////////////////////////////////////// Aggregation and Statistical Analysis ///////////////////////////////////////////////////////////////
    /// 
    /// 
    /// 
    /// 
    /// Calculate total number of students per major
    public Map<String, Long> getStudentCountPerMajor(){
        return students.stream().collect(Collectors.groupingBy(
            Student::getMajor,
            Collectors.counting()
        ));
    }
    
    // Find average GPA by major
    public Map<String, Double> getStudentAveragePerMajor(){
        return students.stream().collect(Collectors.groupingBy(
            Student::getMajor,
            Collectors.averagingDouble(Student::getGpa)
        ));
    }
    
    // Determine percentage of international students
    public long getInternationalStudentPercentage(){
        long totalStudentCount = students.size();
        long internationalStudentCount =  students.stream().filter( x -> x.isInternational()).count();
        
        return (internationalStudentCount * 100) / totalStudentCount;
    }
    
    // Calculate average age of students
    public Double getAvgAge(){
        // System.out.println(students.stream().mapToInt(Student::getAge).boxed().collect(Collectors.toList()));
        return students.stream().mapToInt(Student::getAge).average().orElse(0);
    }

    // Find total credit hours across all students
    public int getTotalCreditHours(){
        return students.stream().mapToInt(Student::getCreditHours).sum();
    }
    // Compute median GPA
    public double getGpaMedian(){

        // first method only get GPA list from stream and then calculate the median
        // List<Double> gpaList =  students.stream().map(Student::getGpa).sorted().collect(Collectors.toList());

        // int listSize = gpaList.size();
        // double median;
        // if( listSize == 0 ){
        //     median = 0;
        // }else if(listSize % 2 == 0 ){
        //     median = gpaList.get(listSize/2);
        // }
        // else{
        //     median = (gpaList.get(listSize / 2 - 1) + gpaList.get(listSize / 2)) / 2.0;
        // }

        //second method use the collectingAndThen stream method
        double median = students.stream().map(Student::getGpa)
                                .sorted().collect(Collectors.collectingAndThen(
                                    Collectors.toList(), 
                                    list -> {
                                        int listSize = list.size();
                                        if (listSize == 0 ) return 0.0;
                                        if (listSize % 2 == 1 ) return list.get(listSize/2);
                                        else return (list.get(listSize / 2 - 1) + list.get(listSize / 2)) / 2.0;
                                    }
                                ));
        // System.out.println(median);
        return median;
    }

    
}
