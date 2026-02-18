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

    // Find the youngest and oldest students
    public Map<String, Student> getYoungestAndOldestStudent(){
        return   Map.of(
           "Oldest",  students.stream().max(Comparator.comparingInt(Student::getAge)).orElse(null),
           "Youngest",  students.stream().min(Comparator.comparingInt(Student::getAge)).orElse(null)
        );
         
    }

    // Calculate scholarship distribution percentage
    public long getScholarshipDistPercentage(){
        long totalStudentCount = students.size();
        long scholarshipCount = students.stream().filter(x -> x.isScholarshipRecipient()).count();
        return (scholarshipCount*100) / totalStudentCount;
    }
    
    // Determine gender ratio in different majors
    public Map<String, Map< String, Long>> getGenderRationByMajor(){
        //me complicating it again
        // return students.stream().collect(Collectors.groupingBy(Student::getMajor,
        //     Collectors.groupingBy(Student::getGender,
        //         Collectors.collectingAndThen(
        //             Collectors.toList(), 
        //             list -> {
        //                 return list.size();
        //             }
        //         )
        //     )
        // ));
        return students.stream().collect(Collectors.groupingBy(Student::getMajor,
            Collectors.groupingBy(Student::getGender, Collectors.counting())));

        // System.out.println();

    }

    // Find universities with highest average GPA
    public String  getHighestAverageGpaUni(){
        return students.stream().collect(Collectors.groupingBy(
            Student::getUniversity,
            Collectors.averagingDouble(Student::getGpa)))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey).orElse("other");
        
    }

    /////////////////////////////////////////////////////////////// Complex Analytical Tasks ///////////////////////////////////////////////////////////////
    /// 
    /// 
    /// 
    /// 
    /// Identify students matching multiple criteria:
    // - GPA > 3.7
    // - International student
    // - STEM major
    // - Scholarship recipient
    public List<Student> getStudentFromCriteria(double gpa, boolean isInternational,  boolean isRecipient){
        List<String> majors = Arrays.asList(
            "Biology",
            "Chemistry",
            "Physics",
            "Computer Science",
            "Mechanical Engineering",
            "Electrical Engineering"
        );
        return  students.stream()
                .filter( x -> x.getGpa() == gpa && x.isInternational() == isInternational && x.isScholarshipRecipient() == isRecipient &&  majors.contains(x.getMajor()))
                .collect(Collectors.toList());
    }

    // Create a comprehensive student profile ranking system
    //just made a ranking system from chatgpt 
    // GPA_Score * 0.6 + Credit_Score * 0.3 + Scholarship_Status
    // GPA_Score = GPA / 4
    //  Credit_Score = Credit_Hours / 120
    public List<Student> rankStudents(){
        return students.stream().sorted((s1, s2) -> {
            double score1 =
                    0.6 * (s1.getGpa() / 4.0)
                    + 0.3 * Math.min(s1.getCreditHours() / 120.0, 1.0)
                    + (s1.isScholarshipRecipient() ? 0.1 : 0.0);

            double score2 =
                    0.6 * (s2.getGpa() / 4.0)
                    + 0.3 * Math.min(s2.getCreditHours() / 120.0, 1.0)
                    + (s2.isScholarshipRecipient() ? 0.1 : 0.0);
            return  Double.compare(score2, score1); // descending
        }).toList();
    }
    // chatgpt told me there is a better way to do it 
    // create a funtion seprately 
    public double calculateScore(Student student) {
        double gpaScore = student.getGpa() / 4.0;
        double creditScore = Math.min(student.getCreditHours() / 120.0, 1.0);
        double scholarshipBonus = student.isScholarshipRecipient() ? 0.1 : 0.0;

        return 0.6 * gpaScore
            + 0.3 * creditScore
            + scholarshipBonus;
    }
    public List<Student> rankStudents2(){
        return students.stream()
                        .sorted(Comparator.comparingDouble((Student x) -> calculateScore(x)).reversed())
                        .toList();
    }

    


}
