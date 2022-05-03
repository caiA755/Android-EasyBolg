package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.logging.Handler;

public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView>{
    private List<Article> articles;
    private Context context;
    private @LayoutRes
    int layout;
    private  @StringRes
    int txtAID;

    private OnItemClickListener mOnItemClickListener;
    public static interface OnItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public MasonryAdapter(List<Article> list,Context context, @LayoutRes int layout) {
        this.articles=list;
        this.context=context;
        this.layout=layout;
    }
    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
       // mOnItemClickListener.onItemClick(view, i);
        MasonryView viewHolder = new MasonryView(
               view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onItemLongClick(v);
                return true;
            }
        });
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, @SuppressLint("RecyclerView") int position) {
        try{
              Glide.with(context).load(articles.get(position).getPostImg()).into(masonryView.imageView);
              masonryView.textView1.setText(articles.get(position).getTitle());
              masonryView.textView2.setText(articles.get(position).getAuthor());
              masonryView.textView3.setText(articles.get(position).getContent());
              masonryView.textView4.setText(articles.get(position).getTime());
              masonryView.txtAid.setText(articles.get(position).getAID());
          }catch (Exception e){
              e.printStackTrace();
          }
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class MasonryView extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView txtAid;


        public MasonryView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.img);
            textView1= (TextView) itemView.findViewById(R.id.title);
            textView2= (TextView) itemView.findViewById(R.id.author);
            textView3= (TextView) itemView.findViewById(R.id.content);
            textView4= (TextView) itemView.findViewById(R.id.publishTime);
            txtAid=(TextView) itemView.findViewById(R.id.aid);

        }

    }
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }


}

class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
        if(parent.getChildAdapterPosition(view)==0){
            outRect.top=space;
        }
    }
}
