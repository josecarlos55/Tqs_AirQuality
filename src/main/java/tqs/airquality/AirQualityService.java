package tqs.airquality;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tqs.airquality.Data.Cache;
import tqs.airquality.domain.AirQuality;
import tqs.airquality.domain.AirQualityInfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class AirQualityService {

    Cache cache = new Cache();

    private static final String URL = "http://api.weatherbit.io/v2.0/current/airquality?city=";

    /**
     * Salvar a cidade em cache
     */
    public void saveCity(String city) {
        cache.addCity(city);
    }


    //obter cidades disponiveis

    public List<String> getCities() {
        return cache.getCities();
    }


    public Map<String, String> getStatistics() {
        return cache.getStatistics();
    }


    public void saveAirQuality(String city, AirQuality airQuality) {
        cache.saveAirQuality(city, airQuality);
    }

    /**
     * Qualidade da cidade atraves da api,  caso seja null colocará um miss
     */
    public AirQualityInfo[] getAirQuality(String city) {

        if (!cache.isValid(city)) {

            RestTemplate restTemplate = new RestTemplate();
            String finalUrl = URL + city + "&country=BR&key=1129cb22b09f462bac4caec3512a03b0";
            AirQuality airQuality = restTemplate.getForObject(finalUrl, AirQuality.class);

            // se for valido entao será salvo
            if (airQuality != null) {

                //caso nao consiga, miss
                cache.setMisses();
                airQuality.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
                this.saveAirQuality(city, airQuality);
                return cache.getAirQuality(city).getData();
            }
            // prevenção de white labels com nulls

            else

                return new AirQualityInfo[] {new AirQualityInfo()};
        }
        else {

            cache.setHit();
            //set hit++
            return cache.getAirQuality(city).getData();
        }

    }

}
