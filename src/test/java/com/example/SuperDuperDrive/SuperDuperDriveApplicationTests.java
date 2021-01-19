package com.example.SuperDuperDrive;

import com.example.SuperDuperDrive.model.Credential;
import com.example.SuperDuperDrive.model.Note;
import com.example.SuperDuperDrive.model.User;
import com.example.SuperDuperDrive.services.CredentialService;
import com.example.SuperDuperDrive.services.NoteService;
import com.example.SuperDuperDrive.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.openqa.selenium.WebDriver;
import org.springframework.core.annotation.Order;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperDuperDriveApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	NoteService noteService;

	@Autowired
	CredentialService credentialService;


	@LocalServerPort
	private int port;

	public static WebDriver driver;
	private LoginPageTest loginPageTest;
	private SignUpPageTest signupPageTest;
	private HomePageTest homePageTest;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		loginPageTest = new LoginPageTest(driver);
		signupPageTest = new SignUpPageTest(driver);
		homePageTest = new HomePageTest(driver);
	}

	@Order(1)
	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}


	@Order(2)
	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Order(3)
	@Test
	public void verifyNewUserAccess() throws InterruptedException {

		driver.get("http://localhost:" + this.port + "/signup");
		String username = "pika";
		String password = "pika";
		signupPageTest.submitSignUpForm("fn", "ln", username, password);

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		loginPageTest.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());


	}


	@Test
	public void verifyNoteCreate() throws InterruptedException {
		loginAsTestUser();
		String noteTitle = "Test Note 1";
		String noteDesc = "Note 1 desc";
		homePageTest.addNote(noteTitle, noteDesc,driver);
		Assertions.assertEquals(noteTitle, homePageTest.getFirstNoteTitle());
		Assertions.assertEquals(noteDesc, homePageTest.getFirstNoteDescription());
	}


	@Test
	public void verifyNoteEdit() throws InterruptedException {
		loginWithExistingUser();
		String noteTitle = "updated Title";
		String noteDesc = "updated Description";
		//driver.get("http://localhost:" + this.port + "/home");
		homePageTest.changeTabToNotes(driver);

		homePageTest.editNote(noteTitle, noteDesc,driver);
		Assertions.assertEquals(noteTitle, homePageTest.getFirstNoteTitle());
		Assertions.assertEquals(noteDesc, homePageTest.getFirstNoteDescription());

	}

	@Order(6)
	@Test
	public void verifyNoteDelete() throws InterruptedException {
		loginWithExistingUser();
		homePageTest.changeTabToNotes(driver);

		homePageTest.deleteNote(driver);
		assertThrows(NoSuchElementException.class, () -> {
			homePageTest.getFirstNoteTitle();
		});
	}

	@Order(7)
	@Test
	public void verifyCredentialCreate() throws InterruptedException {
		loginAsTestUser();
		homePageTest.changeTabToCreds(driver);
		String credUrl = "http://www.google.com";
		String credUser = "testUser1";
		String credPwd = "testPwd1";
		homePageTest.addCredential(credUrl, credUser, credPwd,driver);
		//Assertions.assertEquals(credUrl, homePageTest.getCredUrl());
		Assertions.assertEquals(credUser, homePageTest.getCredUsername());
		//Assertions.assertNotEquals(credPwd, homePageTest.getFirstCredPassword());
	}

	@Order(8)
	@Test
	public void verifyCredentialEdit() throws InterruptedException {
		loginAsUserWithCreds();
		homePageTest.changeTabToCreds(driver);
		String credUrl = "http://www.google.co.in";
		String credUser = homePageTest.getCredUsername() + "new" + "testUser123";
		String credPwd = homePageTest.getCredPassword() + "new" +"password123";
		homePageTest.editCredential(credUrl, credUser, credPwd,driver);
		Assertions.assertEquals(credUrl, homePageTest.getCredUrl());
		Assertions.assertEquals(credUser, homePageTest.getCredUsername());
	}

	@Order(9)
	@Test
	public void verifyCredentialDelete() {
		loginAsUserWithCreds();
		homePageTest.changeTabToCreds(driver);
		homePageTest.deleteCredential(driver);
		assertThrows(NoSuchElementException.class, () -> {
			homePageTest.getCredUsername();
		});

	}


	private User createTestUser() {
		String username = RandomStringUtils.randomAlphabetic(5);
		String password = RandomStringUtils.randomAlphabetic(5);
		User user = new User();
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setUsername(username);
		user.setPassword(password);
		userService. signupUser(user);
		// re-set password since service hashes it
		user.setPassword(password);
		return user;
	}

	private void loginWithExistingUser() {
		User user = createTestUser();
		Note note = new Note();
		note.setUserid(user.getUserid());
		note.setNoteTitle("sample title");
		note.setNoteDescription("sample description");
		noteService.createOrEditNote(note);
		loginAsTestUser(user);
	}

	private void loginAsTestUser(User... users) {
		User user;
		if (users.length == 0) {
			user = createTestUser();
		} else {
			user = users[0];
		}
		driver.get("http://localhost:" + this.port + "/login");
		loginPageTest.login(user.getUsername(), user.getPassword());
		driver.get("http://localhost:" + this.port + "/home");
	}

	private void loginAsUserWithCreds() {
		User user = createTestUser();
		Credential cred = new Credential();
		cred.setUrl("http://www.google.com");
		cred.setUsername("testUser1");
		cred.setDecryptedPassword("testPass1");
		cred.setUserid(user.getUserid());
		credentialService.createOrEditCredential(cred);

		loginAsTestUser(user);
	}

}
