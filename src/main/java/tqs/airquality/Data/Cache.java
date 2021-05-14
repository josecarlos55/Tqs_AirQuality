package tqs.airquality.Data;

import tqs.airquality.domain.AirQuality;

import java.sql.Timestamp;
import java.util.*;
import java.util.HashMap;
import java.util.Map;


public class Cache {

    private static List<String> cities = new ArrayList<>();

    private Queue<String> names;

    private static Map<String, AirQuality> MyCache;

    private int maxSize;
    private int ind;
    private int nRequests;

    private static int misses = 0;
    private static int hit = 0;

    public Cache() {

        this.MyCache = new HashMap<>();
        this.maxSize=30;
        this.ind=0;
        this.nRequests=0;
        this.hit=0;
        this.misses=0;
        this.names = new LinkedList<>();

    }


    public void addCity(String city) {

        nRequests++;
        cities.add(city);
    }

    public List<String> getCities() {

        hit++;
        return cities;
    }

    public void RemoveFromCache(String c){

        if (ind >= maxSize){
            String name = this.names.remove();
            MyCache.remove(name);
            ind--;
        }


    }

    public List searchInCache(String name){

        this.nRequests++;
        if (cities.contains(name)){
            this.hit++;
        }
        this.misses++;
        return null;
    }

    /**
     * estatisticas de operaçao
     */
    public Map getStatistics() {

        this.nRequests++;
        hit++;

        Map<String, String> statistics = new HashMap<>();

        statistics.put("hit", String.valueOf(hit));
        statistics.put("misses", String.valueOf(misses));
        statistics.put("citiesAirQualityInfo", MyCache.keySet().toString());

        return statistics;
    }

    public void setMisses() {
        misses++;
    }

    public void setHit() {
        hit++;
    }

    /**
     * salva a cidade e sua qualidade de ar

     */
    public void saveAirQuality(String city, AirQuality airQuality) {
        MyCache.put(city, airQuality);
    }
    /**
     verificaçao se a cidade esta disponivel, e nao passou 25m de sua obtençao
     */
    public boolean isValid(String city) {

        long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        return (MyCache.containsKey(city) && currentTime - MyCache.get(city).getTimestamp() < 1500000);
    }

    /**
     map com cidade salvas
     */
    public Map<String, AirQuality> getCache() {
        return MyCache;
    }

    /**
    qualidade de ar respectiva
     */
    public AirQuality getAirQuality(String city) {
        return MyCache.get(city);
    }

    public int getHits(){
        return this.hit;
    }

    public int getMisses(){
        return this.misses;
    }

    public int getNrequests(){
        return this.nRequests;
    }


}