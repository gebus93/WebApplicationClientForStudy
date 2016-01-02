package pl.gebickionline.pricelist;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class Service {
    protected String serviceName;
    protected Long price;
    protected Long minPrice;
    protected Long maxPrice;
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

    public Long maxPrice() {
        return maxPrice;
    }

    public Long minPrice() {
        return minPrice;
    }

    public long ordinal() {
        return ordinal;
    }

    public Long price() {
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