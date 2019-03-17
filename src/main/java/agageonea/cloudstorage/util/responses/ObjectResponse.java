package agageonea.cloudstorage.util.responses;

import agageonea.cloudstorage.util.enums.StatusEnum;

public class ObjectResponse extends Response{

    public ObjectResponse(){}

    public ObjectResponse(Object object){
        this.status = StatusEnum.SUCCESS;
        this.object = object;
    }

    protected Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
