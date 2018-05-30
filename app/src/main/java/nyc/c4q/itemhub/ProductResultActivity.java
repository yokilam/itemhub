package nyc.c4q.itemhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nyc.c4q.itemhub.network.UpcService;

public class ProductResultActivity extends AppCompatActivity implements ProductContract.View{

    public static final String TAG= ProductResultActivity.class.getSimpleName();
    private ProductPresenter presenter;
    private TextView productName, description;
    private ImageView productImage;

    long barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_result);

        setUpViews();

        barcode= getIntent().getLongExtra("barcodeNumber",0);
        presenter=new ProductPresenter(this, new UpcService());

        presenter.setBarcode(barcode);
        presenter.start();

        Log.d(TAG, "onCreate: " + barcode);
    }

    private void setUpViews() {
        productName= findViewById(R.id.barcode_number);
        description= findViewById(R.id.product_description);
        productImage= findViewById(R.id.product_picture);
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
}
