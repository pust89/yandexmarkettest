package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import tools.DriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Класс "Абстрактная страница"
 * Содержит конструктор, инициализирующий драйвер
 * и методы пригодные для использования во всех дочерних классов
 *
 * @author Пусвтоит В.
 */
public abstract class AbstractPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Коллекция веб элементов страницы,
     * Структура <"Название веб элемента", "XPath путь">
     */
    protected HashMap<String, String> mapElements;

    public AbstractPage() {
        mapElements = setMapElements();
        driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);
        wait.withTimeout(1, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
    }

    /**
     * Абстрактный метод.
     * Служит для заполнение "базы" веб элементов страницы mapElements
     */
    protected abstract HashMap<String, String> setMapElements();


    public AbstractPage load(String url) {
        driver.navigate().to(url);
        return this;
    }

    public void makeFullScreen() {
        driver.manage().window().maximize();
    }

    /**
     * Метод находит веб элемент по XPath и кликает на него
     * Так же метод отлавливает исключение.
     *
     * @param xPath - XPath веб элемента.
     */
    public void clickOnElementByXpath(String xPath) {
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        btn.click();
    }

    /**
     * Метод находит веб элемент по ключу, получает Xpath из словаря mapElements и кликает на него
     * Так же метод отлавливает исключение.
     *
     * @param key - ключ к коллекции веб эдементов страницы mapElements
     */
    public void findAndClickMethodByKey(String key) {
        String xPath = mapElements.get(key);
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
            btn.click();
    }

    /**
     * Метод находит веб элемент(поле) по XPath, очишает его и вводит строковый параметр
     * Так же метод отлавливает исключение.
     *
     * @param xPath - XPath веб элемента.
     * @param text  - текст, который необходимо ввести.
     */
    public void inputFieldElementByXpath(String xPath, String text) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(xPath))));
        field.clear();
        field.sendKeys(text);
    }

    /**
     * Метод находит веб элемент(поле) по ключу, получает Xpath из словаря mapElements очишает его и вводит строковый параметр
     * Так же метод отлавливает исключение.
     *
     * @param key  - ключ к коллекции веб эдементов страницы mapElements
     * @param text - текст, который необходимо ввести.
     */
    public void findAndInputMethodByKey(String key, String text) {
        String xPath = mapElements.get(key);
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(xPath))));
        field.clear();
        field.sendKeys(text);
    }

    public void closeDriver() {
        driver.quit();
    }

}
