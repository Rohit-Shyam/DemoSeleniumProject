package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StepDefinitions {
    public static WebDriver driver;
    WebDriverWait wait;

    // Method to create a temporary directory for each test
    public String createUniqueUserDataDir() {
        // Create a unique folder name using UUID
        String uniqueDir = "/tmp/chrome-user-data-" + UUID.randomUUID().toString();
        Path path = Path.of(uniqueDir);
        try {
            // Create the directory
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uniqueDir;
    }

    // Method to set up ChromeDriver with the necessary ChromeOptions
    public WebDriver setupDriver() {
        // Create ChromeOptions and set arguments
        String userDataDir = createUniqueUserDataDir();  // Get unique user data directory
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--no-sandbox"); // Disable sandbox for CI/CD environments
        options.addArguments("--disable-dev-shm-usage"); // Avoid /dev/shm memory issues
        options.addArguments("--remote-debugging-port=9222"); // Enable debugging
        options.addArguments("--user-data-dir=" + userDataDir);  // Use the unique user data dir

        driver = new ChromeDriver(options);  // Initialize ChromeDriver with options
        driver.manage().window().maximize();  // Maximize the window
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return driver;
    }

    // === Task 1 ===
    @Given("I launch Wikipedia")
    public void launchWikipedia() {
        driver = setupDriver();
        driver.get("https://www.wikipedia.com");
    }

    @When("I search for {string}")
    public void searchWikipedia(String query) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchInput")));
        searchBox.sendKeys(query);
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("pure-button")));
        searchButton.click();
    }

    @Then("I should be on a Wikipedia page")
    public void verifyWikipediaPage() throws InterruptedException {
        Thread.sleep(2000);
        String title = driver.getTitle();
        Assert.assertTrue("Expected 'Wikipedia' in title but got: " + title, title.contains("Wikipedia"));
        Thread.sleep(1000);
        driver.quit();
        driver = null;
    }

    // === Task 2a ===
    @Given("I open ReactJS homepage")
    public void openReactHomepage() {
        driver = setupDriver();
        driver.get("https://react.dev/");
    }

    @When("I click the Learn React link")
    public void clickLearnReact() {
        WebElement learnReactLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Learn React")));
        learnReactLink.click();
    }

    @Then("The Quick Start section should be visible")
    public void verifyQuickStartSection() throws InterruptedException {
        Thread.sleep(2000);
        WebElement quickStart = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[normalize-space()='Quick Start']")));
        Assert.assertTrue("Quick Start section is not visible", quickStart.isDisplayed());
        Thread.sleep(1000);
        driver.quit();
        driver = null;
    }

    // === Task 2b ===
    @Given("I open IMDb homepage")
    public void openIMDbHomepage() {
        driver = setupDriver();
        driver.get("https://www.imdb.com");
    }

    @Then("The IMDb title should match expected")
    public void verifyIMDbTitle() throws InterruptedException {
        Thread.sleep(2000);
        String title = driver.getTitle();
        Assert.assertTrue("Expected 'Inception' in title but got: " + title, title.contains("IMDb"));
    }

    @And("IMDb search box should be visible")
    public void verifyIMDbSearchBox() {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("suggestion-search")));
        Assert.assertTrue(searchBox.isDisplayed());
    }

    @When("I search for the movie Inception on IMDb")
    public void searchMovieOnIMDb() throws InterruptedException {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("suggestion-search")));
        searchBox.sendKeys("Inception");
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("suggestion-search-button")));
        searchBtn.click();
        Thread.sleep(2000);
        driver.quit();
        driver = null;
    }

    // === Task 3 ===
    @Given("I open Wikipedia homepage")
    public void i_open_wikipedia_homepage() {
        driver = setupDriver();
        driver.get("https://www.wikipedia.org/");
    }

    @Then("Wikipedia logo and search box should be visible")
    public void wikipedia_logo_and_search_box_should_be_visible() throws InterruptedException {
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("central-featured-logo")));
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
        Assert.assertTrue(logo.isDisplayed());
        Assert.assertTrue(searchBox.isDisplayed());
        Thread.sleep(1000);
    }

    @When("I search for {string} on Wikipedia")
    public void i_search_for_on_wikipedia(String keyword) throws InterruptedException {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        driver.quit();
        driver = null;
    }

    // === Task 4 ===
    @Given("I perform login tests using CSV data")
    public void performCSVLoginTests() throws InterruptedException {
        List<String[]> testData = readTestData();
        driver = setupDriver();

        for (String[] data : testData) {
            String username = data[0];
            String password = data[1];

            driver.get("https://www.saucedemo.com/");
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("login-button"));

            usernameField.clear();
            passwordField.clear();
            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            loginButton.click();

            Thread.sleep(1000);
            if (driver.getTitle().contains("Swag Labs")) {
                System.out.println("Login test passed for " + username);
            } else {
                System.out.println("Login test failed for " + username);
            }
        }

        Thread.sleep(1000);
        driver.quit();
        driver = null;
    }

    public List<String[]> readTestData() {
        List<String[]> testData = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/data.csv"))) {
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                testData.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testData;
    }

    // === Task 5 ===
    @Given("I open SauceDemo login page")
    public void openSauceDemoLoginPage() {
        driver = setupDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @When("I login with username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.clear();
        passwordField.clear();
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    @Then("I should see the products page")
    public void verifyProductsPage() throws InterruptedException {
        Thread.sleep(1000);
        String title = driver.getTitle();
        Assert.assertTrue("Expected 'Swag Labs' in title but got: " + title, title.contains("Swag Labs"));
        driver.quit();
        driver = null;
    }

    @Then("I should see error {string}")
    public void verifyErrorMessage(String expectedMessage) throws InterruptedException {
        Thread.sleep(1000);
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        Assert.assertTrue("Expected error message not found", errorMessage.getText().contains(expectedMessage));
        driver.quit();
        driver = null;
    }

    // === Task 6 ===
    @Given("I open SauceDemo Task6 login page")
    public void openSauceDemoTask6LoginPage() {
        driver = setupDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @When("I enter Task6 username {string} and password {string}")
    public void enterTask6Credentials(String username, String password) {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.clear();
        passwordField.clear();
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    @Then("I should successfully login for Task6")
    public void verifyTask6LoginSuccess() {
        try {
            Assert.assertTrue("Expected Swag Labs in title", driver.getTitle().contains("Swag Labs"));
            System.out.println("✔ Task6: Login success!");
        } catch (AssertionError e) {
            System.out.println("✘ Task6: Login failed.");
            throw e;
        }

        driver.quit();
        driver = null;
    }
}
