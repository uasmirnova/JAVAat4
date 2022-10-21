package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FormTest {

    String meetingDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String failFormatDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yy"));
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
        clearBrowserCookies();
    }

    @Test
    public void shouldPositiveTest() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = notification").shouldHave(Condition.exactText("Успешно! " + "Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(exist);
    }

    @Test
    public void shouldValidDate10() {
        String date = meetingDate(10);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = notification").shouldHave(Condition.exactText("Успешно! " + "Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(exist);
    }

    @Test
    public void shouldValidName1() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова-Задунайская Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = notification").shouldHave(Condition.exactText("Успешно! " + "Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(exist);
    }

    @Test //BUG
    public void shouldValidName2() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Ёлкина Алёна");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = notification").shouldHave(Condition.exactText("Успешно! " + "Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(exist);
    }

    @Test
    public void shouldInvalidCity1() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Серпухов");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = city].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldInvalidCity2() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("12345");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = city].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldEmptyCity() {
        String date = meetingDate(3);
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = city].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldInvalidDate1() {
        String date = meetingDate(2);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = date] .input__sub").shouldBe(visible).shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldInvalidDate2() {
        String fDate = failFormatDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(fDate);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = date] .input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"));
    }

    @Test
    public void shouldInvalidDate3() {
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue("00.00.0000");
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = date] .input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"));
    }

    @Test
    public void shouldEmptyDate() {
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = date] .input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"));
    }

    @Test
    public void shouldInvalidName1() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова5");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = name].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldInvalidName2() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Smirnova");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = name].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldInvalidName3() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова@ Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = name].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldEmptyName() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = phone] input").setValue("+79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = name].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldInvalidPhone1() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("79876543210");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = phone].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldInvalidPhone2() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+7987654321");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = phone].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldInvalidPhone3() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+798765432100");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = phone].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldEmptyPhone() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = phone].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldEmptyCheckbox() {
        String date = meetingDate(3);
        $("[data-test-id = city] input").setValue("Москва");
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = date] input").setValue(date);
        $("[data-test-id = name] input").setValue("Смирнова Юлия");
        $("[data-test-id = phone] input").setValue("+79876543210");
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id = agreement].input_invalid").shouldBe(visible).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
