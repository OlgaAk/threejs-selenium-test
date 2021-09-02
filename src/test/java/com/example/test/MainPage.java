package com.example.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    @FindBy(id = "singleGeometryPolygon")
    public WebElement singleGeometryPolygonTab;

//    @FindBy(xpath = "//div[contains(@class, 'menu-main__item') and text() = 'Developer Tools']")

    @FindBy(id = "multiGeometryPolygon")
    public WebElement multiGeometryPolygonTab;


    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
