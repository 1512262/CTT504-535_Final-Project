package com.example.huykhoahuy.finalproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huykhoahuy.finalproject.Class.Lottery;
import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Interface.ItemClickListener;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;

public class HistoryAdapter extends  RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private ArrayList<Lottery>lotteries;
    Context context;
    private HistoryAdapterListener listener;
    private ItemClickListener clickListener;

    public interface HistoryAdapterListener{
        void onLotterySelected(Lottery lottery);

    }

    public HistoryAdapter(Context context) {
        this.lotteries = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_history,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lottery lottery = lotteries.get(position);
//        holder.ivLotteryImage.setImageDrawable();
        holder.tvLotteryCode.setText(lottery.Lottery_Code);
        holder.tvLotteryDate.setText(lottery.getLottery_Date());
        holder.tvLotteryProvinceID.setText(lottery.getLottery_Province_ID());
        holder.tvLotteryPrize.setText(lottery.getLottery_Prize());
        holder.tvCheckDate.setText(lottery.getLottery_Check_Date());
        holder.tvCheckTime.setText(lottery.getLottery_Check_Time());

    }

    @Override
    public int getItemCount() {
        return lotteries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView ivLotteryImage;
        TextView tvLotteryCode,tvLotteryDate,tvLotteryProvinceID,tvLotteryPrize;
        TextView tvCheckDate,tvCheckTime;
        public ViewHolder(View itemView) {
            super(itemView);
            ivLotteryImage = (ImageView)itemView.findViewById(R.id.iv_lottery_image);
            tvLotteryCode = (TextView)itemView.findViewById(R.id.tv_lottery_code);
            tvLotteryDate = (TextView)itemView.findViewById(R.id.tv_lottery_date);
            tvLotteryProvinceID = (TextView)itemView.findViewById(R.id.tv_lottery_province_id);
            tvLotteryPrize = (TextView)itemView.findViewById(R.id.tv_lottery_prize);
            tvCheckDate = (TextView)itemView.findViewById(R.id.tv_check_date);
            tvCheckTime = (TextView)itemView.findViewById(R.id.tv_check_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public void setLotteries(ArrayList<Lottery> lotteryArrayList) {
        this.lotteries = lotteryArrayList;
        notifyDataSetChanged();
    }
}
