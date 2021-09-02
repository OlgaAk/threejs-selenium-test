package com.example.test;

import org.junit.jupiter.api.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    private List<Integer> offsets;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/okokhan/Downloads/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://master--vibrant-swartz-f50769.netlify.app/");

        mainPage = new MainPage(driver);
        offsets = Arrays.asList(
                50, 125,
                100, 0,
                100, 20,
                100, 50,
                0, 100,
                -50, 100,
                -50, 0,
                -10, -10,
                -100, -50,
                -100, -50,
                -10, -100
        );
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void createSingleGeometryPolygon() {
        mainPage.singleGeometryPolygonTab.click();
        drawCoordinates(offsets);
        dragCoordinate(offsets);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
        }
    }

    private void dragCoordinate(List<Integer> offsets) {
        new Actions(driver)
                .moveToElement(mainPage.singleGeometryPolygonTab)
                .moveByOffset(-5, 105)
                .click()
                .clickAndHold()
                .moveByOffset(300, 0)
                .moveByOffset(-200, 200)
                .moveByOffset(-100, -200)
                .release()
                .build()
                .perform();
    }

    private void drawCoordinates(List<Integer> coordinates) {
        Actions actions = new Actions(driver);
        for (int i = 0; i < coordinates.size(); i += 2) {
            actions
                    .moveByOffset(coordinates.get(i), coordinates.get(i + 1))
                    .click();
        }
        actions.build().perform();
    }


    @Test
    public void createMultiGeometryPolygon() {
        mainPage.multiGeometryPolygonTab.click();
        drawCoordinates(offsets);
        dragCoordinate(offsets);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
        }
    }


}
