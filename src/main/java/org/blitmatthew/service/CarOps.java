package org.blitmatthew.service;

import java.util.List;
import java.util.stream.Collectors;

import org.blitmatthew.data.DataRetriever;
import org.blitmatthew.entity.Car;

public class CarOps {
    List<Car> cars = DataRetriever.getCars();

    public Integer getCarCount() {
        return cars.size();
    }

    /////////////////////////////////////////////////////////////// Basic Filtering Tasks ///////////////////////////////////////////////////////////////
    /// 
    /// 
    /// 
    /// 
    /// Find all electric vehicles
    public  List<Car> getElectricCars(){
        return cars.stream().filter(x -> x.getFuelType().equals("Electric")).collect(Collectors.toList());
    }

    // List cars under $30,000
    public  List<Car> getCarsUnderUnderThirtyK(){
        return cars.stream().filter(x -> x.getPrice() < 30000).collect(Collectors.toList());
    }
}
