package tqs.airquality.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tqs.airquality.AirQualityService;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private AirQualityService airQualityService;

    @Override
    public void run(String... args) throws Exception {
        //cidades disponiveis

        String[] cities = {"Brasília", "Salvador", "Manaus", "Rio de Janeiro", "Curitiba", "Belém", "Campinas", "Recife",
                "Guarulhos", "Goiania", "Aracaju", "Sorocaba", "Teresina", "Santos", "Niterói", "Vitória",
                "Pelotas", "Petrópolis", "Taubaté", "Macaé", "Porto Alegre", "Manaus","Fortaleza","Maringá","Bahia"};


        // salva as cidades na cache
        for (String c : cities ) {
            airQualityService.saveCity(c);
        }
    }
}
