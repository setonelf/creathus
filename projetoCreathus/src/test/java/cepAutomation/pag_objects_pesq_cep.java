package cepAutomation;
import org.openqa.selenium.*;
public class pag_objects_pesq_cep {
    private static WebElement element = null;

    //Campo de CEP
    public static WebElement fld_txt_cep(WebDriver driver) {
        element = driver.findElement(By.id("endereco"));
        return element;
    }



    //botao de busca
    public static WebElement btn_pesquisar(WebDriver driver) {
        element = driver.findElement(By.id("btn_pesquisar"));
        return element;
    }

    //resultado da busca campo cep
    public static WebElement res_cep(WebDriver driver){
        element = driver.findElement(By.xpath("//*[@id=\"resultado-DNEC\"]/tbody/tr/td[4]"));
        return element;
    }

    //resultado da busca campo cep nao valido
    public static WebElement res_cep_invalido(WebDriver driver){
        element = driver.findElement(By.xpath("//*[@id=\"mensagem-resultado-alerta\"]/h6"));
        return element;
    }

}
