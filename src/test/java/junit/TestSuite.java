package junit;

import org.junit.*;
import pages.FiltersPage;
import pages.MainPage;
import pages.MarketPage;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestSuite {

    private static MainPage mainPage;
    private static final int expectedQuantityCardsOnPage = 12;

    private String minPrice;
    private String maxPrice;
    private String brendOne;
    private String brendTwo;

    static {
        System.setProperty("webdriver.gecko.driver", "C:\\share\\drivers\\geckodriver.exe");
    }

    public TestSuite(String minPrice, String maxPrice, String brendOne, String brendTwo) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.brendOne = brendOne;
        this.brendTwo = brendTwo;
    }

    @Parameterized.Parameters
    public static Collection<String[]> getTestData() {
        return Arrays.asList(new String[][]{
                //Структура массивов {мин. цена, макс. цена, бренд один, бренд два}
                {"20000", "25000", "Acer", "DELL"},
                {"19000", "23000", "ASUS", "Lenovo"}
        });
    }

    @Test
    public void tests() throws InterruptedException {
        String nameOfFirstCardOnPage;
        String infoAboutFirstCardOnPage;

        // 1. Открыть браузер FireFox и развернуть на весь экран.
        mainPage = new MainPage();
        mainPage.makeFullScreen();

        // 2. Зайти на yandex.ru.
        mainPage.load("https://www.yandex.ru/");

        // 3. Перейти в яндекс маркет.
        MarketPage marketPage = mainPage.goToYandexMarket();

        // 4. Выбрать раздел Компьютеры.
        marketPage.goToComputerCatalog();

        // 5. Выбрать раздел Планшеты
        marketPage.goToLapTopCatalog();

        //6. Зайти в расширенный поиск
        FiltersPage filtersPage = marketPage.goToFiltersPage();

        // 7. Задать параметр поиска от 20000 рублей.
        // почему-то при работе через метод findAndInputMethodByKey тест литит
        filtersPage.inputFieldElementByXpath("//*[@id=\"glf-pricefrom-var\"]", minPrice);

        // 8. Задать параметр поиска до 25000 рублей.
        // почему-то при работе через метод findAndInputMethodByKey тест литит
        filtersPage.inputFieldElementByXpath("//*[@id=\"glf-priceto-var\"]", maxPrice);

        // 9. Выбрать производителей Acer и DELL.
        // Будем выбирать через кнопку "Показать все" и ввод имени бренда в поле поиска
        filtersPage.findAndClickMethodByKey("showAllBrendsBtn");
        filtersPage.findAndInputMethodByKey("fieldFindBrend", brendOne);
        Thread.sleep(1000);
        filtersPage.findAndClickMethodByKey(brendOne);
        Thread.sleep(1000);
        filtersPage.findAndInputMethodByKey("fieldFindBrend", brendTwo);
        Thread.sleep(1000);
        filtersPage.findAndClickMethodByKey(brendTwo);
        Thread.sleep(1000);

        // 10. Нажать кнопку Применить.
        MarketPage filterMarketPage = filtersPage.applyFilters();

        // 11. Проверить, что элементов на странице 12.
        // В этом шаге мы не только проверяем количество элементов на странице, но и
        // сохраняем их в статик поле класса MarketPage  public static List<WebElement> listOfCards
        Assert.assertEquals(expectedQuantityCardsOnPage, filterMarketPage.quantityElementsOnPageByKey("infoCardsOnPage"));

        // 12. Запомнить первый элемент в списке.
        // Разбил на 2 задачи: 1)Запомнить название товара nameOfFirstCardOnPage 2) Запомнить информацию о товаре infoAboutFirstCardOnPage
        nameOfFirstCardOnPage = filterMarketPage.returnTextNameByKey("firstCardName");
        // Убираем слово "Планшет", для корректного поиска(если не убрать, попадем на страничку со списком всех устройств)
        if (nameOfFirstCardOnPage.contains("Планшет")) {
            nameOfFirstCardOnPage = nameOfFirstCardOnPage.substring(7, nameOfFirstCardOnPage.length());
        }

        infoAboutFirstCardOnPage = MarketPage.listOfCards.get(0).getText();
        System.out.println(MarketPage.listOfCards.get(0).getText());
        // Очищаем наш список карточек, для прогонки следующего теста
        MarketPage.listOfCards.clear();

        // 13. В поисковую строку ввести запомненное значение.
        filterMarketPage.findAndInputMethodByKey("searchField", nameOfFirstCardOnPage);
        filterMarketPage.findAndClickMethodByKey("searchBtn");

        // 14. Найти и проверить, что наименование товара соответствует запомненному.
        System.out.println(filterMarketPage.returnTextNameByKey("fullSpecByFirstCard"));
        Assert.assertTrue(filterMarketPage.returnTextNameByKey("fullSpecByFirstCard").toLowerCase().contains(infoAboutFirstCardOnPage.toLowerCase()));

    }

    @AfterClass
    public static void closeTest() {
        mainPage.closeDriver();
    }
}
