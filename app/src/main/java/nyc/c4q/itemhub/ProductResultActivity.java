package nyc.c4q.itemhub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nyc.c4q.itemhub.model.Offers;
import nyc.c4q.itemhub.network.UpcService;
import nyc.c4q.itemhub.rv.MerchantAdapter;

public class ProductResultActivity extends AppCompatActivity implements ProductContract.View {

    public static final String TAG = ProductResultActivity.class.getSimpleName();
    private ProductPresenter presenter;
    private TextView productName, description;
    private ImageView productImage;
    private RecyclerView merchantRecyclerView;
    private WebView myWebView;
    private FrameLayout noItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_result);

        setUpViews();
        setupRecyclerView();

        long barcode = getIntent().getLongExtra("barcodeNumber", 0);
        presenter = new ProductPresenter(this, new UpcService());

        presenter.setBarcode(barcode);
        presenter.start();

        Log.d(TAG, "onCreate: " + barcode);
    }

    private void setUpViews() {
        productName = findViewById(R.id.product_name);
        description = findViewById(R.id.product_description);
        productImage = findViewById(R.id.product_picture);
        myWebView = findViewById(R.id.webview);
        noItem= findViewById(R.id.no_item);
    }

    @Override
    public void showTitle(String title) {
        productName.setText(title);
    }

    @Override
    public void showDescription(String descriptionText) {
        description.setText(descriptionText);
    }

    @Override
    public void showImage(String url) {
        Picasso.with(productImage.getContext())
                .load(url)
                .into(productImage);
    }

    @Override
    public void showMerchants(List<Offers> merchantList) {
        MerchantAdapter merchantAdapter= new MerchantAdapter(merchantList);
        merchantRecyclerView.setAdapter(merchantAdapter);
    }

    @Override
    public void loadWebsite(String url) {
        myWebView.loadUrl(url);
    }

    @Override
    public void showFragment() {
        noItem.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFragment() {
        noItem.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        merchantRecyclerView = findViewById(R.id.recyclerView);
        merchantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        merchantRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
