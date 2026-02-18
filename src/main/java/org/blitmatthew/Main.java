package org.blitmatthew;

// import org.blitmatthew.service.CarOps;
import org.blitmatthew.service.CarOps;

public class Main {
    public static void main(String[] args) {
        CarOps carOps = new CarOps();
        // StudentOps studentOps = new StudentOps();

        System.out.println(carOps.getCarsByFuel("Plug-in Hybrid"));
        // System.out.println(studentOps.getStudentFromCriteria(2.89, false, true));
        // System.out.println(studentOps.groupStudents());
        // studentOps.analyseCorelation();
        // studentOps.getGenderRationByMajor();
    }
}