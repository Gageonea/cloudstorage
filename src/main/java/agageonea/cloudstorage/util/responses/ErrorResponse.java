package agageonea.cloudstorage.util.responses;

import agageonea.cloudstorage.util.enums.StatusEnum;

public class ErrorResponse extends Response {

    private String message;

    public ErrorResponse(String message){
        this.status = StatusEnum.ERROR;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
