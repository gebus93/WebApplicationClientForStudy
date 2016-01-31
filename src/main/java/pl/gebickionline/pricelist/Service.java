package pl.gebickionline.pricelist;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class Service {
    protected String serviceName;
    protected Integer price;
    protected Integer minPrice;
    protected Integer maxPrice;
    protected long ordinal;

    public Service(pl.gebickionline.communication.Service s) {
        serviceName = s.serviceName();
        ordinal = s.ordinal();
        price = s.price();
        minPrice = s.minPrice();
        maxPrice = s.maxPrice();
    }

    protected Service() {
    }

    public Integer maxPrice() {
        return maxPrice;
    }

    public Integer minPrice() {
        return minPrice;
    }

    public long ordinal() {
        return ordinal;
    }

    public Integer price() {
        return price;
    }

    public String serviceName() {
        return serviceName;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceName='" + serviceName + '\'' +
                ", price=" + price +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", ordinal=" + ordinal +
                '}';
    }
}
