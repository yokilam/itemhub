package nyc.c4q.itemhub.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nyc.c4q.itemhub.R;
import nyc.c4q.itemhub.model.Offers;

public class MerchantAdapter extends RecyclerView.Adapter<MerchantViewholder> {
    private List<Offers> merchantList;

    public MerchantAdapter(List <Offers> merchantList) {
        this.merchantList = merchantList;
    }

    @NonNull
    @Override
    public MerchantViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_itemview, parent, false);
        return new MerchantViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantViewholder holder, int position) {
        holder.onBind(merchantList.get(position));
    }

    @Override
    public int getItemCount() {
        return merchantList.size();
    }

}
