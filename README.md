# 🤖 Selenium Automation Framework

A robust end-to-end **Web UI Testing** project built using **Selenium WebDriver**, **JUnit**, and **Cucumber** for BDD. The project automates testing across multiple websites like Wikipedia, ReactJS, IMDb, and SauceDemo with integrated reporting and CI/CD support.

---

## 📁 Project Structure

```bash
DemoSeleniumProject/
├── pom.xml
├── testrunner/
│ └── TestRunner.java
├── stepdefinitions/
│ └── StepDefinitions.java
├── src/
│ └── test/
│ ├── resources/
│ │ ├── features/
│ │ │ └── combined.feature
│ │ └── data.csv
│ └── cucumber.properties
├── target/
│ └── cucumber-reports.html (generated)
├── screenshots/
│ └── *.png
```


---

## 🚀 Technologies Used

- **Java** 🟨
- **Selenium WebDriver** 🌐
- **JUnit** ✅
- **Cucumber** 🍀
- **Maven** 📦
- **ChromeDriver** 🧪
- **GitHub Actions / Jenkins** 🔄
- **CSV File Handling** 📑

---

## 🔧 Setup & Installation

1. **Clone the repo:**

   ```bash
   git clone https://github.com/your-username/DemoSeleniumProject.git
   cd DemoSeleniumProject
   ```

2. **Install dependencies (via Maven):**

   ```bash
   mvn clean install
   ```

3. **Make sure ChromeDriver is in your system PATH or update the driver path in code.**

---

## 🧪 Usage

1. **Run from IntelliJ IDEA using the TestRunner.java file.**

2. **Run with Maven:**
  
  ```bash
   mvn test
  ``` 


3. **Reports: After test execution, find your HTML/JSON/XML reports in the target/ directory.**

---

## 🎯 Test Scenarios

The project tests the following:

- ### ✅ Wikipedia:
    - Open page and search functionality

    - Logo and search box validation

- ### ✅ ReactJS:
    - Navigate to homepage

    - Click on "Learn React"

    - Validate visibility of "Quick Start" section

- ### ✅ IMDb:
    - Homepage title validation

    - Search movie "Inception"

- ### ✅ SauceDemo:
    - Login validations with valid and invalid credentials

    - Data-driven login test using data.csv

    - Error message validations for edge test cases

---

## 📊 Reporting
HTML Report – target/cucumber-reports.html

JSON Report – target/cucumber.json

JUnit Report – target/cucumber.xml

Reports compatible with CI/CD tools like Jenkins and GitHub Actions.

---

## 📂 Sample Data

Located in:

```bash
   src/test/resources/data.csv
```

Example:

```bash
  username,password
  standard_user,secret_sauce
  locked_out_user,wrong_password
  problem_user,secret_sauce
```
---

## 🌐 CI/CD Integration

Designed to integrate with GitHub Actions or Jenkins.

maven-surefire-plugin and maven-cucumber-reporting used for test automation and reporting.

---

## 🤝 Contributing

1. Fork this repository 🍴

2. Create a new branch feature/your-feature 🌱

3. Commit your changes ✅

4. Push and open a PR 🔁

---

## 📸 Visual Demo

📷 Screenshots of IDE setup, test execution, and generated HTML reports can be found in the screenshots/ folder.

---

💬 Have questions or suggestions? Drop an issue or connect on [LinkedIn](https://www.linkedin.com/in/rohit-shyam-598029184/).


---

### ✅ Next Steps:

- Replace `your-username` and LinkedIn URL in the README with your actual profile info.
- Push your updated `README.md` and project files to GitHub.
- Add relevant screenshots under a `screenshots/` folder to visualize your demo output.
