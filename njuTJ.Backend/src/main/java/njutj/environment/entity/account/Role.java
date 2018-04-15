package njutj.environment.entity.account;

public class Role {
    public final static String USER_NAME = "ROLE_USER";
    public final static String EXPERT_NAME = "ROLE_EXPERT";
    public final static Role USER = new Role(USER_NAME);
    public final static Role EXPERT = new Role(EXPERT_NAME);

    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
