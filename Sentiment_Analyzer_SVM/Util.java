import java.util.ArrayList;
import java.util.List;

public class Util {

    public static WeatherStation generateWeatherStation() {
        /*
            This function generates dummy weather station used for assignment
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

    public static String getPredictedClassSVM(Object o) {
        Double score= (Double)o;
        if(score >0)
            return "1.0";
        return "0.0";
    }
}
