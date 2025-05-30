package com.example.newww;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class CardPage {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;
    private By commentTextArea = By.cssSelector("textarea");
    private By submitCommentButton = By.cssSelector("button");
    private By commentText = By.cssSelector(".text");

    public CardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAddCardLink() {
        WebElement addCard = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add a new card...")));
        addCard.click();
    }

    public void enterCardName(String name) {
        WebElement cardName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("card_name")));
        cardName.sendKeys(name);
    }

    public void clickEditCard() {
        WebElement editLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Edit")));
        editLink.click();
    }

    public void openOptionsMenu() {
        driver.findElement(By.cssSelector(".button:nth-child(2) > span")).click();
    }

    public void hoverOverOptions() {
        WebElement element = driver.findElement(By.cssSelector(".button:nth-child(2) > span"));
        actions.moveToElement(element).perform();
    }

    public void moveToTopLeft() {
        WebElement body = driver.findElement(By.tagName("body"));
        actions.moveToElement(body, 0, 0).perform();
    }

    public void clickSecondMemberInList() {
        driver.findElement(By.cssSelector("ul:nth-child(2) > li:nth-child(2) span:nth-child(2)")).click();
    }

    public boolean hasCardMembers() {
        List<WebElement> elements = driver.findElements(By.cssSelector(".card-members > .react-gravatar"));
        return elements.size() > 0;
    }
    public void addComment(String comment) {
        driver.findElement(commentTextArea).click();
        driver.findElement(commentTextArea).sendKeys(comment);
        driver.findElement(submitCommentButton).click();

        // Optional hover actions to simulate UI behavior
        WebElement element = driver.findElement(submitCommentButton);
        Actions builder = new Actions(driver);
        builder.moveToElement(element).perform();

        WebElement body = driver.findElement(By.tagName("body"));
        builder.moveToElement(body, 0, 0).perform();
    }

    public String getCommentText() {
        return driver.findElement(commentText).getText();
    }

    public void setCardTitle(String title) {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input")));
        inputField.clear();
        inputField.sendKeys(title);
    }

    public void setCardDescription(String description) {
        WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea:nth-child(2)")));
        textarea.clear();
        textarea.sendKeys(description);
    }

    public void saveCardChanges() {
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button:nth-child(3)")));
        saveButton.click();
    }

    public void submitCard() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button")));
        submitButton.click();
    }

    public void clickCardContent() {
        WebElement cardContent = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".card-content")));
        cardContent.click();
    }

    public void enterCardDescription(String text) {
        WebElement textarea = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("textarea")));
        textarea.click();
        textarea.sendKeys(text);
    }

    public void saveCardDescription() {
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button")));
        saveButton.click();
    }

    public void clickDeleteCardButton() {
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fa-trash-o")));
        deleteButton.click();
    }
    public void selectTag() {
        WebElement tagButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button:nth-child(3) > span")));
        tagButton.click();
    }
    public void clickTagColor() {
        driver.findElement(By.cssSelector(".green")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".tag:nth-child(2)"));
            assert(!elements.isEmpty());
        }
    }
}
