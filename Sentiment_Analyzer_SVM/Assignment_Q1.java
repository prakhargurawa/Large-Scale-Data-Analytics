
public class Assignment_Q1 {

    public static void main(String args[]){
        /*
            Solution for Question 1 of Assignment 3
         */
        WeatherStation weatherStation=Util.generateWeatherStation(); // Creating a dummy weather station system for question
        Double inputTemp = 19.0;
        System.out.println("The number of temperature approx calculated for temp "+inputTemp+" is "+WeatherStation.countTemperature(inputTemp));
    }
}
