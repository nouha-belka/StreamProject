package org.blitmatthew.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // List cars under ,000
    public  List<Car> getCarsUnderUnderThirtyK(){
        return cars.stream().filter(x -> x.getPrice() < 30000).collect(Collectors.toList());
    }
    
    // Identify cars with manual transmission
    public  List<Car> getManualCars(){
        return cars.stream().filter(x -> x.getTransmission().equals("Manual")).collect(Collectors.toList());
    }
    
    // Find vehicles from specific manufacturers
    public List<Car> getCarsFromManufacturer(String... manufacturers){
        List<String> manufacturerList = Arrays.asList(manufacturers);
        return cars.stream().filter(x -> manufacturerList.contains(x.getMake()) ).collect(Collectors.toList());
    }
    
    //  List cars manufactured after 2020
    public List<Car> getCarsAfter202(){
        return cars.stream().filter(x -> x.getYear() > 2020).collect(Collectors.toList());
    }
    
    //  Identify cars with mileage under 50,000
    public List<Car> getCarsMileageUnder50k(){
        return cars.stream().filter(x -> x.getMileage() <= 50000).collect(Collectors.toList());
    }
    
    //  Find cars in "Excellent" condition
    public  List<Car> getExcellentCars(){
        return cars.stream().filter(x -> x.getCondition().equals("Manual")).collect(Collectors.toList());
    }
    
    //  Filter cars by specific fuel types
    public List<Car> getCarsByFuel(String... fuels){
        List<String> fuelList = Arrays.asList(fuels);
        return cars.stream().filter(x -> fuelList.contains(x.getFuelType()) ).collect(Collectors.toList());
    }
    
    /////////////////////////////////////////////////////////////// Advanced Filtering Tasks///////////////////////////////////////////////////////////////
    /// 
    /// 
    /// 
    /// Cars with price between ,000 and ,000
    public List<Car> getCarsbetween40kAnd60k(){
        return cars.stream().filter(x -> x.getMileage() >= 40000 && x.getMileage() <= 60000 ).collect(Collectors.toList());
    }
    
    // Hybrid vehicles manufactured in last 3 years
    public List<Car> getHybridCarsInLast3Years(){
        LocalDateTime now = LocalDateTime.now();
        return cars.stream().filter(x -> x.getFuelType().equals("Hybrid") && x.getYear() > now.getYear() - 3  ).collect(Collectors.toList());
    }
    // Low-mileage luxury vehicles
    public List<Car> getLxuryLowMileageCars(){
        List<String> luxuryBrands = Arrays.asList(
            "Audi",
            "BMW",
            "Lexus"
            
        );
        return cars.stream().filter(x -> luxuryBrands.contains(x.getMake()) && x.getMileage() <= 10000 ).collect(Collectors.toList());
    }

    // Performance cars with specific characteristics

    // Vehicles meeting multiple complex criteria

    // Find cars with specific color combinations
    public List<Car> getCarsWithColors(String... colors){
        List<String> colorsList = Arrays.asList(colors);
        return cars.stream().filter(x -> String.join(" ", colorsList).equals(x.getColor())).collect(Collectors.toList());
    }

    // Identify most and least expensive vehicles by make
    public Map< String, Map< String, Car>> getMostAndLeastExpansiveCarsByMake(){
        return cars.stream().collect(Collectors.groupingBy(
            Car::getMake,
            Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    Map<String, Car> map = new HashMap<>();
                    map.put("Most", ((Collection<Car>) list).stream().max( Comparator.comparingDouble(Car::getPrice)).get());
                    map.put("Least", ((Collection<Car>) list).stream().min( Comparator.comparingDouble(Car::getPrice)).get());
                    return map;
                }
            )
        ));
    }

}
