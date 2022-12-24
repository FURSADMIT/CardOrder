package ru.netology.qa;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestCreditCard {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void cardOrderPositiveTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Коноров Петр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79282833545");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button'][type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());


    }

    @Test
    void cartOrderNameFormInvalidTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Konorov Petr");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79282833545");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button'][type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);

    }

    @Test
    void cardOrderFormPhoneFieldIsInvalidTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Коноров Петр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7928283354");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button'][type='button']")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void cardOrderFormNameFieldIsNullTest() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79282833545");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button'][type='button']")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void cardOrderFormPhoneFieldIsNullTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Коноров Петр");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button'][type='button']")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text);

    }

    @Test
    void cardOrderFormCheckboxDisableVisibleTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Коноров Петр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79282833545");
        driver.findElement(By.cssSelector("button[role='button'][type='button']")).click();

        ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")));

    }

    @Test
    void cardOrderFormCheckboxDisableAssertTextTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Коноров Петр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79282833545");
        driver.findElement(By.cssSelector("button[role='button'][type='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actual);

    }
}
