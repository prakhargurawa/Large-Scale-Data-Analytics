import java.io.Serializable;

public class Measurement implements Serializable {

    private Integer time;
    private Double temperature;

    public Measurement(Integer time, Double temperature) {
        this.time = time;
        this.temperature = temperature;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "time=" + time +
                ", temperature=" + temperature +
                '}';
    }
}
