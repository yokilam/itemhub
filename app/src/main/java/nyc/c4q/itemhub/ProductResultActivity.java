package nyc.c4q.itemhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import nyc.c4q.itemhub.network.UpcService;

public class ProductResultActivity extends AppCompatActivity implements ProductContract.View{

    public static final String TAG= ProductResultActivity.class.getSimpleName();
    private ProductPresenter presenter;
    private TextView barcodeNum;
    long barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_result);

        barcodeNum= findViewById(R.id.barcode_number);

        barcode= getIntent().getLongExtra("barcodeNumber",0);
        presenter=new ProductPresenter(this, new UpcService());
        presenter.setBarcode(barcode);
        presenter.start();


        Log.d(TAG, "onCreate: " + barcode);
//        barcodeNum.setText(String.valueOf(barcode));
    }

    @Override
    public void showTitle(String title) {
        barcodeNum.setText(title);

    }
}
