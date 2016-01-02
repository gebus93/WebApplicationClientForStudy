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
}
