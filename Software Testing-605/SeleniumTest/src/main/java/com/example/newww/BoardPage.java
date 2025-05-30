package com.example.newww;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class BoardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public BoardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10-second wait
    }

    public void openBoardById(String boardId) {
        // Properly escape CSS ID and wait for the element to be clickable
        String cssSelector = "#\\3" + boardId.charAt(0) + boardId.substring(1) + " > .inner";

        WebElement boardElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector))
        );

        boardElement.click();
    }
    public void addCollaboratorByEmail(String email) {
        driver.findElement(By.cssSelector(".fa-plus")).click(); // or use better locator
        WebElement emailInput = driver.findElement(By.id("crawljax_member_email"));
        emailInput.click();
        emailInput.sendKeys(Keys.DOWN);
        emailInput.sendKeys(email);
        driver.findElement(By.cssSelector("button")).click();
    }

    public String getCollaboratorErrorMessage() {
        return driver.findElement(By.cssSelector(".error")).getText();
    }
    public void clickUpdateName() {
        driver.findElement(By.cssSelector("h4")).click();
    }
    public void enterListName(String listName) {
        driver.findElement(By.id("list_name")).sendKeys(listName);
    }
    public void clickUpdate() {
        driver.findElement(By.cssSelector("button")).click();
    }
    public String checkUpdatedName() {
        return driver.findElement(By.cssSelector("h4")).getText();
    }
}
