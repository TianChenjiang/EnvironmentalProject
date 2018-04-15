package njutj.environment.vo.record;

import njutj.environment.publicdatas.record.Position;

import java.io.Serializable;

public class SpeciesWaitForCheckVo implements Serializable {
    private Position position;
    private long recordId;

    public SpeciesWaitForCheckVo() {
    }

    public SpeciesWaitForCheckVo(Position position, long recordId) {
        this.position = position;
        this.recordId = recordId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }
}
