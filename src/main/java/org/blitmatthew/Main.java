package org.blitmatthew;

import org.blitmatthew.service.CarOps;
import org.blitmatthew.service.StudentOps;

public class Main {
    public static void main(String[] args) {
        CarOps carOps = new CarOps();
        StudentOps studentOps = new StudentOps();

        // System.out.println(carOps.getCarCount());
        System.out.println(studentOps.getStudentsGPA35List());
    }
}