package nyc.c4q.itemhub;

import java.util.List;

import nyc.c4q.itemhub.model.Offers;

/**
 * Created by yokilam on 5/29/18.
 */

public interface ProductContract {

    interface View{
        void showTitle(String title);
        void showDescription(String description);
        void showImage(String url);
        void showMerchants(List<Offers> merchantList);
        void loadWebsite(String url);
        void showFragment();
        void hideFragment();
    }

    interface Presenter{
        void start();
        void setBarcode(long barcode);
    }
}
