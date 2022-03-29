package Utill;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ActionEvents {
    WebDriver driver;

    public ActionEvents(WebDriver driver){
        this.driver = driver;
    }

    public void inputXpath(String type, String value, String data){
        driver.findElement(By.xpath("//input[@"+type+"= '"+value+"']")).sendKeys(data);
    }

    public void tagXpathText(String tag,String value){
        driver.findElement(By.xpath("//"+tag+"[text() = '"+value+"']")).click();
    }

    public void tagXpathIdentifier(String tag, String category, String value) {
        driver.findElement(By.xpath("//"+tag+"[@"+category+" = '"+value+"']")).click();
    }

    public void alertBoxOk() {
        Alert al = driver.switchTo().alert();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        al.accept();
        System.out.println("click ok in alert box");
    }

    public void takeScreenshot(String path ,String fileName) throws IOException, AWTException {

        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "png", new File(path+fileName+".png"));
    }
}
