package tqs.airquality.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.airquality.domain.AirQualityInfo;
import tqs.airquality.AirQualityService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/airQuality")
public class AirQualityController {

    @Autowired
    private AirQualityService airQualityService;


    @GetMapping("/cities")
    public List<String> getCities() {
        return airQualityService.getCities();
    }


    @GetMapping("/{city}")
    public AirQualityInfo[] getCityAirQuality(@PathVariable(value = "city") String city) {

        return airQualityService.getAirQuality(city);
    }
    @GetMapping("/statistics")

    public Map<String, String> getStatistics() {
        return airQualityService.getStatistics();
    }


}