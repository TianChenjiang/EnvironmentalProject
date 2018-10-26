package njutj.environment.exception.viewexception;

import njutj.environment.response.WrongResponse;

public class UserAlreadyExistentException extends Exception {
    private WrongResponse response = new WrongResponse(10002, "User already exists.");

    public WrongResponse getResponse() {
        return response;
    }
}
