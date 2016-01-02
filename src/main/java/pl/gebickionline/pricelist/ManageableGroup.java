package pl.gebickionline.pricelist;

import java.util.*;

import static java.util.Collections.*;

/**
 * Created by Łukasz on 2016-01-02.
 */
public class ManageableGroup {
    private Long id;
    private String groupName;
    private long ordinal;
    private List<ManageableService> services;

    public ManageableGroup(Long id, String groupName, long ordinal) {
        this.id = id;
        this.groupName = groupName;
        this.ordinal = ordinal;
    }

    public String groupName() {
        return groupName;
    }

    public Long id() {
        return id;
    }

    public long ordinal() {
        return ordinal;
    }

    public List<ManageableService> services() {
        return services == null
                ? emptyList()
                : unmodifiableList(services);
    }

    public void addService(pl.gebickionline.communication.Service s) {
        if (services == null)
            services = new ArrayList<>();

        services.add(new ManageableService(s));
        services.sort((s1, s2) -> Long.valueOf(s1.ordinal()).compareTo(s2.ordinal()));
    }

    public void services(List<ManageableService> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "ManageableGroup{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", ordinal=" + ordinal +
                ", services=" + services() +
                '}';
    }
}
