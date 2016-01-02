package pl.gebickionline.communication;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class Group {
    private final String groupName;
    private final long ordinal;
    private final Long id;
    private final Boolean visible;

    public Group(Builder builder) {
        this.groupName = builder.groupName;
        this.id = builder.id;
        this.ordinal = builder.ordinal;
        this.visible = builder.visible;

    }

    public Boolean visible() {
        return visible;
    }

    public Group(long id, String groupName, long ordinal) {
        this.groupName = groupName;
        this.id = id;
        this.ordinal = ordinal;
        this.visible = null;
    }

    public Long id() {
        return id;
    }

    public String groupName() {
        return groupName;
    }

    public long ordinal() {
        return ordinal;
    }

    public static class Builder {
        private String groupName;
        private long ordinal;
        private Long id;
        private Boolean visible;

        public Builder withGroupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public Builder withOrdinal(long ordinal) {
            this.ordinal = ordinal;
            return this;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withVisible(Boolean visible) {
            this.visible = visible;
            return this;
        }

        public Group build() {
            return new Group(this);
        }
    }
}
