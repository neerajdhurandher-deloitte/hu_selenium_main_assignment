import Customer.CostumeExceptions;
import Customer.Customer;
import Functionality.MainClass;
import Utill.XlsxReader;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TestClass {

    public static MainClass mainClass;
    public static ArrayList<Customer> customerList;

    Logger log = Logger.getLogger(TestClass.class);


    @BeforeTest
    public  void initializeSetup(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\ndhurandher\\Downloads\\chromedriver.exe");
        mainClass = new MainClass();
        readXmlFile("src/main/java/Customer/customerDetails.xlsx");
        try {
            Thread.sleep(3000);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private void readXmlFile(String path) {
        XlsxReader xmlReader = new XlsxReader(path);
        try {
            customerList = xmlReader.readFile();
            log.info("xlsx read");
        }catch (FileNotFoundException e){
            log.error("File not found " + e.getMessage());
        }catch (IOException e){
            log.error("IO exception "+ e.getMessage());
        }

    }

    @AfterTest
    public  void closeBrowser(){
        log.info("Web driver closed");
    }

//    @Test
//    public  void AddCustomerTest() throws InterruptedException {
//        try {
//            mainClass.launch();
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        mainClass.ManagerLogIn();
//
//        Customer customer1 = customerList.get(0);
//        mainClass.AddCustomer(customer1.getFirstName(), customer1.getLastName(), customer1.getPinCode());
//        Customer customer2 = customerList.get(1);
//        mainClass.AddCustomer(customer2.getFirstName(), customer2.getLastName(), customer2.getPinCode());
//
//        mainClass.openAccount(6, 3);
//        mainClass.openAccount(7, 3);
//
//        mainClass.goToHomePage();
//
//        mainClass.closeWindow();
//
//    }

    @Test
    public void DepositWithdraw() throws InterruptedException {
        try {
            mainClass.launch();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        mainClass.ManagerLogIn();
        Customer customer1 = customerList.get(2);
        mainClass.AddCustomer(customer1.getFirstName(), customer1.getLastName(), customer1.getPinCode());
        mainClass.openAccount(6, 3);
        mainClass.goToHomePage();

        logIn(customer1);

        mainClass.deposit(140);

        try {
            mainClass.withDrawl(90);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        mainClass.closeWindow();

    }

    @Test
    public void MaxWithdraw() throws InterruptedException {
        try {
            mainClass.launch();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        mainClass.ManagerLogIn();

        Customer customer1 = customerList.get(3);
        mainClass.AddCustomer(customer1.getFirstName(), customer1.getLastName(), customer1.getPinCode());
        mainClass.openAccount(6, 3);

        mainClass.goToHomePage();

        logIn(customer1);

        mainClass.deposit(100);

        try {
            mainClass.withDrawl(mainClass.checkBalance()+10);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        mainClass.closeWindow();

    }

    @Test
    public void ChangeAccount() throws InterruptedException {

        try {
            mainClass.launch();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        mainClass.ManagerLogIn();

        Customer customer1 = customerList.get(4);
        mainClass.AddCustomer(customer1.getFirstName(), customer1.getLastName(), customer1.getPinCode());
        mainClass.openAccount(6, 3);
        mainClass.openAccount(6, 3);


        mainClass.goToHomePage();



        logIn(customer1);

        mainClass.changeAccount(2);

        mainClass.deposit(100);

        try {
            mainClass.withDrawl(90);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        mainClass.closeWindow();

    }

//    @Test
//    public  void DeleteCustomerTest() throws InterruptedException {
//        try {
//            mainClass.launch();
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        mainClass.ManagerLogIn();
//
//        Customer customer1 = customerList.get(5);
//        mainClass.AddCustomer(customer1.getFirstName(), customer1.getLastName(), customer1.getPinCode());
//
//        boolean customerExits;
//        try {
//            customerExits = mainClass.searchCustomer(customer1.getFirstName(), customer1.getLastName());
//            if (customerExits) {
//                mainClass.deleteCustomer();
//                log.info(customer1.getFirstName() + " " +customer1.getLastName()+ "'s account has been deleted");
//            }
//        }catch (CostumeExceptions e){
//            log.error(e.getExceptionMsg());
//        }
//
//        mainClass.goToHomePage();
//
//        mainClass.closeWindow();
//
//    }
//
//    @Test
//    public  void DeleteCustomerNonExistsTest() throws InterruptedException {
//        try {
//            mainClass.launch();
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        mainClass.ManagerLogIn();
//
//        Customer customer1 = customerList.get(6);
//        mainClass.AddCustomer(customer1.getFirstName(), customer1.getLastName(), customer1.getPinCode());
//
//        boolean customerExits;
//        try {
//            customerExits = mainClass.searchCustomer(customer1.getFirstName(), customer1.getLastName());
//            if (customerExits) {
//                mainClass.deleteCustomer();
//                log.info(customer1.getFirstName() + " " +customer1.getLastName()+ "'s account has been deleted");
//            }
//        }catch (CostumeExceptions e){
//            log.error(e.getExceptionMsg());
//        }
//
//        mainClass.goToHomePage();
//
//        mainClass.closeWindow();
//
//    }




    public void logIn(Customer customer) throws InterruptedException {

        try {
            mainClass.logIn(customer.getFirstName()+" "+customer.getLastName());
        }catch (CostumeExceptions e){
            log.error(e.getExceptionMsg());
        }
    }
}
