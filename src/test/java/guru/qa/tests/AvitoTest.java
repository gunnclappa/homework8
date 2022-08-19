package guru.qa.tests;

import guru.qa.pages.AvitoPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

public class AvitoTest extends TestBase {

    AvitoPage avitoPage = new AvitoPage();

    // First Test
    @ValueSource(strings = {"Личные вещи", "Для дома и дачи"})
    @ParameterizedTest(name = "Результаты поиска не пустые для категории {0}")
    void searchResultsAreNotNull(final String category) {
        open("https://www.avito.ru");
        avitoPage.findCategory(category);
        avitoPage.itemsSizeGreaterThan0();
    }

    // Second Test
    @CsvSource(value = {"Личные вещи | Одежда, обувь, аксессуары",
            "Для дома и дачи | Ремонт и строительство"},
            delimiter = '|')
    @ParameterizedTest(name = "Результаты поиска содержат текст {1} для категории {0}")
    void categoriesContainsSection(final String category, final String section) {
        open("https://www.avito.ru");
        avitoPage.findCategory(category);
        avitoPage.firstSectionContainsText(section);
    }

    // Third Test
    @MethodSource("dataProviderForCategoriesContainsSections")
    @ParameterizedTest(name = "Категория {0} содержит разделы {1}")
    void categoriesContainsSections(final String category, final List<String> sections) {
        open("https://www.avito.ru");
        avitoPage.findCategory(category);
        avitoPage.visibleSectionsContainsTexts(sections);
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
