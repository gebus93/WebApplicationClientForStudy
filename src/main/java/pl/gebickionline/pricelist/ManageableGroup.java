package pl.gebickionline.pricelist;

import pl.gebickionline.ui.controller.pricelist.MovementDirection;

import java.util.*;

import static java.util.Collections.emptyList;
import static pl.gebickionline.ui.controller.pricelist.MovementDirection.*;

/**
 * Created by Łukasz on 2016-01-02.
 */
public class ManageableGroup implements Comparable<ManageableGroup> {
    private Long id;
    private boolean visible;
    private String groupName;
    private long ordinal = 1;
    private List<ManageableService> services;

    public ManageableGroup(Long id, String groupName, boolean visible, long ordinal) {
        this.id = id;
        this.groupName = groupName;
        this.visible = visible;
        this.ordinal = ordinal;
    }

    public ManageableGroup() {

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
        if (services == null) return emptyList();

        sortServices();
        return Collections.unmodifiableList(services);
    }

    public void addService(pl.gebickionline.communication.Service s) {
        if (services == null)
            services = new ArrayList<>();

        services.add(new ManageableService(s));
    }

    public void services(List<ManageableService> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return groupName();
    }

    public boolean visible() {
        return visible;
    }

    public void id(Long id) {
        this.id = id;
    }

    public void ordinal(Long newOrdinal) {
        this.ordinal = newOrdinal;
    }

    @Override
    public int compareTo(ManageableGroup o) {
        return Long.valueOf(ordinal()).compareTo(o.ordinal());
    }

    public void moveService(ManageableService service, MovementDirection direction) {
        if (direction == UP) {
            if (service.ordinal() == 1)
                return;

            long newOrdinal = service.ordinal() - 1;
            services.get((int) newOrdinal - 1).ordinal++;
            service.ordinal(newOrdinal);
        }

        if (direction == DOWN) {
            if (service.ordinal() == services.size())
                return;

            long newOrdinal = service.ordinal() + 1;
            services.get((int) newOrdinal - 1).ordinal--;
            service.ordinal(newOrdinal);
        }
        sortServices();
    }

    private void sortServices() {
        final Long[] newOrdinal = {1L};
        services.stream().sorted().forEachOrdered(s -> s.ordinal(newOrdinal[0]++));
        services.sort((s1, s2) -> Long.valueOf(s1.ordinal()).compareTo(s2.ordinal()));
    }

    public void addService(ManageableService manageableService) {
        if (services == null)
            services = new ArrayList<>();

        services.add(manageableService);

    }

    public void removeService(ManageableService service) {
        ManageableService serviceInstance = services.stream()
                .filter(s -> Objects.equals(s.id(), service.id()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usługa nie istnieje"));

        services.remove(serviceInstance);
    }

    public void merge(ManageableGroup group) {
        this.groupName = group.groupName;
        this.visible = group.visible;
        this.ordinal = group.ordinal;
    }
}
