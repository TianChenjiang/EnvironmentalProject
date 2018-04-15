package njutj.environment.response.record;

public class WaitForCheckItem {
    private long recordId;
    private String url;

    public WaitForCheckItem() {
    }

    public WaitForCheckItem(long recordId, String url) {
        this.recordId = recordId;
        this.url = url;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
