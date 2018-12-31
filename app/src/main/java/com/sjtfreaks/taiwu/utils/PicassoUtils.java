package com.sjtfreaks.taiwu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by jet on 2018-09-15.
 */

public class PicassoUtils {
    //默认加载

    public  static void loadImageView(Context mContext, String url, ImageView imageView){
        //加载图片
        Picasso.with(mContext)
                .load(url)
                .into(imageView);
    }
    //指定大小
    public  static void loadImageViewSize (Context mContext, String url, ImageView imageView, int width,int height){
        Picasso.with(mContext)
                .load(url)
                .resize(width,height)
                .centerCrop()
                .into(imageView);
    }
    //加载默认图片
    public static  void loadImageViewHoder(Context mContext, String url, ImageView imageView,int loadImg,int errorImg){
        Picasso.with(mContext)
                .load(url)
                .placeholder(loadImg)
                .error(errorImg)
                .into(imageView);
    }
    //裁剪图片
    public  static void loadImageViewCrop(Context mContext,String url,ImageView imageView){
        Picasso.with(mContext)
                .load(url)
                .transform(new CropSquareTransformation())
                .into(imageView);
    }


    // 按比例
    public static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }
        @Override public String key() {
            return "square()";
        }
    }
}
