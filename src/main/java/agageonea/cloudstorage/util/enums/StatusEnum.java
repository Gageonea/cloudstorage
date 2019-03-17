package agageonea.cloudstorage.util.enums;

public enum StatusEnum {

    SUCCESS("success"), ERROR("error");

    private String text;

    private StatusEnum(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
