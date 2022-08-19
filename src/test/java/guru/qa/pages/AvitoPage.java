package guru.qa.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AvitoPage {

    private final SelenideElement
            firstSectionInSubmenu = $(".rubricator-list-item-submenu-bQ0A4 .rubricator-list-item-item-WKnEv");

    private final ElementsCollection
            sectionsInSubmenu = $$(".rubricator-list-item-submenu-bQ0A4 .rubricator-list-item-item-WKnEv"),
            items = $$("div[data-marker='item']");

    public void findCategory(final String category) {
        $(byText(category)).click();
    }

    public void itemsSizeGreaterThan0() {
        items.shouldBe(sizeGreaterThan(0));
    }

    public void firstSectionContainsText(final String section) {
        firstSectionInSubmenu.shouldHave(text(section));
    }

    public void visibleSectionsContainsTexts(final List<String> sections) {
        sectionsInSubmenu.filter(visible)
                .shouldHave(texts(sections));
    }
}
