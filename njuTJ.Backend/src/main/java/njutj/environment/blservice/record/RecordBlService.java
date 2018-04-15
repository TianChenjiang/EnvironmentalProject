package njutj.environment.blservice.record;

import njutj.environment.response.record.RecordCreateResponse;
import njutj.environment.vo.record.RecordCreateVo;
import njutj.environment.vo.record.SpeciesCheckVo;

public interface RecordBlService {
    /**
     * save expert's check result
     *
     * @param speciesCheckVo
     */
    void check(SpeciesCheckVo speciesCheckVo);

    /**
     * create a record
     *
     * @param recordCreateVo
     */
    RecordCreateResponse createRecord(RecordCreateVo recordCreateVo);
}