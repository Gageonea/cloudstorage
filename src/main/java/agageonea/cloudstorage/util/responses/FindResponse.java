package agageonea.cloudstorage.util.responses;

import agageonea.cloudstorage.util.enums.StatusEnum;

public class FindResponse extends ObjectResponse{


    public FindResponse(String query, Object object){
        this.query = query;
        this.object = object;
        this.status = StatusEnum.SUCCESS;
    }

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
