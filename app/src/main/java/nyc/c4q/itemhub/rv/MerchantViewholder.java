package nyc.c4q.itemhub.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nyc.c4q.itemhub.R;
import nyc.c4q.itemhub.model.Offers;

public class MerchantViewholder extends RecyclerView.ViewHolder {

    private TextView merchantName, domain, price, shippingPrice, condition, link;

    public MerchantViewholder(View itemView) {
        super(itemView);

        merchantName= itemView.findViewById(R.id.merchant_name);
        domain= itemView.findViewById(R.id.domain);
        price= itemView.findViewById(R.id.price);
        shippingPrice= itemView.findViewById(R.id.shipping_price);
        condition= itemView.findViewById(R.id.condition);
        link= itemView.findViewById(R.id.link);
    }

    public void onBind(Offers offers) {
        merchantName.setText(offers.getMerchant());
        domain.setText(offers.getDomain());
        price.setText(offers.getPrice());
        shippingPrice.setText(offers.getShipping());
        condition.setText(offers.getCondition());
        link.setText(offers.getLink());
    }
}
