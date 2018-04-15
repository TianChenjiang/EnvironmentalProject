package njutj.environment.blservice.record;

import njutj.environment.exception.viewexception.SystemException;
import njutj.environment.response.record.RecordCreateResponse;
import njutj.environment.response.record.WaitForCheckResponse;
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
    RecordCreateResponse createRecord(RecordCreateVo recordCreateVo) throws SystemException;

    /**
     * load the data of records to be checked
     *
     * @return the records to be checked
     */
    WaitForCheckResponse loadWaitForCheck();
}