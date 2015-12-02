package pl.gebickionline.communication;

import java.text.*;
import java.util.Date;

public class News {
    private final Long id;
    private final String title;
    private final String content;
    private final Date creationTime;
    private final Date lastUpdateTime;

    private News(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.creationTime = builder.creationTime;
        this.lastUpdateTime = builder.lastUpdateTime;
    }

    public String content() {
        return content;
    }

    public Date creationTime() {
        return creationTime;
    }

    public Long id() {
        return id;
    }

    public Date lastUpdateTime() {
        return lastUpdateTime;
    }

    public String title() {
        return title;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String content;
        private Date creationTime;
        private Date lastUpdateTime;
        private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmm");


        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withCreationTime(Date creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public Builder withCreationTime(String creationTime) {
            this.creationTime = parseStringToDate(creationTime);
            return this;
        }

        private Date parseStringToDate(String creationTime) {
            try {
                return sdf.parse(creationTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        public Builder withLastUpdateTime(Date lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
            return this;
        }

        public Builder withLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = parseStringToDate(lastUpdateTime);
            return this;
        }

        public News build() {
            return new News(this);
        }
    }
}