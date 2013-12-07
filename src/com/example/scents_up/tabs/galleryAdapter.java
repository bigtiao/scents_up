package com.example.scents_up.tabs;

import com.example.scents_up.R;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class galleryAdapter extends BaseAdapter {

    private Integer[] img={
                    R.drawable.chanpin01,R.drawable.chanpin02,R.drawable.chanpin03
                    
    };
    private String[] str={"益生康肽","喜香汇食用调和油","天下五谷粗粮营养调和油"};
    private Context mContext;
    public galleryAdapter(Context c){
            mContext=c;
    }
    public int getCount() {
            // TODO Auto-generated method stub
            return img.length;
    }

    public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
    }

    public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if(convertView==null){
                    holder=new ViewHolder();
                    convertView=View.inflate(mContext, R.layout.gallery_pic_text, null);
                    holder.pic=(ImageView)convertView.findViewById(R.id.image);
                    holder.text=(TextView)convertView.findViewById(R.id.text);
                    convertView.setTag(holder);
            }else{
                    holder=(ViewHolder)convertView.getTag();
            }
            holder.pic.setImageResource(img[position]);
            holder.text.setText(str[position]);
            
            return convertView;
    }
 
     class ViewHolder {
                    private  ImageView pic;
                    private TextView text;
                    }
     
     
}