package pl.gebickionline.communication;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class Service {
    private final Long id;
    private final Boolean visible;
    private final String serviceName;
    private final Integer price;
    private final Integer minPrice;
    private final Integer maxPrice;
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

    public Integer maxPrice() {
        return maxPrice;
    }

    public Integer minPrice() {
        return minPrice;
    }

    public Long ordinal() {
        return ordinal;
    }

    public Integer price() {
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
        private Integer price;
        private Integer minPrice;
        private Integer maxPrice;
        private Long ordinal;
        private Long groupID;

        public Builder() {
        }

        public Builder(Service s) {
            this.id = s.id;
            this.visible = s.visible;
            this.serviceName = s.serviceName;
            this.price = s.price;
            this.minPrice = s.minPrice;
            this.maxPrice = s.maxPrice;
            this.ordinal = s.ordinal;
            this.groupID = s.groupID;
        }


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

        public Builder withPrice(Integer price) {
            this.price = price;
            return this;
        }

        public Builder withMinPrice(Integer minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public Builder withMaxPrice(Integer maxPrice) {
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
