package tqs.airquality;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.airquality.Data.Cache;
import tqs.airquality.domain.AirQuality;
import tqs.airquality.domain.AirQualityInfo;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;



@ExtendWith(MockitoExtension.class)
class AirQualityServiceTest {

    @Mock(lenient = true)

    private Cache cache;

    @InjectMocks

    private AirQualityService airQualityService;

    @BeforeEach

    public void setUp() {
        //definação de todas as cidades a serem inseridas

        List<String> allCities = Arrays.asList("Rio de Janeiro", "São Paulo", "Curitiba","Campinas","Teresina");


        //estatisticas que serao inseridas no map

        HashMap<String, String> statistics = new HashMap<>();

        statistics.put("hit", "7");
        statistics.put("misses", "3");
        statistics.put("citiesAirQualityInfo", allCities.toString());

        AirQualityInfo info = new AirQualityInfo("32", "67", "223.12", "1.5", "1.1");

        AirQualityInfo[] airInfo = {info};

        long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

        AirQuality Curitiba = new AirQuality(airInfo,timestamp);

        Mockito.when(cache.getCities()).thenReturn(allCities);
        Mockito.when(cache.getStatistics()).thenReturn(statistics);
        Mockito.when(cache.getAirQuality("Curitiba")).thenReturn(Curitiba);
        Mockito.when(cache.getAirQuality("invalid")).thenReturn(null);
    }

    /**
     *  public void getCityDetails_returnsValid() {
     *
     *         AirQualityInfo info = new AirQualityInfo("32", "67", "223.12", "1.5", "1.1");
     *         HashMap<String, String> statistics = new HashMap<>();
     *         statistics.put("hit", "7");
     *         statistics.put("misses", "3");
     *         AirQualityInfo[] airInfo = {info};
     *         long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
     *         AirQuality Campinas = new AirQuality(airInfo,timestamp);
     *
     *         assertThat(Campinas)
     *                 .hasFieldOrProperty(String.valueOf(airInfo));
     *
     *     }
     *
     *
     */
            //obter todas as cidades e verificar seu tamanho
     @Test
     public void Test_GetAll() {

         String city0 ="Rio de Janeiro";
         String city1 = "São Paulo";
         String city2 = "Curitiba";
         String city3 = "Campinas";
         String city4= "Teresina";

         
         List<String> all = airQualityService.getCities();
         assertThat(all)
            .hasSize(5)
             .contains(city0, city1, city2, city3, city4)
             .extracting(String::toString);
     }


     //Test method: HashMap<String, String> getStatistics()
        //verificar se possui as 3 chaves
     @Test
     public void Statistics_GetStatistics_Return() {

         int misses = 3;
         int hit = 7;

         List<String> cities = Arrays.asList("Rio de Janeiro", "São Paulo", "Curitiba","Campinas", "Teresina");

         Map<String, String> stat = airQualityService.getStatistics();

         System.out.println(stat);

         assertThat(stat)
            .hasSize(3)
            .containsKeys("hit", "misses", "citiesAirQualityInfo")
            .containsValues(String.valueOf(hit), String.valueOf(misses), cities.toString());
     }

        //verificar a qualidade se possui, se a api retorna a mesma
     //Test method: AirQualityInfo[] getAirQuality(String city)
     @Test
     public void ValidCity_Air() {
         String validCity = "Curitiba";

         AirQualityInfo[] returned = airQualityService.getAirQuality(validCity);
         AirQualityInfo info= airQualityService.getAirQuality(validCity)[0];


         assertThat(returned)
                 .hasSize(1);
         assertEquals("32", info.getAQI());
         assertEquals("1.1", info.getO3());
         assertEquals("67", info.getCO());
         assertEquals("223.12", info.getSO2());
         assertEquals("1.5", info.getNO2());
     }






}