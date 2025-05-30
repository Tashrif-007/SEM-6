package com.example.newww;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver=driver;
        this.wait= new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void clickAddNewBoard() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add_new_board")));
        element.click();
    }
    public void enterBoardNameAndSubmit(String name) {
        WebElement input = driver.findElement(By.id("board_name"));
        input.sendKeys(name);
        input.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void enterBoardName(String name) {
        driver.findElement(By.id("board_name")).sendKeys(name);
    }
    public void signOut() {
        WebElement signOutButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#crawler-sign-out>span"))
        );
        signOutButton.click();
    }

    public void openBoardByCSS() {
        driver.findElement(By.cssSelector("#\\31-board1 > .inner")).click();
    }
    public void openBoard() {
        driver.findElement(By.cssSelector(".inner")).click();
    }
}
