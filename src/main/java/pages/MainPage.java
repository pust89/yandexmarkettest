package pages;

import java.util.HashMap;

/**
 * Класс "Главная страница Яндекса"
 * Ничего интересного
 *
 * @author Пусвтоит В.
 */
public class MainPage extends AbstractPage {

    protected HashMap<String, String> setMapElements() {
        HashMap<String, String> map = new HashMap<String, String>();
        // Кнопка перехода на страницу яндекс маркета
        map.put("marketBtn", "//a[@data-id=\"market\"]");
        return map;
    }

    public MarketPage goToYandexMarket() {
        findAndClickMethodByKey("marketBtn");
        MarketPage marketPage = new MarketPage();
        return marketPage;
    }

}
