package pl.gebickionline.services.pricelist;

import java.util.*;

import static java.util.Collections.*;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class PriceList {

    private List<Group> groups;

    public PriceList(Collection<Group> groups) {
        if (groups == null) {
            this.groups = new LinkedList<>();
            return;
        }

        this.groups = new LinkedList<>(groups);
        this.groups.sort((g1, g2) -> Long.valueOf(g1.ordinal()).compareTo(g2.ordinal()));
    }

    public List<Group> asList() {
        return groups == null
                ? emptyList()
                : unmodifiableList(groups);
    }

    public byte[] asPdf() {
        return null;
    }
}
