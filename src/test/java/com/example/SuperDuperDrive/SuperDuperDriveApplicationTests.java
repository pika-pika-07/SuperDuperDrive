package com.example.SuperDuperDrive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.openqa.selenium.WebDriver;
import org.springframework.core.annotation.Order;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperDuperDriveApplicationTests {


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
		verifyNewUserAccess();
		String noteTitle = "Test Note 1";
		String noteDesc = "Note 1 desc";
		homePageTest.addNote(noteTitle, noteDesc,driver);
		Assertions.assertEquals(noteTitle, homePageTest.getFirstNoteTitle());
		Assertions.assertEquals(noteDesc, homePageTest.getFirstNoteDescription());
	}

}
