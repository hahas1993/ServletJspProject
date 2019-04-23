package pl.sda.model;

import java.util.List;

public class User {

    private String name;
    private List<String> follows;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFollows() {
        return follows;
    }

    public void setFollows(List<String> follows) {
        this.follows = follows;
    }
}
