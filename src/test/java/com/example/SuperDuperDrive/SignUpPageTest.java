package com.example.SuperDuperDrive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPageTest {

    @FindBy(id="inputFirstName")
    private WebElement firstNameField;

    @FindBy(id="inputLastName")
    private WebElement lastnameField;

    @FindBy(id="inputUsername")
    private WebElement usernameField;

    @FindBy(id="inputPassword")
    private WebElement passwordField;

    @FindBy(xpath="//button[contains(text(),'Sign Up')]")
    private WebElement submitButtonField;

    public SignUpPageTest(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void submitSignUpForm(String firstName, String lastName, String userName, String password) throws InterruptedException {
        this.firstNameField.sendKeys(firstName);
        this.lastnameField.sendKeys(lastName);
        this.usernameField.sendKeys(userName);
        this.passwordField.sendKeys(password);
        this.submitButtonField.click();

//        enterText(inputFirstname, firstname);
//        enterText(inputLastname, lastname);
//        enterText(inputUsername, username);
//        enterText(inputPassword, password);
//        click(buttonSignup);
//        wait(1);
    }

}
