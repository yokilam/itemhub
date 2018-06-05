package nyc.c4q.itemhub;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import nyc.c4q.itemhub.Utils.Tools;
import nyc.c4q.itemhub.rv.MyViewPagerAdapter;

public class UpcInfoActivity extends AppCompatActivity implements MyListener {

    private static final int MAX_STEP = 4;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private String about_title_array[] = {
            "What is UPC-A barcode?",
            "How does itemHub works?",
            "Result",
            "Disclaimer"
    };
    private String about_description_array[] = {
            "UPC stands for Universal Product Code, UPC-A is 12 digit barcode",
            "Scan UPC-A barcode",
            "Return a product's information that is associate with that barcode.",
            "Might return a result you might not like",
    };
    private int about_images_array[] = {
            R.drawable.upcbarcode,
            R.drawable.scan,
            R.drawable.information,
            R.drawable.disclaimer
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upc_info);

        viewPager = findViewById(R.id.view_pager);

        bottomProgressDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter(this, about_title_array, about_description_array,
                about_images_array, viewPager, MAX_STEP, this);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        Tools.setSystemBarColor(this, R.color.mainPurple);
        Tools.setSystemBarLight(this);
    }

    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.mainPurple), PorterDuff.Mode.SRC_IN);
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void listenForFinish() {
        finish();
    }
}
