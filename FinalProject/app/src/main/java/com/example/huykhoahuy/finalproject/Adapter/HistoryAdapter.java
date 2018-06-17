package com.example.huykhoahuy.finalproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;

public class HistoryAdapter extends  RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    ArrayList<LotteryCompany>lotteryCompanies;
    Context context;

    public HistoryAdapter(ArrayList<LotteryCompany> lotteryCompanies, Context context) {
        this.lotteryCompanies = lotteryCompanies;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_lottery,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LotteryCompany company = lotteryCompanies.get(position);
        holder.tvName.setText(company.getName());
        holder.tvAddr.setText("Địa chỉ: "+company.getAddress());
        holder.tvPhone.setText("Điện thoại: "+company.getPhone());

    }

    @Override
    public int getItemCount() {
        return lotteryCompanies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName,tvAddr,tvPhone;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tv_lottery_company_name);
            tvAddr = (TextView)itemView.findViewById(R.id.tv_lottery_company_addr);
            tvPhone = (TextView)itemView.findViewById(R.id.tv_lottery_company_phone);
        }
    }
}
