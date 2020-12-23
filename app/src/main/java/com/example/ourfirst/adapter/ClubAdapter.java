package com.example.ourfirst.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ourfirst.Activity.Club_detailActivity;
import com.example.ourfirst.R;
import com.example.ourfirst.bean.ClubBean;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ViewHolder> {
    Context mcontext;
    List<ClubBean> arr2;

    public ClubAdapter(Context mcontext, List<ClubBean> arr2) {
        this.mcontext = mcontext;
        this.arr2 = arr2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.club_lists,parent,false);
        ViewHolder mholder = new ViewHolder(view);
        return mholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder mholder, int position) {
        final ClubBean clubBean = arr2.get(position);
        mholder.club_name.setText(clubBean.getClub_name());
        mholder.clublevel.setText(clubBean.getLevel());
        mholder.clubcampus.setText(clubBean.getCampus());
        mholder.clubkind.setText(clubBean.getKind());
        mholder.clubintos.setText(clubBean.getClub_intro());
        mholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, Club_detailActivity.class);
                intent.putExtra("seri", (Parcelable) clubBean);
                mcontext.startActivity(intent);
                ((Activity)mcontext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView club_name,clublevel,clubcampus,clubintos,clubkind;

        public ViewHolder(View itemView) {
            super(itemView);
            club_name = itemView.findViewById(R.id.clubname);
            clublevel = itemView.findViewById(R.id.clublevel);
            clubcampus = itemView.findViewById(R.id.clubcampus);
            clubkind = itemView.findViewById(R.id.clubkind);
            clubintos = itemView.findViewById(R.id.clubintos);
        }
    }
}
