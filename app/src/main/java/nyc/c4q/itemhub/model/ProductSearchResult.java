package nyc.c4q.itemhub.model;

import java.util.List;

public class ProductSearchResult {
    private int total;
    private List<Product> items;

    public int getTotal() {
        return total;
    }

    public List <Product> getItems() {
        return items;
    }
}
