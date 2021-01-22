package com.example.SuperDuperDrive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageTest {

    @FindBy(id="inputUsername")
    private WebElement userName;

    @FindBy(id="inputPassword")
    private WebElement password;

    @FindBy(xpath="//button[contains(text(),'Login')]")
    private WebElement button;

    public LoginPageTest(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void login(String userName, String password){
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);

        this.button.click();
    }
}
