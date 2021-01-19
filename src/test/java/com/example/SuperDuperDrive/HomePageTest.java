package com.example.SuperDuperDrive;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePageTest {

    public HomePageTest(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="nav-notes-tab")
    private WebElement tabNavNotes;

    @FindBy(id="nav-credentials-tab")
    private WebElement tabNavCredentials;

//    @FindBy(xpath="//button[.='Logout']")
//    private WebElement buttonLogout;

    @FindBy(id="logout")
    private WebElement buttonLogout;

    // files

    @FindBy(id="fileUpload")
    private WebElement inputFileUpload;

    @FindBy(id="fileUploadButton")
    private WebElement buttonFileUpload;

    @FindBy(css="[data-id='fileNames']")
    private WebElement fileNames;

    //notes

    @FindBy(id="buttonAddNewNote")
    private WebElement buttonAddNewNote;

//    @FindBy(css="#notesTable tbody tr td:nth-of-type(1)")
//    private WebElement noteTitles;

    @FindBy(id="noteTitle")
    private WebElement noteTitles;

//    @FindBy(css="#notesTable tbody tr td:nth-of-type(2)")
//    private WebElement noteDescriptions;


    @FindBy(id="noteDescription")
    private WebElement noteDescriptions;

    @FindBy(id="editNote")
    private WebElement buttonNoteEdit;

    @FindBy(id="noteDelete")
    private WebElement buttonNoteDelete;


    @FindBy(id="noteModalLabel")
    private WebElement headerNoteModal;

    @FindBy(id="note-title")
    private WebElement inputNoteTitle;

    @FindBy(id="note-description")
    private WebElement inputNoteDescription;

    @FindBy(id="noteModalSaveChanges")
    private WebElement buttonNoteSaveChanges;

    // credentials

    @FindBy(id="buttonAddNewCred")
    private WebElement  buttonAddNewCred;

    @FindBy(id="editCred")
    private WebElement buttonEditCred;

    @FindBy(css="[data-id='deleteCred']")
    private WebElement buttonDeleteCred ;

    @FindBy(css="[data-id='credentialUrl']")
    private WebElement credUrls;

    @FindBy(css="[data-id='credentialUsername']")
    private WebElement credUsernames;

    @FindBy(css="[data-id='credentialPassword']")
    private WebElement credPasswords;


    @FindBy(id="credentialModalLabel")
    private WebElement headerCredModal;

    @FindBy(id="credential-url")
    private WebElement inputCredUrl;

    @FindBy(id="credential-username")
    private WebElement inputCredUser;

    @FindBy(id="credential-password")
    private WebElement inputCredPwd;

    @FindBy(id="credModalSaveChanges")
    private WebElement buttonCredSaveChanges;

    @FindBy(css="[data-id='changeSuccessLink']")
    private WebElement linkChangeSuccess;


    public void addNote(String title, String description, WebDriver driver) {

       // WebDriverWait wait = new WebDriverWait(driver, 1);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // switch to Tab Nav notes
        wait.until(ExpectedConditions.elementToBeClickable(this.tabNavNotes));
        this.tabNavNotes.click();
        wait.until(ExpectedConditions.visibilityOf(this.tabNavNotes));

        // Click on add new Note button

        wait.until(ExpectedConditions.elementToBeClickable(this.buttonAddNewNote));
        this.buttonAddNewNote.click();


        // set title and description to note modal
        wait.until(ExpectedConditions.visibilityOf(this.headerNoteModal));
        this.inputNoteTitle.sendKeys(title);
        this.inputNoteDescription.sendKeys(description);

        // Submit Note Modal
        wait.until(ExpectedConditions.elementToBeClickable(this.buttonNoteSaveChanges));
        this.buttonNoteSaveChanges.click();
      //  wait.until(ExpectedConditions.invisibilityOf(this.buttonAddNewNote));

        // Success Link
        wait.until(ExpectedConditions.visibilityOf(this.linkChangeSuccess));
        this.linkChangeSuccess.click();


        //wait(1);
        try {
            Thread.sleep(1 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

      //  wait.until(ExpectedConditions.elementToBeClickable(this.buttonLogout));
        //wait.until(ExpectedConditions.elementToBeClickable(this.tabNavNotes));
        //this.tabNavNotes.click();

       // wait.until(ExpectedConditions.elementToBeClickable(this.buttonAddNewNote));

    }

    public void editNote(String title, String description, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 1);


        wait.until(ExpectedConditions.elementToBeClickable(this.buttonNoteEdit));
        this.buttonNoteEdit.click();

        wait.until(ExpectedConditions.visibilityOf(this.headerNoteModal));
        this.inputNoteTitle.clear();
        this.inputNoteDescription.clear();
        try {
            Thread.sleep(1 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.inputNoteTitle.sendKeys(title);
        this.inputNoteDescription.sendKeys(description);


        wait.until(ExpectedConditions.elementToBeClickable(this.buttonNoteSaveChanges));
        this.buttonNoteSaveChanges.click();
      //  wait.until(ExpectedConditions.invisibilityOf(this.buttonAddNewNote));


        wait.until(ExpectedConditions.visibilityOf(this.linkChangeSuccess));
        this.linkChangeSuccess.click();


        //wait(1);
        try {
            Thread.sleep(5 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
//
    public void deleteNote(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 1);

        wait.until(ExpectedConditions.elementToBeClickable(this.buttonNoteDelete));
        this.buttonNoteDelete.click();
      //  wait.until(ExpectedConditions.invisibilityOf(this.buttonAddNewNote));
        try {
            Thread.sleep(1 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOf(this.linkChangeSuccess));
        this.linkChangeSuccess.click();
        try {
            Thread.sleep(1 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addCredential(String url, String username, String password, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 1);
//        wait.until(ExpectedConditions.elementToBeClickable(this.tabNavCredentials));
//        this.tabNavCredentials.click();

        wait.until(ExpectedConditions.elementToBeClickable(this.buttonAddNewCred));
        this.buttonAddNewCred.click();

        wait.until(ExpectedConditions.visibilityOf(this.headerCredModal));

        this.inputCredUrl.sendKeys(url);
        this.inputCredUser.sendKeys(username);
        this.inputCredPwd.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(this.buttonCredSaveChanges));
        this.buttonCredSaveChanges.click();

        try {
            Thread.sleep(1 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
      //  wait.until(ExpectedConditions.visibilityOf(this.linkChangeSuccess));
        wait.until(ExpectedConditions.elementToBeClickable(this.linkChangeSuccess));
        this.linkChangeSuccess.click();

        try {
            Thread.sleep(2 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editCredential(String url, String username, String password, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 1);
//        wait.until(ExpectedConditions.elementToBeClickable(this.tabNavCredentials));
//        this.tabNavCredentials.click();
        try {
            Thread.sleep(1 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.elementToBeClickable(this.buttonEditCred));
        this.buttonEditCred.click();

        wait.until(ExpectedConditions.visibilityOf(this.headerCredModal));

        this.inputCredUrl.clear();
        this.inputCredUser.clear();
        this.inputCredUrl.sendKeys(url);
        this.inputCredUser.sendKeys(username);
        this.inputCredPwd.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(this.buttonCredSaveChanges));
        this.buttonCredSaveChanges.click();

        try {
            Thread.sleep(1 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  wait.until(ExpectedConditions.visibilityOf(this.linkChangeSuccess));
        wait.until(ExpectedConditions.elementToBeClickable(this.linkChangeSuccess));
        this.linkChangeSuccess.click();

        try {
            Thread.sleep(2 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void changeTabToNotes(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(this.tabNavNotes));
        this.tabNavNotes.click();
        wait.until(ExpectedConditions.visibilityOf(this.tabNavNotes));
      //  wait.until(ExpectedConditions.elementToBeClickable(this.buttonAddNewNote));

    }

    public void changeTabToCreds(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(this.tabNavCredentials));
        this.tabNavCredentials.click();
        wait.until(ExpectedConditions.visibilityOf(this.tabNavCredentials));
        //  wait.until(ExpectedConditions.elementToBeClickable(this.buttonAddNewNote));

    }


    public String getFirstNoteTitle() {
        System.out.println(this.noteTitles);
        return this.noteTitles.getText();

    }

    public String getFirstNoteDescription() {

        return this.noteDescriptions.getText();
    }
    public String getCredUrl() {
        return this.credUrls.getText();
    }

    public String getCredUsername() {
        return this.credUsernames.getText();
    }

    public String getCredPassword() {
        return this.credPasswords.getText();
    }


//    public int getCredentialCount() {
//        return this.credUrls.size();
//    }





}
