package in.mobileappdev.ecommerce.model;

/**
 * Created by Techjini on 9/4/2017.
 */

public class GenericResponse {

    private  int success;
    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
