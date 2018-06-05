package nyc.c4q.itemhub.rv;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import nyc.c4q.itemhub.MyListener;
import nyc.c4q.itemhub.R;

public class MyViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private Button btnNext;
    private Context context;
    private String[] about_title_array, about_description_array;
    private int[] about_images_array;
    private int maxStep;
    private ViewPager viewPager;
    private MyListener listener;

    public MyViewPagerAdapter(Context context, String[] titleArray, String[] descriptionArray,
                              int[] image, ViewPager viewPager, int maxStep, MyListener listener ) {
        this.context=context;
        this.layoutInflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        about_title_array=titleArray;
        about_description_array=descriptionArray;
        about_images_array= image;
        this.maxStep= maxStep;
        this.viewPager=viewPager;
        this.listener=listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = layoutInflater.inflate(R.layout.viewpager, container, false);
        ((TextView) view.findViewById(R.id.title)).setText(about_title_array[position]);
        ((TextView) view.findViewById(R.id.description)).setText(about_description_array[position]);
        ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);

        btnNext = (Button) view.findViewById(R.id.btn_next);

        if (position == about_title_array.length - 1) {
            btnNext.setText("Get Started");
        } else {
            btnNext.setText("Next");
        }


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < maxStep) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    listener.listenForFinish();
                }
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return about_title_array.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
