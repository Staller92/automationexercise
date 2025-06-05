# automationexercise
# 🧪 AutomationExercise Test Framework

This project is an automated testing framework that combines:

- ✅ **UI testing** using Selenium WebDriver  
- 🌐 **API testing** using Rest Assured  
- 📊 **Test reporting** with Allure

---

## 📌 Technologies Used

- Java 11  
- Maven  
- Selenium 4.20  
- Rest Assured 5.4  
- TestNG 7.9  
- Allure 2.24  
- AssertJ  
- SLF4J + Logback (logging)  
- Owner (configuration management)  

---

## 🚀 How to Run

### 1. Clone the Repository 
```bash
git clone https://github.com/your-user/automationexercise.git
cd automationexercise

mvn clean install
mvn clean test
mvn test -DsuiteXmlFile=testng_api.xml
mvn test -DsuiteXmlFile=testng_ui.xml

Generate and View Report
allure serve target/allure-results
