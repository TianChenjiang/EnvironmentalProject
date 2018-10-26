package njutj.environment.dataservice.record;

import njutj.environment.exception.viewexception.SystemException;

public interface AliCheckDataService {
    String checkImage(String url) throws SystemException;
}
