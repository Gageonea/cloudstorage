package agageonea.cloudstorage.util.responses;

import agageonea.cloudstorage.util.enums.StatusEnum;

import java.io.Serializable;

public class Response implements Serializable {

    protected StatusEnum status;

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }


}
