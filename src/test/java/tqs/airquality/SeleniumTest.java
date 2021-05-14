package tqs.airquality;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;



@ExtendWith(SeleniumExtension.class)
public class SeleniumTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeEach
    public void setUp() throws Exception {

        System.setProperty("webdriver.gecko.driver","geckodriver");
        driver = new ChromeDriver();
        baseUrl = "https://www.katalon.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterEach
    public void pullDown() throws Exception {
        driver.quit();

        String errorString = verificationErrors.toString();

        if (!"".equals(errorString)) {
            Assertions.fail(errorString);
        }
    }

    @Test
    public void test() throws Exception {
        boolean ok = false;

        driver.get("http://localhost:8080");

        List<String> cities = new ArrayList<>();


        String[] cidades = {"Brasília", "Salvador", "Manaus", "Rio de Janeiro", "Curitiba", "Belém", "Campinas", "Recife",
                "Guarulhos", "Goiania", "Aracaju", "Sorocaba", "Teresina", "Santos", "Niterói", "Vitória",
                "Pelotas", "Petrópolis", "Taubaté", "Macaé", "Porto Alegre", "Manaus","Fortaleza","Maringá","Bahia"};


        for (String cd : cidades ) {
            cities.add(cd);
        }

        for (String city : cities) {

            new Select(driver.findElement(By.id("selectCity"))).selectByVisibleText(city);
            driver.findElement(By.id("info")).click();
            driver.findElement(By.xpath("//button[@type='button']")).click();
            ok = true;

        }

        assertThat(ok).isTrue();
    }



}
