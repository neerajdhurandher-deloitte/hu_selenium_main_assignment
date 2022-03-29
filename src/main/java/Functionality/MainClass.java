package Functionality;

import Customer.CostumeExceptions;
import Utill.ActionEvents;
import log4jPackage.LogTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainClass {
    WebDriver driver;
    ActionEvents actionEvents;

    Logger log = Logger.getLogger(MainClass.class);


    public void launch() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();;
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/");
        actionEvents = new ActionEvents(driver);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(2000);
        log.info("web browser initialized");
        log.trace("browser opened");

    }

    public void ManagerLogIn() throws InterruptedException {
        actionEvents.tagXpathText("button","Bank Manager Login");
        log.info("Manager login");

//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(2000);

    }
    public void AddCustomer(String first_name, String last_name, String post_code) throws InterruptedException {

        actionEvents.tagXpathIdentifier("button","ng-class","btnClass1");

//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(2000);
        actionEvents.inputXpath("placeholder","First Name",first_name);
        actionEvents.inputXpath("placeholder","Last Name",last_name);
        actionEvents.inputXpath("placeholder","Post Code",post_code);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(2000);

        actionEvents.tagXpathText("button","Add Customer");
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(2000);

        actionEvents.alertBoxOk();

        log.info("customer added");

//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(2000);


    }

    public void openAccount(int userIndex,int currencyIndex) throws InterruptedException {

        actionEvents.tagXpathIdentifier("button","ng-class","btnClass2");
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(1000);

        dropDownSelection("userSelect",userIndex);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(1000);

        dropDownSelection("currency",currencyIndex);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(1000);

        actionEvents.tagXpathText("button","Process");
        actionEvents.alertBoxOk();

        log.info("customer account created");

//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(2000);

    }

    private void dropDownSelection(String value, int selectIndex){

        WebElement selectOption  = driver.findElement(By.xpath("//select[@id='"+value+"']"));
        selectOption.click();
        Select selectObj = new Select(selectOption);
        if(selectObj.getOptions().size() > selectIndex)
            selectObj.selectByIndex(selectIndex);
        else
            log.error("given index is greater then option list size");

    }

    public boolean searchCustomer(String customerFirstName, String customerLastName) throws InterruptedException, CostumeExceptions {

        actionEvents.tagXpathIdentifier("button","ng-class","btnClass3");

//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(2000);

        actionEvents.inputXpath("placeholder","Search Customer",customerFirstName);

//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Thread.sleep(2000);

        WebElement baseTable = driver.findElement(By.cssSelector("table[class*='table table-bordered table-striped']"));

        WebElement firstNameCell = baseTable.findElement(By.xpath("//table/tbody/tr[1]/td[1]"));
        WebElement lastNameCell = baseTable.findElement(By.xpath("//table/tbody/tr[1]/td[2]"));
        System.out.println(firstNameCell.getText());
        System.out.println(lastNameCell.getText());

        if(customerFirstName.equals(firstNameCell.getText()) && customerLastName.equals(lastNameCell.getText())){
            log.info("Customer " + customerFirstName + " " + customerLastName + " is available");
            return true;
        }else{
            throw new CostumeExceptions("Costumer not found");
        }

    }

    public void deleteCustomer(){
        WebElement baseTable = driver.findElement(By.cssSelector("table[class*='table table-bordered table-striped']"));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        baseTable.findElement(By.xpath("//table/tbody/tr[1]/td[4]")).findElement(By.xpath("//button[text() = 'Delete']")).click();
    }

    public void logIn(String customerName) throws InterruptedException, CostumeExceptions {

        driver.findElement(By.xpath("//button[text() = 'Customer Login']")).click();

        WebElement selectOption  = driver.findElement(By.xpath("//select[@id='userSelect']"));
        selectOption.click();
        Select selectObj = new Select(selectOption);

        if(itemContains(selectObj,customerName)) {
            selectObj.selectByVisibleText(customerName);
            Thread.sleep(2000);
            actionEvents.tagXpathText("button","Login");
        }
        else {
            log.error("Customer not found");
            throw new CostumeExceptions("Customer not found exception");
        }


    }

    private boolean itemContains(Select selectObj, String item) {

        List<WebElement> list = selectObj.getOptions();

        for(WebElement element : list){
            if(element.getText().equals(item)) {
                System.out.println(element.getText());
                return true;
            }
        }

        return false;
    }


    public void goToHomePage(){
        actionEvents.tagXpathIdentifier("button","class","btn home");
    }

    public void logOut(){
        actionEvents.tagXpathIdentifier("button","class","btn logout");
    }

    public void deposit(int amount){

        int previousBalance =  checkBalance();

        actionEvents.tagXpathIdentifier("button","ng-class","btnClass2");
        actionEvents.inputXpath("placeholder","amount",String.valueOf(amount));
        actionEvents.tagXpathText("button","Deposit");
        int newBalance = checkBalance();

        if(previousBalance+amount==newBalance){
            log.info("Deposit successfully");
        }else {
            log.error("Deposit unsuccessfully");

        }
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

    public int checkBalance() {

        String balance = driver.findElement(By.xpath("//STRONG[@class='ng-binding'][2]")).getText();

        log.info("balance : - " + balance);

        return Integer.parseInt(balance);
    }

    public void withDrawl(int amount) throws InterruptedException, IOException, AWTException {

        int previousBalance =  checkBalance();

        actionEvents.tagXpathIdentifier("button","ng-class","btnClass3");

        Thread.sleep(3000);

        actionEvents.inputXpath("placeholder","amount",String.valueOf(amount));

        actionEvents.tagXpathText("button","Withdraw");

//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        Thread.sleep(2000);


        if(amount > previousBalance){

            if(driver.findElement(By.xpath("//span[@class='error ng-binding']")).isDisplayed()){
                log.info("error msg displayed");
            }else{
                log.error("error msg not displayed");
            }

        }else{

            int newBalance = checkBalance();

            if (previousBalance - amount == newBalance) {
                log.info("Withdraw successfully");
            } else {
                log.error("Withdraw unsuccessfully");
                actionEvents.takeScreenshot("C:\\Users\\ndhurandher\\Pictures\\Screenshorts\\", "withdraw error");
            }
        }
    }

    public void changeAccount(int index) throws InterruptedException {
        dropDownSelection("accountSelect",index);
        Thread.sleep(2000);
    }

    public void transactionTest() throws InterruptedException {
        actionEvents.tagXpathIdentifier("button","ng-class","btnClass1");

        Thread.sleep(2000);

        actionEvents.tagXpathText("button","Back");

    }

    public void takeScreenshot(String path ,String fileName) throws IOException, AWTException {

        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "png", new File(path+fileName+".png"));
    }

    public void closeWindow() {
        driver.close();
    }
}
