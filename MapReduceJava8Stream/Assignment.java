import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public class Assignment {

    public static void main(String args[]){

        System.out.println("Assignment Part 1 :");
        // Create a weather station with seven measurements where time varies from 1 to 7
        WeatherStation weatherStation=new WeatherStation("Galway",Util.generateMeasurements());
        //Find the maximum temperature recorded between time 2 to 6
        System.out.println("The maximum temperature generated between time 2 to 6 : "+weatherStation.maxTemperature(2,6));

        System.out.println("\nAssignment Part 2 : ");
        // Create a weather station for part 2
        List<SimpleEntry> result=Util.generateWeatherStation().countTemperatures(19.0,10.8,2.1);
        System.out.println("(("+result.get(0).getKey()+","+result.get(0).getValue()+"),("+result.get(1).getKey()+","+result.get(1).getValue()+"))");
    }
}
