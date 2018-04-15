package njutj.environment.response.record;

import njutj.environment.response.Response;

import java.util.List;

public class WaitForCheckResponse extends Response {
    List<WaitForCheckItem> waitForCheckItemList;

    public WaitForCheckResponse() {
    }

    public WaitForCheckResponse(List<WaitForCheckItem> waitForCheckItemList) {
        this.waitForCheckItemList = waitForCheckItemList;
    }

    public List<WaitForCheckItem> getWaitForCheckItemList() {
        return waitForCheckItemList;
    }

    public void setWaitForCheckItemList(List<WaitForCheckItem> waitForCheckItemList) {
        this.waitForCheckItemList = waitForCheckItemList;
    }
}
