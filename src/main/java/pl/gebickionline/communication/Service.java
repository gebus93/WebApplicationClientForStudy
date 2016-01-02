package pl.gebickionline.communication;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class Service {
    private final Long id;
    private final Boolean visible;
    private final String serviceName;
    private final Long price;
    private final Long minPrice;
    private final Long maxPrice;
    private final Long ordinal;
    private final Long groupID;

    private Service(Builder builder) {
        this.serviceName = builder.serviceName;
        this.price = builder.price;
        this.minPrice = builder.minPrice;
        this.maxPrice = builder.maxPrice;
        this.ordinal = builder.ordinal;
        this.groupID = builder.groupID;
        this.id = builder.id;
        this.visible = builder.visible;
    }

    public Long maxPrice() {
        return maxPrice;
    }

    public Long minPrice() {
        return minPrice;
    }

    public Long ordinal() {
        return ordinal;
    }

    public Long price() {
        return price;
    }

    public Long groupID() {
        return groupID;
    }

    public String serviceName() {
        return serviceName;
    }

    public Long id() {
        return id;
    }

    public Boolean visible() {
        return visible;
    }

    public static class Builder {
        private Long id;
        private Boolean visible;
        private String serviceName;
        private Long price;
        private Long minPrice;
        private Long maxPrice;
        private Long ordinal;
        private Long groupID;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withVisible(Boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder withServiceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder withPrice(Long price) {
            this.price = price;
            return this;
        }

        public Builder withMinPrice(Long minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public Builder withMaxPrice(Long maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public Builder withOrdinal(Long ordinal) {
            this.ordinal = ordinal;
            return this;
        }

        public Builder withGroupID(Long groupID) {
            this.groupID = groupID;
            return this;
        }

        public Service build() {
            return new Service(this);
        }
    }
}
