package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;

public class MarketPage extends AbstractPage {

    // список информационных карточек на странице
    public static List<WebElement> listOfCards;

    protected HashMap<String, String> setMapElements() {
        HashMap<String, String> map = new HashMap<String, String>();

        // Для различных видов каталога "Компьютеры"
        map.put("computerBtn", "//a[@href=\"/catalog/54425?hid=91009&track=menu\"]");
        map.put("bigCatalogBtn", "//button[@class=\"button n-topmenu-new-vertical__popup-show button_size_m" +
                " button_theme_normal button_arrow_down i-bem button_js_inited _popup2-destructor" +
                " _popup2-destructor_js_inited\"]");
        map.put("computerTechnBtn", "//a[@href=\"/catalog/54425?track=menu\"]");

        // Подкаталоги
        map.put("Планшеты", "//a[@href=\"/catalog/54545/list?hid=6427100&track=fr_ctlg\"]");

        //Кнопка перехода к расширенному поиску
        map.put("goToFiltersPage","//a[text()=\"Перейти ко всем фильтрам\"]");

        //Поле поиска и кнопка
        map.put("searchField", "//input[@id = \"header-search\"]");
        map.put("searchBtn", "//span[@class = \"search2__button\"]");

        //Элементы для сравнения и поиска
        map.put("firstCardName", "/html/body/div[1]/div[4]/div[2]/div[1]/div[2]/div/div[1]/div[1]/div[4]/div[1]/div/a");
        map.put("infoCardsOnPage", "//div[@class = \"n-snippet-card2__content\"]");
        map.put("fullSpecByFirstCard", "//ul[@class=\"n-product-spec-list\"]");
        return map;
    }


    /**
     * Метод находит веб элементы, добавляет их в список
     * и возвращает его.
     * Так же метод отлавливает исключение.
     *
     * @param key - ключ к коллекции веб эдементов страницы mapElements
     * @return возвращает список веб элементов.
     */
    private List<WebElement> addCardsToList(String key) {
        listOfCards = driver.findElements(By.xpath(mapElements.get(key)));
        return listOfCards;
    }

    // Метод возвращает количество элементов на странице
    public int quantityElementsOnPageByKey(String key) {
        addCardsToList(key);
        return listOfCards.size();
    }

    // Метод возращает текст вебэлемента по ключу
    public String returnTextNameByKey(String key) {
        String xPath = mapElements.get(key);
        return driver.findElement(By.xpath(xPath)).getText();
    }

    // Метод перехода в каталог "Компьютеры"
    public void goToComputerCatalog() {
        try {
            findAndClickMethodByKey("computerBtn");
        } catch (Exception ex1) {
            try {
                findAndClickMethodByKey("bigCatalogBtn");
                findAndClickMethodByKey("computerTechnBtn");
            } catch (Exception ex2) {
                findAndClickMethodByKey("computerTechnBtn");
            }
        }
    }

    // Метод перехода в каталог "Планшеты"
    public void goToLapTopCatalog() {
        findAndClickMethodByKey("Планшеты");
    }

    // Метод перехода в расширенный поиск
    public FiltersPage goToFiltersPage(){
        FiltersPage filtersPage = new FiltersPage();
        findAndClickMethodByKey("goToFiltersPage");
        return filtersPage;
    }

}
