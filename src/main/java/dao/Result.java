package dao;

public class Result {

    private String id;
    private String count;

    public Result(String id, String name) {
        this.id = id;
        this.count = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return count;
    }

    public void setName(String name) {
        this.count = name;
    }
}
