package cepAutomation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class cepAutomationTests {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver;
        driver = new ChromeDriver();
        String cepBuscado = "";
        String cepBuscadoInvalido = "";

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        //Teste CEP válido
        driver.manage().window().maximize();
        driver.get("https://buscacepinter.correios.com.br/app/endereco/index.php");
        pag_objects_pesq_cep.fld_txt_cep(driver).sendKeys("69082-640");
        pag_objects_pesq_cep.btn_pesquisar(driver).click();
        cepBuscado = pag_objects_pesq_cep.res_cep(driver).getText();
        Assert.assertEquals(cepBuscado, "69082-640");


        //Test CEP inválido
        driver.get("https://buscacepinter.correios.com.br/app/endereco/index.php");
        pag_objects_pesq_cep.fld_txt_cep(driver).sendKeys("Instituto Creathus");
        pag_objects_pesq_cep.btn_pesquisar(driver).click();
        cepBuscadoInvalido = pag_objects_pesq_cep.res_cep_invalido(driver).getText();
        Assert.assertEquals(cepBuscadoInvalido, "Dados não encontrado");
        driver.quit();
    }
}



