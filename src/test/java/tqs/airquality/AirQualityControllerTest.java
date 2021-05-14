package tqs.airquality;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.airquality.Controller.AirQualityController;
import tqs.airquality.domain.AirQualityInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AirQualityController.class)
class AirQualityControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityService airQualityService;



      //Test method: List<String> getCities()

    //vericação se o fiquei json possui as cinco cidades e verificação sua adição.
    @Test
    public void TestCities_ReturnJson() throws Exception {
        List<String> allCities = new ArrayList<>();

        String[] cities = {"Rio de Janeiro", "São Paulo", "Curitiba","Campinas", "Teresina"};

        for (String c : cities)
            allCities.add(c);

        given(airQualityService.getCities()).willReturn(allCities);

        mvc.perform(get("/api/airQuality/cities")

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0]", is("Rio de Janeiro")))
                .andExpect(jsonPath("$[1]", is("São Paulo")))
                .andExpect(jsonPath("$[2]", is("Curitiba")))
                .andExpect(jsonPath("$[3]", is("Campinas")))
                .andExpect(jsonPath("$[4]", is("Teresina")));
    }


      //Test method: HashMap<String, String> getStatistics()
        // verificação se o json possui os 3 atributos corretos
    @Test
    public void TestCities_ReturnStatsJson() throws Exception {
        List<String> allCities = new ArrayList<>();

        String[] cities = {"Rio de Janeiro", "São Paulo", "Curitiba","Campinas", "Teresina"};

        for (String c : cities)
            allCities.add(c);


        HashMap<String, String> statistics = new HashMap<>();
        statistics.put("hit", "7");
        statistics.put("misses", "3");
        statistics.put("citiesAirQualityInfo", allCities.toString());


        given(airQualityService.getStatistics()).willReturn(statistics);

        mvc.perform(get("/api/airQuality/statistics")

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.misses", is(statistics.get("misses"))))
                .andExpect(jsonPath("$.hit", is(statistics.get("hit"))))
                .andExpect(jsonPath("$.citiesAirQualityInfo", is(statistics.get("citiesAirQualityInfo"))));
    }


     //Test method: AirQualityInfo[] getCityAirQuality(@PathVariable(value = "city") String city)
    //verificação se o ficheiro json possui todas as chaves
    @Test
    public void GetAirQuality_ReturnAirQualityInfo() throws Exception {
        AirQualityInfo info = new AirQualityInfo("32", "68.2071", "205", "1.51429", "6");

        AirQualityInfo[] airInfo = {info};

        String valid_city = "Santos";

        given(airQualityService.getAirQuality(anyString())).willReturn(airInfo);

        mvc.perform(get("/api/airQuality/".concat(valid_city)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", hasKey("aqi")))
                .andExpect(jsonPath("$[0]", hasKey("co")))
                .andExpect(jsonPath("$[0]", hasKey("so2")))
                .andExpect(jsonPath("$[0]", hasKey("no2")))
                .andExpect(jsonPath("$[0]", hasKey("o3")));

    }

     //Verificação se com cidade invalidade nao retorna nada

    @Test
    public void GetCityDoesNotExist_ReturnNothing() throws Exception {
        String invalid_city = "coisaqualquer";

        given(airQualityService.getAirQuality(invalid_city)).willReturn(null);

        mvc.perform(get("/airQuality/".concat(invalid_city)))
                .andExpect(status().isNotFound());
    }

}