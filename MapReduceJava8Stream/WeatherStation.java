import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.lang.Math;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.stream.Collectors.groupingBy;


public class WeatherStation {

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

    public Double maxTemperature(int startTime,int endTime) throws NoSuchElementException{
        /*
            Solution for part 1 : This function returns the max temperature recorded by this whether station
                                  between the range of startTime and endTime
         */
        Double maxTemperature = measurements.stream()
                .filter( m -> m.getTime() >= startTime && m.getTime() <= endTime ) // Filter all those measurements whose time in range
                .mapToDouble( m -> m.getTemperature() ) // map all measurements to their respective temperature
                .max().orElseThrow( NoSuchElementException::new ); // find the max temperature
        return maxTemperature;
    }

    public List<SimpleEntry> countTemperatures(Double t1,Double t2,Double r){
        /*
         Solution for part 2 : This function return count of temperature in range of t1 and t2 respectively.
                               In range means that temperature in range of (t1-r, t1+r)

         References : https://shekhargulati.com/2015/09/19/word-count-example-in-java-8/
         */

        // Represent Input and Splitting phase of mapreduce
        Stream<Measurement> preprocessedStream = stations.parallelStream() // represent parallel processing of hadoop map/reduce
                .flatMap(x -> x.getMeasurements().stream()) // using flatmap to flatten input stream of stream to stream of measurements -- Represents Input/preprocessing phase
                .filter(m -> Math.abs(m.getTemperature()-t1)<=r || Math.abs(m.getTemperature()-t2)<=r); // Preprocessing phase - filter those measurement which are of use/ in range

        // Represent map phase of mapreduce - SimpleEntry is java inbuilt structure to store generic key-value pair
        Stream<SimpleEntry> mappedStream = preprocessedStream.parallel()
                .map(m-> new SimpleEntry<>(m.getTemperature(), 1)); // represent map phase of hadoop mapreduce - m is mapped to (m,1)

        List<SimpleEntry> reduceOutput = new ArrayList<>(); // data structure to store final reduced output

        // Represent Shuffling phase of mapreduce
        mappedStream.parallel().collect(groupingBy(SimpleEntry::getKey)) // represents the shuffle phase of mapreduce - collecting on basis of key (temperature)
                .forEach((k,v)->{
                    reduceOutput.add(new SimpleEntry(k,v.size()));
                });

        // global counter to count final temperature in range of t1
        AtomicInteger t1count = new AtomicInteger(0);
        // global counter to count final temperature in range of t2
        AtomicInteger t2count = new AtomicInteger(0);

        // the reduceOutput is much smaller as compare to original provided data so its easier to traverse it now
        // Represent the final result phase after reduce phase of mapreduce
        reduceOutput.parallelStream().forEach( (k)->{
            Double temp= (Double) k.getKey();
            Integer count= (Integer) k.getValue();
            if(Math.abs(temp-t1)<=r){
                t1count.set((t1count.get()+count)); // t1count stores count of temperature in range of t1
            }
            if(Math.abs(temp-t2)<=r){
                t2count.set((t2count.get()+count)); // t2count stores count of temperature in range of t2
            }
        });

        // Data structure to return result in desired format
        List<SimpleEntry> ans=new ArrayList<>();
        ans.add(new SimpleEntry(t1,t1count));
        ans.add(new SimpleEntry(t2,t2count));
        return ans;
    }

    @Override
    public String toString() {
        return "WeatherStation{" +
                "city='" + city + '\'' +
                ", measurements=" + measurements +
                '}';
    }
}
