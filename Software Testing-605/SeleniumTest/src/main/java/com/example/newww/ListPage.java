package com.example.newww;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterListName(String listName) {
        WebElement listNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("list_name")));
        listNameInput.sendKeys(listName);
    }

    public void clickCreateListButton() {
        driver.findElement(By.cssSelector("button")).click();
    }

    public String getFirstListTitle() {
        return driver.findElement(By.cssSelector("h4")).getText();
    }
}