package com.third.party.library.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.third.party.library.views.GlideCircleTransform;
import com.third.party.library.views.GlideRoundTransform;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * Created by Administrator on 2016/1/9.
 */
public class GlideUtil {
    static RequestManager glideRequest;

    //圆形
    public static void load(Context context, String url, ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(context)).into(iv);
    }


    public static void load(Context context, int url, ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(context)).into(iv);
    }

    public static void load(Context context, Fragment f, String url, ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(context)).into(iv);
    }

    public static void load(Context context, Fragment f, int url, ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(context)).into(iv);
    }

    //圆形
    public static void load(Context context, String url, int errurl , ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(errurl).transform(new GlideCircleTransform(context)).into(iv);
    }


    public static void load(Context context, Fragment f, String url, int errurl , ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(errurl).transform(new GlideCircleTransform(context)).into(iv);
    }

    //长方形
    public static void loadF(Context context, String url, ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
    }

    public static void loadF(Context context, int url, ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
    }

    public static void loadF(Context context, Fragment f, String url, ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
    }

    public static void loaFd(Context context, Fragment f, int url, ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
    }

    public static void loadF(Context context, String url, int errurl , ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).error(errurl).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
    }



    public static void loadF(Context context, Fragment f, String url, int errurl , ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).error(errurl).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
    }

    //圆角
    public static void loadRound(Context context, String url, int radius, ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new CenterCrop(context), new GlideRoundTransform(context, radius)).into(iv);
    }

    public static void loadRound(Context context, int url, int radius, ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new CenterCrop(context), new GlideRoundTransform(context, radius)).into(iv);
    }

    public static void loadRound(Context context, Fragment f, String url, int radius, ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideRoundTransform(context, radius)).into(iv);
    }

    public static void loadRound(Context context, Fragment f, int url, int radius, ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideRoundTransform(context, radius)).into(iv);
    }

    //圆角
    public static void loadRound(Context context, String url, int errurl , int radius, ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).error(errurl).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new CenterCrop(context), new GlideRoundTransform(context, radius)).into(iv);
    }



    public static void loadRound(Context context, Fragment f, String url, int errurl , int radius, ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).error(errurl).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideRoundTransform(context, radius)).into(iv);
    }

    //模糊
    public static void loadblur(Context context, Fragment f, String url, int errurl , int radius, ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(url).error(errurl).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new BlurTransformation(context, radius)).into(iv);
    }
    public static void loadblur(Context context, Fragment f, int errurl , int radius, ImageView iv){
        glideRequest = Glide.with(f);
        glideRequest.load(errurl).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new BlurTransformation(context,radius)).into(iv);
    }

    public static void loadblur(Context context, String url, int errurl , int radius, ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(url).error(errurl).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new BlurTransformation(context,radius)).into(iv);
    }

    public static void loadblur(Context context, int errurl , int radius, ImageView iv){
        glideRequest = Glide.with(context);
        glideRequest.load(errurl).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new BlurTransformation(context,radius)).into(iv);
    }


    static ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(View view) {
            // if it's a custom view class, cas tit here
            // then find subviews and do the animations
            // here, we just use the entire view for the fade animation
            view.setScaleX(0f);
            view.setScaleY(0f);
//            view.setAlpha(0f);

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "scale", 0f, 1f );
            fadeAnim.setDuration( 1500 );
            fadeAnim.start();
        }
    };





}
