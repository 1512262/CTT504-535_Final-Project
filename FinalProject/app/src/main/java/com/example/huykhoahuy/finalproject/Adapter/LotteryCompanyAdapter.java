package com.example.huykhoahuy.finalproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Fragment.LotteryCenterFragment;
import com.example.huykhoahuy.finalproject.Interface.ItemClickListener;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;

public class LotteryCompanyAdapter extends RecyclerView.Adapter<LotteryCompanyAdapter.ViewHolder> implements Filterable {

    private ArrayList<LotteryCompany>lotteryCompanies;
    private ArrayList<LotteryCompany>lotteryCompaniesFiltered;
    private ItemClickListener clickListener;
    Context context;




    public LotteryCompanyAdapter(ArrayList<LotteryCompany> lotteryCompanies, Context context) {
        this.lotteryCompanies = lotteryCompanies;
        this.context = context;
        this.lotteryCompaniesFiltered = lotteryCompanies; // Khởi tạo cho lotteryCompaniesFiltered
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_lottery,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LotteryCompany company = lotteryCompaniesFiltered.get(position); // Chọn lotteryCompaniesFiltered thay cho lotteryCompanies để nhận kết quả search
        holder.tvName.setText(company.getName());
        holder.tvAddr.setText("Địa chỉ: "+company.getAddress());
        holder.tvPhone.setText("Điện thoại: "+company.getPhone());


    }

    @Override
    public int getItemCount() {
        return lotteryCompaniesFiltered.size(); // Chọn lotteryCompaniesFiltered thay cho lotteryCompanies để nhận kết quả search
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvName,tvAddr,tvPhone;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tv_lottery_company_name);
            tvAddr = (TextView)itemView.findViewById(R.id.tv_lottery_company_addr);
            tvPhone = (TextView)itemView.findViewById(R.id.tv_lottery_company_phone);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }



    // thêm getFilter, muốn có getFilter thì thêm implements Filterable
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String constraintString = constraint.toString();
                if (constraintString.isEmpty()) {
                    lotteryCompaniesFiltered = lotteryCompanies;
                } else {
                    ArrayList<LotteryCompany> filteredList = new ArrayList<>();
                    for (LotteryCompany row : lotteryCompanies) {
                        if (row.getName().toLowerCase().contains(constraintString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    lotteryCompaniesFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = lotteryCompaniesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lotteryCompaniesFiltered = (ArrayList<LotteryCompany>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    // Kết thúc việc thêm getFilter

}
