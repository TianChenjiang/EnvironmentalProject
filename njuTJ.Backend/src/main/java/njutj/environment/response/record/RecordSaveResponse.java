package njutj.environment.response.record;

public class RecordSaveResponse {
    private int infoCode;
    private String description;

    public RecordSaveResponse(String description) {
        this.infoCode = 10000;
        this.description = description;
    }

    public int getInfoCode() {
        return infoCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
