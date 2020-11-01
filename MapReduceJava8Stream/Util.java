import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<Measurement> generateMeasurements(){
        /*
            This function generates dummy measurements used for question 1
         */
        List<Measurement> measurementList=new ArrayList<>();
        measurementList.add(new Measurement(1,42.3));
        measurementList.add(new Measurement(2,45.3));
        measurementList.add(new Measurement(3,12.3));
        measurementList.add(new Measurement(4,35.3));
        measurementList.add(new Measurement(5,49.3));
        measurementList.add(new Measurement(6,41.3));
        measurementList.add(new Measurement(7,23.3));
        return measurementList;
    }

    public static WeatherStation generateWeatherStation() {
        /*
            This function generates dummy weather station used for question 2
         */
        List<Measurement> m1=new ArrayList<>();
        m1.add(new Measurement(1,20.0));
        m1.add(new Measurement(2,11.7));
        m1.add(new Measurement(3,-5.4));
        m1.add(new Measurement(4,18.7));
        m1.add(new Measurement(5,20.9));
        WeatherStation w1 =new WeatherStation("W1",m1);

        List<Measurement> m2=new ArrayList<>();
        m2.add(new Measurement(1,8.4));
        m2.add(new Measurement(12,19.2));
        m2.add(new Measurement(122,7.2));
        WeatherStation w2 =new WeatherStation("W2",m2);

        List<WeatherStation> weatherStationList=new ArrayList<>();
        weatherStationList.add(w1);
        weatherStationList.add(w2);
        WeatherStation weatherStation1=new WeatherStation("Galway");
        weatherStation1.setStations(weatherStationList);
        return weatherStation1;
    }
}
