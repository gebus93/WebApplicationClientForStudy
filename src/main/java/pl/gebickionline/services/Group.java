package pl.gebickionline.services;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class Group {
    private String groupName;
    private long ordinal;
    private long id;

    public Group(long id, String groupName, long ordinal) {
        this.groupName = groupName;
        this.id = id;
        this.ordinal = ordinal;
    }

    public long id() {
        return id;
    }

    public String groupName() {
        return groupName;
    }

    public long ordinal() {
        return ordinal;
    }

}
