package tqs.airquality.domain;


public class AirQuality {

    private long timestamp;
    private AirQualityInfo[] data;


    public AirQuality() {

    }

    public AirQuality(AirQualityInfo[] data, long timestamp) {

        this.data = data;

        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public AirQualityInfo[] getData() {
        return data;
    }


    public void setData(AirQualityInfo[] data) {
        this.data = data;
    }


}
