package com.example.newww;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage {
    private WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    private By firstNameInput = By.id("user_first_name");
    private By lastNameInput = By.id("user_last_name");
    private By emailInput = By.id("user_email");
    private By passwordInput = By.id("user_password");
    private By passwordConfirmInput = By.id("user_password_confirmation");
    private By signUpButton = By.cssSelector("button");

    public String getPasswordErrorText() {
        return driver.findElement(By.cssSelector(".error")).getText();
    }
    public void enterFirstName(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void confirmPassword(String password) {
        driver.findElement(passwordConfirmInput).sendKeys(password);
    }

    public void clickSignUpButton() {
        driver.findElement(signUpButton).click();
    }
    public String checkDuplicateText() {
        return driver.findElement(By.cssSelector(".error")).getText();
    }
}
