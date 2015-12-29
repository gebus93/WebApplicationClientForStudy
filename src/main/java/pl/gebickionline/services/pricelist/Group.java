package pl.gebickionline.services.pricelist;

import java.util.*;

import static java.util.Collections.*;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class Group {
    private String groupName;
    private long ordinal;
    private List<Service> services;

    public Group(String groupName, long ordinal) {
        this.groupName = groupName;
        this.ordinal = ordinal;
    }

    public String groupName() {
        return groupName;
    }

    public long ordinal() {
        return ordinal;
    }

    public List<Service> services() {
        return services == null
                ? emptyList()
                : unmodifiableList(services);
    }

    public void addService(pl.gebickionline.services.Service s) {
        if (services == null)
            services = new ArrayList<>();

        services.add(new Service(s));
        services.sort((s1, s2) -> Long.valueOf(s1.ordinal()).compareTo(s2.ordinal()));
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", ordinal=" + ordinal +
                ", services=" + services() +
                '}';
    }
}
