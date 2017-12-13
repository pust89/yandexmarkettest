package pages;

import java.util.HashMap;

public class FiltersPage extends AbstractPage {
    @Override
    protected HashMap<String, String> setMapElements() {
        HashMap<String, String> map = new HashMap<String, String>();

        // Поля ввода минимальной и максимальной цены.
        map.put("fieldMin", "//*[@id=\"glf-pricefrom-var\"]");
        map.put("fieldMax", "//*[@id=\"glf-priceto-var\"]");

        // Кнопка показать все бренды
        map.put("showAllBrendsBtn", "/html/body/div[1]/div[4]/div/div[1]/div[1]/div[2]/div[2]/div/div[2]/button");

        // Поле поиска брендов
        map.put("fieldFindBrend", "/html/body/div[1]/div[4]/div/div[1]/div[1]/div[2]/div[2]/div/span/span/input");

        // Чекбоксы брендов
        map.put("Acer", "//label[@for=\"glf-7893318-267101\"]");
        map.put("DELL", "//label[@for=\"glf-7893318-153080\"]");
        map.put("ASUS", "//label[@for=\"glf-7893318-152863\"]");
        map.put("Lenovo", "//label[@for=\"glf-7893318-152981\"]");

        // Кнопка применить (фильтры)
        map.put("applyBtn", "/html/body/div[1]/div[4]/div/div[1]//a[2]");
        return map;
    }
        public MarketPage applyFilters(){
            MarketPage filterMarketPage = new MarketPage();
            clickOnElementByXpath("/html/body/div[1]/div[4]/div/div[1]//a[2]");
            return filterMarketPage;
        }
}
