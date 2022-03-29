package Utill;

import Customer.Customer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XlsxReader {
    String filePath;
    ArrayList<Customer> customerList = new ArrayList<>();

    public XlsxReader(String path){
        this.filePath = path;
    }

    public ArrayList<Customer> readFile() throws IOException {

        File file = new File(filePath);

        FileInputStream inputStream = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet workbookSheet = workbook.getSheet("sheet1");

        int rowCount = workbookSheet.getLastRowNum()-workbookSheet.getFirstRowNum();

        for (int i = 1; i < rowCount+1; i++) {

            Row row = workbookSheet.getRow(i);

            Customer customer = new Customer();

            for (int j = 0; j < row.getLastCellNum(); j++) {

                String data = row.getCell(j).getStringCellValue();

                switch (j){
                    case 0-> customer.setFirstName(data);
                    case 1-> customer.setLastName(data);
                    case 2-> customer.setPinCode(data);
                }
            }
            customerList.add(customer);
            System.out.println(customer.getFirstName()+" " + customer.getLastName() + " " + customer.getPinCode());
        }

        return customerList;
    }
}
