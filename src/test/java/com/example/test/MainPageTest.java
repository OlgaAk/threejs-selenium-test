package com.example.test;

import org.junit.jupiter.api.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    private List<Integer> offsets = Arrays.asList(
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
    int count = 0;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/okokhan/Downloads/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://master--vibrant-swartz-f50769.netlify.app/");

        mainPage = new MainPage(driver);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
        }
    }

    @AfterEach
    public void tearDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object data = js.executeScript("return window.performance.getEntries();");
        System.out.println(data.toString());
        System.out.println(count);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
        }
        driver.quit();
    }


    @Test
    public void createSingleGeometryPolygon() {
        mainPage.singleGeometryPolygonTab.click();
        drawCoordinates(offsets);
        dragCoordinate(offsets);
    }

    @Test
    public void createMultiGeometryPolygon() {
        mainPage.multiGeometryPolygonTab.click();
        drawCoordinates(offsets);
        dragCoordinate(offsets);
    }

    private void dragCoordinate(List<Integer> offsets) {
        Actions actions = new Actions(driver)
                .moveToElement(mainPage.singleGeometryPolygonTab)
                .moveByOffset(-5, 105)
                .click()
                .clickAndHold();

        moveCursor(actions);
        actions.release().build().perform();
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
        int coordsMultiplyBy = 20;
        // add first point
        actions.moveByOffset(coordinates.get(0), coordinates.get(1)).click();
        for (int i = 0; i < coordinates.size(); i += 2) {
            addCoordinatesInBetween(i, coordinates, coordsMultiplyBy, actions);
        }
        actions.build().perform();
    }

    private void addCoordinatesInBetween(int i, List<Integer> coordinates, int coordsMultiplyBy, Actions actions) {
        if (i + 2 < coordinates.size()) {
            int x = coordinates.get(i + 2) / coordsMultiplyBy;
            int y = coordinates.get(i + 3) / coordsMultiplyBy;
            for (int j = 0; j < coordsMultiplyBy; j++) {
                actions.moveByOffset(x, y).click();
                count++;
            }
        }
    }

}
