package tqs.airquality;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tqs.airquality.Data.Cache;
import tqs.airquality.domain.AirQuality;
import tqs.airquality.domain.AirQualityInfo;

import java.sql.Timestamp;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheTest {
    protected Cache cache = new Cache();

    @Test
    public void testget_andAdd(){

        testGetStatistics();

        testAddStatistics();
    }



    /**
     *
     *  @Test
     *     void checkRemoved(){
     *         String city0 = "Paraiba";
     *         cache.addCity(city0);
     *         cache.RemoveFromCache(city0);
     *
     *         System.out.println(cache);
     *         System.out.println(cache);
     *         System.out.println(cache);
     *
     *         assertEquals(30, this.cache.getCache().size());
     *     }
     *
     */

    @Test
    private void testGetStatistics() {

        HashMap<String, String> expected = new HashMap<>();

        List<String> citiesAirInfoIn = new ArrayList<>();

        expected.put("misses", "0");

        expected.put("hit", "1");

        expected.put("citiesAirQualityInfo", citiesAirInfoIn.toString());

        Map<String, String> found = cache.getStatistics();

        assertThat(expected).isEqualTo(found);
    }

    //numero de requets ao sistema é correto
    @Test
    void cacheRequests(){
        String city0 = "Paraiba";
        String city1 = "Roraima";
        String city2 = "Goiás";
        String city3 = "Maranhão";

        cache.addCity(city0);
        cache.addCity(city1);
        cache.addCity(city2);
        cache.addCity(city3);

        List<String> cities = cache.getCities();

        cache.searchInCache(city0);
        cache.searchInCache(city1);
        cache.searchInCache(city2);
        cache.searchInCache(city3);



        assertEquals(8, this.cache.getNrequests());


    }
        //teste de adiçao de statistics com misses e hites, maneira que encontrei


    @Test
    private void testAddStatistics() {
        int hit = 1;
        int misses = 0;

        Set<String> citiesAirInfoIn = new HashSet<String>();

        citiesAirInfoIn.add("Rio de Janeiro");

        AirQuality air = new AirQuality();
        air.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());

        cache.setMisses();
        misses++;
        cache.setHit();
        hit++;
        cache.saveAirQuality("Rio de Janeiro", air);
        Map<String, String> found = cache.getStatistics();
        hit++;

        HashMap<String, String> expected = new HashMap<>();
        expected.put("misses", String.valueOf(misses));
        expected.put("hit", String.valueOf(hit));
        expected.put("citiesAirQualityInfo", citiesAirInfoIn.toString());

        assertThat(expected).isEqualTo(found);
    }

    @Test
    public void testGetandAdd(){

        testGetCities();
        testAddCitiesandGet();
    }


    @Test
    private void testAddCitiesandGet() {
        String city0 = "Paraiba";
        String city1 = "Roraima";
        String city2 = "Goiás";
        String city3 = "Maranhão";

        cache.addCity(city0);
        cache.addCity(city1);
        cache.addCity(city2);
        cache.addCity(city3);

        List<String> cities = cache.getCities();
        System.out.println(cities);
        assertEquals(33, cities.size());
        assertThat(cities)
                .contains(city0, city1,city2,city3)
                .doesNotContain("Lisboa");
    }
    //verificar se a cache possui todas as cidades
    @Test
    private void testGetCities() {
        List<String> cities = cache.getCities();
        assertEquals(29, cities.size());

        assertThat(cities)
                .contains("Brasília", "Salvador", "Manaus", "Rio de Janeiro", "Curitiba", "Belém", "Campinas", "Recife",
                        "Guarulhos", "Goiania", "Aracaju", "Sorocaba", "Teresina", "Santos", "Niterói", "Vitória",
                        "Pelotas", "Petrópolis", "Taubaté", "Macaé", "Porto Alegre", "Manaus","Fortaleza","Maringá","Bahia");
    }

        //verificaçao de cidade invalida
    @Test
    public void testInvalidCity() {
        boolean res0 = cache.isValid("jijij");

        assertThat(res0).isFalse();

        AirQuality air = new AirQuality();

        cache.saveAirQuality("Campinas", air);

        boolean res1 = cache.isValid("Campinas");
        assertThat(res1).isFalse();
    }



    @Test
    public void testGetAirQuality() {
        String city = "Curitiba";
        AirQualityInfo[] info = {new AirQualityInfo("28", "61", "327.11", "3.8", "2.7")};

        AirQuality airQ = new AirQuality(info, new Timestamp(System.currentTimeMillis()).getTime());
        cache.saveAirQuality(city, airQ);

        AirQuality comparetest = cache.getAirQuality("Curitiba");
        assertThat(comparetest.toString()).isEqualTo(airQ.toString());
        assertThat(comparetest.getData().toString()).isEqualTo(info.toString());
    }
        //salvar air quality

    @Test
    public void testSaveAirQuality() {
        String city = "Rio de Janeiro";

        AirQuality airQ = new AirQuality();

        cache.saveAirQuality(city, airQ);

        Map<String, AirQuality> inCache = cache.getCache();
        assertThat(inCache)
                .containsKey("Rio de Janeiro")
                .containsValue(airQ);
    }




}