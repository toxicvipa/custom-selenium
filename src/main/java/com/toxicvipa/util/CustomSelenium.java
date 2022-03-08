package com.toxicvipa.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CustomSelenium {

    private final String driverLocation;
    private List<WebDriver> browsers = new ArrayList<>();

    public CustomSelenium(String driverLocation) {
        this.driverLocation = driverLocation;
        //setting the driver executable
        System.setProperty("webdriver.gecko.driver", driverLocation);
    }

    public int createBrowser() {
        try {
            WebDriver driver = new FirefoxDriver();
            browsers.add(driver);
            Thread.sleep(5000);
            return browsers.size() - 1;
        } catch (Exception e) {
            throw new RuntimeException("Geckodriver not found: " + driverLocation);
        }
    }

    public void get(int id, String url) {
        if(browsers.size() <= id) {
            throw new RuntimeException("ID: " + id + ", browser with that ID does not exist!");
        }
        browsers.get(id).get(url);
        System.out.println("[" + id + "] Opened: " + url);
    }

    public void get(String url) {
        get(0, url);
    }

    public void closeBrowser(int id) {
        if(browsers.size() <= id) {
            throw new RuntimeException("ID: " + id + ", browser with that ID does not exist!");
        }
        browsers.get(id).quit();
    }

    public void closeBrowser() {
        closeBrowser(0);
    }

    public WebElement getElementByXpath(int id, String xpath) {
        WebDriverWait wait = new WebDriverWait(browsers.get(id), Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public WebElement getElementByXpath(String xpath) {
        return getElementByXpath(0, xpath);
    }

    public void clickElementByXpath(int id, String xpath) {
        getElementByXpath(id, xpath).click();
    }

    public void clickElementByXpath(String xpath) {
        clickElementByXpath(0, xpath);
    }


    public void writeElementByXpath(int id, String xpath, CharSequence... text) {
        getElementByXpath(id, xpath).sendKeys(text);
    }

    public void writeElementByXpath(String xpath, CharSequence... text) {
        writeElementByXpath(0, xpath, text);
    }

    public WebDriver getBrowser(int id) {
        if(browsers.size() <= id) {
            throw new RuntimeException("ID: " + id + ", browser with that ID does not exist!");
        }
        return browsers.get(id);
    }

    public WebDriver getBrowser() {
        return browsers.get(0);
    }
}