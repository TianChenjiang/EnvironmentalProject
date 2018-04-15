package njutj.environment.vo.record;

import njutj.environment.publicdatas.record.Position;

public class RecordCreateVo {
    private long recordId;
    private String name;
    private Position position;

    public RecordCreateVo() {
    }

    public RecordCreateVo(long recordId, String name, Position position) {
        this.recordId = recordId;
        this.name = name;
        this.position = position;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
