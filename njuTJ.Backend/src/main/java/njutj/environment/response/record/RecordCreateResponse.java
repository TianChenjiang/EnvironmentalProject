package njutj.environment.response.record;

import njutj.environment.publicdatas.record.RecordState;
import njutj.environment.response.Response;

public class RecordCreateResponse extends Response {
    private long recordId;
    private RecordState recordState;
    private String name;

    public RecordCreateResponse() {
    }

    public RecordCreateResponse(long recordId, RecordState recordState, String name) {
        this.recordId = recordId;
        this.recordState = recordState;
        this.name = name;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public RecordState getRecordState() {
        return recordState;
    }

    public void setRecordState(RecordState recordState) {
        this.recordState = recordState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
