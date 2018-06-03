package nyc.c4q.itemhub.rv;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import nyc.c4q.itemhub.R;
import nyc.c4q.itemhub.model.Offers;

public class MerchantViewholder extends RecyclerView.ViewHolder {

    private TextView merchantName, domain, price, shippingPrice,
            condition, companyName, merchantPrice, buyNow;
    private FoldingCell foldingCell;

    public MerchantViewholder(View itemView) {
        super(itemView);

        foldingCell = itemView.findViewById(R.id.folding_cell);
        merchantName = itemView.findViewById(R.id.merchant_name);
        domain = itemView.findViewById(R.id.domain);
        price = itemView.findViewById(R.id.price);
        shippingPrice = itemView.findViewById(R.id.shipping_price);
        condition = itemView.findViewById(R.id.condition);
        companyName = itemView.findViewById(R.id.company_name);
        merchantPrice = itemView.findViewById(R.id.merchant_price);
        buyNow = itemView.findViewById(R.id.content_request_btn);
    }

    public void onBind(final Offers offers) {
        merchantName.setText(offers.getMerchant());
        domain.setText(offers.getDomain());
        if (offers.getPrice().equals("0")) {
            price.setText("$-");
        } else {
            price.setText(addDollarSign(offers.getPrice()));
        }
        merchantPrice.setText(addDollarSign(offers.getPrice()));
        shippingPrice.setText(addDollarSign(offers.getShipping()));
        condition.setText(offers.getCondition());
        companyName.setText(offers.getMerchant());

        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);

            }
        });

        final Uri webpage = Uri.parse(offers.getLink());
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                itemView.getContext().startActivity(intent);
            }
        });
    }

    private String addDollarSign(String price) {
        StringBuilder dollarString = new StringBuilder();
        dollarString.append("$").append(price);
        return dollarString.toString();
    }
}
