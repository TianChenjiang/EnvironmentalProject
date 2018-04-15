package njutj.environment.response.upload;

import njutj.environment.response.Response;

public class UploadImageResponse extends Response {
    private long recordId;
    private String url;

    public UploadImageResponse() {
    }

    public UploadImageResponse(long recordId, String url) {
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
