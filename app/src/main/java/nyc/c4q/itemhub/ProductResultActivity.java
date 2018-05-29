package nyc.c4q.itemhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ProductResultActivity extends AppCompatActivity {

    private TextView barcodeNum;
    public static final String TAG= ProductResultActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_result);

        barcodeNum= findViewById(R.id.barcode_number);

        Intent intent= getIntent();
        long barcode= intent.getLongExtra("barcodeNumber",0);
        Log.d(TAG, "onCreate: " + barcode);
        barcodeNum.setText(String.valueOf(barcode));
    }
}
