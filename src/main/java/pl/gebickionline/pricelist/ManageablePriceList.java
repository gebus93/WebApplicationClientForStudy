package pl.gebickionline.pricelist;

import java.util.*;

import static java.util.Collections.*;

/**
 * Created by Łukasz on 2016-01-02.
 */
public class ManageablePriceList {
    private List<ManageableGroup> groups;

    public ManageablePriceList(Collection<ManageableGroup> groups) {
        if (groups == null) {
            this.groups = new LinkedList<>();
            return;
        }

        this.groups = new LinkedList<>(groups);
        this.groups.sort((g1, g2) -> Long.valueOf(g1.ordinal()).compareTo(g2.ordinal()));
    }

    public List<ManageableGroup> asList() {
        return groups == null
                ? emptyList()
                : unmodifiableList(groups);
    }

}
