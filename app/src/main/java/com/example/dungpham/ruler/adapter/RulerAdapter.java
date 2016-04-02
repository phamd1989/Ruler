//package com.example.dungpham.ruler.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.dungpham.ruler.R;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
///**
// * Created by dungpham on 4/1/16.
// */
//public class RulerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private String[] mData;
//
//    public RulerAdapter(String[] data) {
//        mData = data;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ruler_marker, parent, false);
//        RecyclerView.ViewHolder holder = new ViewHolder(v);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof ViewHolder) {
//            String text = mData[position];
//            if ((position + 1) % 8 == 0) text = "--------" + text;
//            else if ((position + 1) % 4 == 0) text = "----" + text;
//            else if ((position + 1) % 2 == 0) text = "--" + text;
//            else text = "-" + text;
//            ((ViewHolder) holder).mMarker.setText(text);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.length;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        @Bind(R.id.marker_text)
//        public TextView mMarker;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//}
