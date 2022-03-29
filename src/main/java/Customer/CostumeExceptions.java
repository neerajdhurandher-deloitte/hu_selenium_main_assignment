package Customer;

public class CostumeExceptions extends  Exception {

    private String exceptionMsg;

    public CostumeExceptions(String msg){
        this.exceptionMsg = msg;
    }

    public String getExceptionMsg(){
        return this.exceptionMsg;
    }


}
