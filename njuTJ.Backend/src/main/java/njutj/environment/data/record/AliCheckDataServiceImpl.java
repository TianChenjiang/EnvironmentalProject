package njutj.environment.data.record;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import njutj.environment.dataservice.record.AliCheckDataService;
import njutj.environment.exception.viewexception.SystemException;
import njutj.environment.util.ApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AliCheckDataServiceImpl implements AliCheckDataService {
    @Value("${aliyun.akId}")
    private String akId;
    @Value("${aliyun.akSecret}")
    private String akSecret;
    @Value("${aliyun.imageHostUrl}")
    private String imageHostUrl;

    @Override
    public String checkImage(String url) throws SystemException {
        String bodys = "{\"type\":0," +
                "\"image_url\":\"" + url + "\"}";
        try {
            return (String) ((JSONObject) ((JSONArray) JSONObject.fromObject(ApiUtil.sendPost(imageHostUrl, bodys, akId, akSecret)).get("tags")).get(0)).get("value");
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException();
        }
    }
}
