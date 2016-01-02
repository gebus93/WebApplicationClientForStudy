package pl.gebickionline.pricelist;

/**
 * Created by Łukasz on 2016-01-02.
 */
public class ManageableService extends Service {
    private Long id;
    private boolean visible;

    public ManageableService(pl.gebickionline.communication.Service s) {
        super(s);
        this.id = s.id();
        this.visible = s.visible();
    }

    public ManageableService(Builder builder) {
        this.id = builder.id;
        this.serviceName = builder.serviceName;
        this.price = builder.price;
        this.minPrice = builder.minPrice;
        this.maxPrice = builder.maxPrice;
        this.ordinal = builder.ordinal;
        this.visible = builder.visible;
    }

    public Long id() {
        return id;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return "ManageableService{" +
                "id=" + id +
                ", visible=" + visible +
                ", serviceName='" + serviceName() + '\'' +
                ", price=" + price() +
                ", minPrice=" + minPrice() +
                ", maxPrice=" + maxPrice() +
                ", ordinal=" + ordinal() +
                '}';
    }

    public static class Builder {
        private Long id;
        private String serviceName;
        private Long price;
        private Long minPrice;
        private Long maxPrice;
        private long ordinal;
        private boolean visible;


        public Builder withId(Long id) {
            this.id = id;
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

        public Builder withOrdinal(long ordinal) {
            this.ordinal = ordinal;
            return this;
        }

        public Builder withVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public ManageableService build() {
            return new ManageableService(this);
        }
    }
}
