package nyc.c4q.itemhub;

/**
 * Created by yokilam on 5/29/18.
 */

public interface ProductContract {

    interface View{
        void showTitle(String title);
        void showDescription(String description);
        void showImage(String url);
    }

    interface Presenter{
        void start();
        void setBarcode(long barcode);
    }
}
