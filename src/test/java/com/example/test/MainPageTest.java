package com.example.test;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    private List<Integer> offsets  = Arrays.asList(
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

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/okokhan/Downloads/chromedriver_win32/chromedriver.exe");
        var options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);

        driver = new ChromeDriver(options);
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
        getPerformanceLogs();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
        }
        driver.quit();
    }

    private void getPerformanceLogs() {
        List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
        writeToJson(entries);
    }

    private void writeToJson(List<LogEntry> logs) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File("./performance-logs.json"), logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        int coordsMultiplyBy = 6;
        for (int i = 0; i < coordinates.size(); i += 2) {
            actions
                    .moveByOffset(coordinates.get(i), coordinates.get(i + 1))
                    .click();
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
            }
        }
    }

}
