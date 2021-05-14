package tqs.airquality.domain;


public class AirQualityInfo {
    private String AQI;
    private String CO;
    private String SO2;
    private String NO2;
    private String O3;


    public AirQualityInfo() {
    }

    public AirQualityInfo(String AQI, String CO, String SO2, String NO2, String O3) {

        this.AQI = AQI;
        this.CO = CO;
        this.SO2 = SO2;
        this.NO2 = NO2;
        this.O3 = O3;

    }

    public String getAQI() {
        return AQI;
    }


    public void setAQI(String AQI) {
        this.AQI = AQI;
    }


    public String getCO() {
        return CO;
    }



    public void setCO(String CO) {
        this.CO = CO;
    }


    public String getSO2() {
        return SO2;
    }


    public void setSO2(String SO2) {
        this.SO2 = SO2;
    }





    public String getO3() {
        return O3;
    }


    public void setO3(String O3) {
        this.O3 = O3;
    }


    public String getNO2() {
        return NO2;
    }


    public void setNO2(String NO2) {
        this.NO2 = NO2;
    }




}
