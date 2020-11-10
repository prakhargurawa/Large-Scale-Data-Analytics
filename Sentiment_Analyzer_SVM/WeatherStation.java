import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Serializable;
import scala.Tuple2;
import java.util.List;
import java.lang.Math;

public class WeatherStation implements Serializable {

    private String city;
    private List<Measurement> measurements;
    private static List<WeatherStation> stations;

    public WeatherStation(String city) {
        this.city = city;
    }

    public WeatherStation(String city, List<Measurement> measurements) {
        this.city = city;
        this.measurements = measurements;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public List<WeatherStation> getStations() {
        return stations;
    }

    public void setStations(List<WeatherStation> stations) {
        WeatherStation.stations = stations;
    }

    public static Integer countTemperature(Double t){
        /*
         Solution for part 1 of Assignment 3 : This function approximately measures count for a temperature
         */
        System.setProperty("hadoop.home.dir", "C:/winutils");
        SparkConf sparkConf = new SparkConf()
                .setAppName("Count Temperature")
                .setMaster("local[4]").set("spark.executor.memory", "1g");
        // Initialize JavaSparkContext
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);

        // store all the weather station in weatherStationJavaRDD
        JavaRDD<WeatherStation> weatherStationJavaRDD =ctx.parallelize(stations,1);

        // Use flatmap to store all measurements of all weather stations
        JavaRDD<Measurement> measurementJavaRDD= weatherStationJavaRDD.flatMap((x)-> x.getMeasurements().iterator());

        // remove all measurements whole temperature not in range ( +1, -1 of given temperature ) ie. those are not approx measurements
        measurementJavaRDD=measurementJavaRDD.filter(m -> Math.abs(m.getTemperature()-t)<=1);

        //map each measurement to (temperature,1)
        JavaPairRDD<Double, Integer> temperaturePairRDD =measurementJavaRDD.mapToPair((Measurement s) -> new Tuple2<Double, Integer>(t, 1));

        // sort based on temperature and count : this process does both shuffle and reduce
        JavaPairRDD<Double, Integer> counts = temperaturePairRDD.reduceByKey((Integer i1, Integer i2) -> i1 + i2);
        List<Tuple2<Double, Integer>> output = counts.collect();

        // Close connections with spark
        ctx.stop();
        ctx.close();

        // return number of approx temperature measured
        return output.get(0)._2;
    }

    @Override
    public String toString() {
        return "WeatherStation{" +
                "city='" + city + '\'' +
                ", measurements=" + measurements +
                '}';
    }
}
