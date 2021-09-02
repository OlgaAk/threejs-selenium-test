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

        try {
            Thread.sleep(4000);
        } catch (InterruptedException ie) {
        }
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
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
        }
    }

    @Test
    public void createMultiGeometryPolygon() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ie) {
        }
        mainPage.multiGeometryPolygonTab.click();
        drawCoordinates(offsets);
        dragCoordinate(offsets);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
        }
    }

    private void dragCoordinate(List<Integer> offsets) {
        Actions actions = new Actions(driver)
                .moveToElement(mainPage.singleGeometryPolygonTab)
                .moveByOffset(-5, 105)
                .click()
                .clickAndHold();

        moveCursor(actions);

        actions.release()
                .build()
                .perform();
    }

    private void moveCursor(Actions actions) {
        int xOffset = 300;
        int yOffset = 200;
        int times = 10;
        for (int i = 0; i < times; i++) {
            actions.moveByOffset(xOffset, 0)
                    .moveByOffset(-xOffset, yOffset)
                    .moveByOffset(xOffset, 0)
                    .moveByOffset(-xOffset, -yOffset);
        }
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

}
