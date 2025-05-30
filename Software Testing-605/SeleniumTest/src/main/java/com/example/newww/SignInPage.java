package com.example.newww;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class SignInPage {
    private WebDriver driver;
    private By emailField = By.id("user_email");
    private By passwordField = By.id("user_password");
    private By signInButton = By.cssSelector("button");
    private By errorMessage = By.cssSelector(".error");

    public SignInPage(WebDriver driver) {
        this.driver = driver;
        open();  // Open and set window size during initialization
    }

    public void open() {
        driver.get("http://localhost:4000/sign_in");
        driver.manage().window().setSize(new Dimension(550, 702));
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).click();
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).click();
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickSignInButton() {
        driver.findElement(signInButton).click();
    }

    public void signIn(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignInButton();
    }

    public String getErrorMessage() {
        return driver.findElement(By.cssSelector(".error")).getText();
    }
}
