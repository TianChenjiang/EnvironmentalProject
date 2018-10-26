package njutj.environment.vo.record;

public class SpeciesCheckVo {
    private long recordId;
    private String name;

    public SpeciesCheckVo() {
    }

    public SpeciesCheckVo(long recordId, String name) {
        this.recordId = recordId;
        this.name = name;
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
}
