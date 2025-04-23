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
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StepDefinitions {
    public static WebDriver driver;
    WebDriverWait wait;

    // === Task 1 ===
    @Given("I launch Wikipedia")
    public void launchWikipedia() {
        // Set up ChromeOptions to avoid conflicts with user data
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=/tmp/chrome-user-data"); // Set unique user data dir

        driver = new ChromeDriver(options);  // Use ChromeOptions
        driver.manage().window().maximize();
        driver.get("https://www.wikipedia.com");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        Thread.sleep(1000);
        driver = null;
    }

    // === Task 2a ===
    @Given("I open ReactJS homepage")
    public void openReactHomepage() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=/tmp/chrome-user-data"); // Set unique user data dir

        driver = new ChromeDriver(options);  // Use ChromeOptions
        driver.manage().window().maximize();
        driver.get("https://react.dev/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        Thread.sleep(1000);
        driver = null;
    }

    // === Task 2b ===
    @Given("I open IMDb homepage")
    public void openIMDbHomepage() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=/tmp/chrome-user-data"); // Set unique user data dir

        driver = new ChromeDriver(options);  // Use ChromeOptions
        driver.manage().window().maximize();
        driver.get("https://www.imdb.com");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        Thread.sleep(1000);
        driver = null;
    }

    // === Task 3 ===
    @Given("I open Wikipedia homepage")
    public void i_open_wikipedia_homepage() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=/tmp/chrome-user-data"); // Set unique user data dir

        driver = new ChromeDriver(options);  // Use ChromeOptions
        driver.manage().window().maximize();
        driver.get("https://www.wikipedia.org/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        Thread.sleep(1000);
        driver = null;
    }

    // === Task 4 ===
    @Given("I perform login tests using CSV data")
    public void performCSVLoginTests() throws InterruptedException {
        List<String[]> testData = readTestData();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=/tmp/chrome-user-data"); // Set unique user data dir

        driver = new ChromeDriver(options);  // Use ChromeOptions
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
        Thread.sleep(1000);
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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=/tmp/chrome-user-data"); // Set unique user data dir

        driver = new ChromeDriver(options);  // Use ChromeOptions
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=/tmp/chrome-user-data"); // Set unique user data dir

        driver = new ChromeDriver(options);  // Use ChromeOptions
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("I should see Task6 error message {string}")
    public void verifyTask6ErrorMessage(String expectedMessage) {
        try {
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
            String actual = errorMsg.getText();
            Assert.assertTrue("Expected message not found", actual.contains(expectedMessage));
            System.out.println("✔ Task6: Correct error message displayed.");
        } catch (Exception e) {
            System.out.println("✘ Task6: Error message not found or mismatch.");
            Assert.fail("Expected error message not found");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        driver = null;
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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        driver = null;
    }
}
