package pl.gebickionline.services.pricelist;

import java.util.Collections;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class EmptyPriceList extends PriceList {
    public EmptyPriceList() {
        super(Collections.emptyList());
    }
}
