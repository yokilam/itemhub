package nyc.c4q.itemhub.model;

import java.util.List;

public class Items {
    private String title;
    private String description;
    private String brand;
    private String model;
    private String color;
    private String upc;
    private List<Offers> offers;
    private String[] images;
    private String lowest_recorded_price;
    private String highest_recorded_price;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getUpc() {
        return upc;
    }

    public List <Offers> getOffers() {
        return offers;
    }

    public String[] getImages() {
        return images;
    }

    public String getLowest_recorded_price() {
        return lowest_recorded_price;
    }

    public String getHighest_recorded_price() {
        return highest_recorded_price;
    }
}
