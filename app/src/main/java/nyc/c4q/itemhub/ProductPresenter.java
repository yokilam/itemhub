package nyc.c4q.itemhub;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import nyc.c4q.itemhub.model.Offers;
import nyc.c4q.itemhub.model.ProductSearchResult;
import nyc.c4q.itemhub.network.UpcService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenter implements ProductContract.Presenter {
    private static final String TAG = "Presenter";
    private ProductContract.View viewImpl;
    private UpcService upcService;
    private long barcodeNumber;

    public ProductPresenter(ProductContract.View viewImpl, UpcService upcService) {
        this.viewImpl = viewImpl;
        this.upcService = upcService;
    }

    @Override
    public void start() {
        Log.d(TAG, "start: ====== running start method in presenter " + barcodeNumber);
        getProductResult();
    }

    @Override
    public void setBarcode(long barcode) {
        barcodeNumber = barcode;
    }

    private void getProductResult() {
        Call <ProductSearchResult> productCall = upcService.getUpcItemDbApi().getProductSearchResult(barcodeNumber);
        productCall.enqueue(new Callback <ProductSearchResult>() {
            @Override
            public void onResponse(@NonNull Call <ProductSearchResult> call, @NonNull Response <ProductSearchResult> response) {
                Log.d(TAG, "onResponse: I SEARCHING FOR RESULT");
                ProductSearchResult productSearchResult = response.body();
                if (productSearchResult!=null && productSearchResult.getTotal()>0 ) {
                    String productName = productSearchResult.getItems().get(0).getTitle();
                    List <String> imageList = productSearchResult.getItems().get(0).getImages();
                    String productDescription = productSearchResult.getItems().get(0).getDescription();
                    List <Offers> merchantList = productSearchResult.getItems().get(0).getOffers();

                    viewImpl.hideFragment();
                    viewImpl.showTitle(productName);
                    viewImpl.showDescription(productDescription);
                    viewImpl.showImage(imageList.get(0));

                    if (!merchantList.isEmpty()) {
                        viewImpl.showMerchants(merchantList);
                    }
                } else {
                    viewImpl.showFragment();
                }
            }

            @Override
            public void onFailure(Call <ProductSearchResult> call, Throwable t) {
                Log.d(TAG, "onFailure: === " + t.toString());
            }
        });
    }
}
