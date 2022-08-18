package guru.qa.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AvitoTest extends TestBase {

    // First Test
    @ValueSource(strings = {"Личные вещи", "Для дома и дачи"})
    @ParameterizedTest(name = "Результаты поиска не пустые для категории {0}")
    void searchResultsAreNotNull(final String category) {
        open("https://www.avito.ru");
        $(byText(category)).click();
        $$("div[data-marker='item']").shouldBe(sizeGreaterThan(0));
    }

    // Second Test
    @CsvSource(value = {"Личные вещи | Одежда, обувь, аксессуары",
            "Для дома и дачи | Ремонт и строительство"},
            delimiter = '|')
    @ParameterizedTest(name = "Результаты поиска содержат текст {1} для категории {0}")
    void categoriesContainsSection(final String category, final String section) {
        open("https://www.avito.ru");
        $(byText(category)).click();
        $(".rubricator-list-item-submenu-bQ0A4").$$(".rubricator-list-item-item-WKnEv")
                .first().shouldHave(text(section));
    }

    // Third Test
    @MethodSource("dataProviderForCategoriesContainsSections")
    @ParameterizedTest(name = "Категория {0} содержит разделы {1}")
    void categoriesContainsSections(final String category, final List<String> sections) {
        open("https://www.avito.ru");
        $(byText(category)).click();
        $$(".rubricator-list-item-submenu-bQ0A4 .rubricator-list-item-item-WKnEv")
                .filter(visible)
                .shouldHave(texts(sections));
    }

    static Stream<Arguments> dataProviderForCategoriesContainsSections() {
        return Stream.of(
                Arguments.of("Личные вещи", List.of(
                        "Одежда, обувь, аксессуары",
                        "Детская одежда и обувь",
                        "Товары для детей и игрушки",
                        "Красота и здоровье",
                        "Часы и украшения"
                )),
                Arguments.of("Для дома и дачи", List.of(
                        "Ремонт и строительство",
                        "Мебель и интерьер",
                        "Бытовая техника",
                        "Продукты питания",
                        "Растения",
                        "Посуда и товары для кухни"))
        );
    }
}
