package com.third.party.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZY on 2017/7/24.\
 * 历史搜索 搜索 流式布局
 */

public class FlowLayout extends ViewGroup {

    /**
     * 用来保存每行View的List
     */
    private List<List<View>> mViewLinesList = new ArrayList<>();

    /**
     * 用来保存行高的List
     */
    private List<Integer> mLineHeights = new ArrayList<>();

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    /**
     * 负责测量子控件的宽高,根据子控件的宽高，来设置自己的宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取父容器给子View(FlowLayout)设置的测量模式和大小

        int iWidthMode = MeasureSpec.getMode(widthMeasureSpec);//宽的测量模式
        int iWidthSize = MeasureSpec.getSize(widthMeasureSpec);//宽的测量大小

        int iHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int iHeightSize = MeasureSpec.getSize(heightMeasureSpec);


        int iMeasureW = 0;//最终测量出来的自身(FlowLayout)的宽
        int iMeasureH = 0;//最终测量出来的自身(FlowLayout)的高
        if(iWidthMode==MeasureSpec.EXACTLY&&iHeightMode==MeasureSpec.EXACTLY){
            iMeasureW=iWidthSize;
            iMeasureH=iHeightSize;
        }else {
            int iChildCount = getChildCount();
            int iChildWidth = 0;//测量出来的子View的宽
            int iChildHeight = 0;//测量出来的子View的高
            int iCurLineW = 0;//记录当前行的宽度(是通过累加这一行所有View的宽度得来的)
            int iCurLineH = 0;//记录当前行的高度
            List<View> viewList = new ArrayList<>();
            for (int i=0;i<iChildCount;i++){
                View childView = getChildAt(i);
                measureChild(childView,widthMeasureSpec,heightMeasureSpec);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                iChildWidth = childView.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
                iChildHeight = childView.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;

                //当(不断累加的行的宽度+正在算的子View的宽度)>父容器的宽度时，就要换行
                if (iCurLineW+iChildWidth>iWidthSize){
                    /**************************记录当前行的信息*********************/
                    //iMeasureW(历史最大宽度，也是最终的FlowLayout的宽度)
                    //记录当前行的信息(取历史测量出的宽度和当前这行的宽度中的最大值)
                    iMeasureW = Math.max(iMeasureW,iCurLineW);
                    iMeasureH +=iCurLineH;//行高累加

                    //将当前行的viewList添加至总的mViewList
                    mViewLinesList.add(viewList);
                    //当前行的行高添加到总的行高里
                    mLineHeights.add(iCurLineH);

                    /**************************记录新建行的信息*********************/
                    //1、新建一行,重新赋值新建的那一行的宽高
                    iCurLineW = iChildWidth;//新建行的宽=当前子View的宽
                    iCurLineH = iChildHeight;

                    //2、新建一行的viewList初始化，添加第一个childView
                    viewList = new ArrayList<>();
                    viewList.add(childView);

//                    /**************************换行时，如果正好是最后一个需要换行*********************/
//                    //需要特别注意，特别容易出错，负责会少一行
//                    if(i==iChildCount-1){
//                        iMeasureW = Math.max(iMeasureW,iCurLineW);
//                        iMeasureH +=iCurLineH;//行高累加
//
//                        //将当前行的viewList添加至总的mViewList
//                        mViewLinesList.add(viewList);
//                        //当前行的行高添加到总的行高里
//                        mLineHeights.add(iCurLineH);
//                    }
                }else {//没有超过，就行内宽高累加
                    /**************************1记录行内的信息*********************/
                    iCurLineW+=iChildWidth;
                    //取当前行高与当前View的行高中的大值
                    iCurLineH = Math.max(iCurLineH,iChildHeight);

                    /**************************2添加至当前行的viewList里*********************/
                    viewList.add(childView);
                }

                //注意别加错地方
                /**************************换行时，如果正好是最后一个需要换行*********************/
                //需要特别注意，特别容易出错，负责会少一行
                if(i==iChildCount-1){
                    //为啥比较历史最大宽和当前行宽的大小(因为如果只有一个view,那么历史最大宽首先为0
                    // ，还有下一行宽可能大于或者小于之前的历史最大宽度)
                    iMeasureW = Math.max(iMeasureW,iCurLineW);
                    iMeasureH +=iCurLineH;//行高累加

                    //将当前行的viewList添加至总的mViewList
                    mViewLinesList.add(viewList);
                    //当前行的行高添加到总的行高里
                    mLineHeights.add(iCurLineH);
                }
            }
        }

        setMeasuredDimension(iMeasureW,iMeasureH);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int iLineSize=mViewLinesList.size();
        int iChildLeft;
        int iChildTop;
        int iChildRight;
        int iChildBottom;
        int iCurLeft = 0;
        int iCurTop = 0;

        for (int i=0;i<iLineSize;i++){
            List<View> viewList = mViewLinesList.get(i);
            for (int j=0;j<viewList.size();j++){
                View childView = viewList.get(j);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                iChildLeft = iCurLeft+layoutParams.leftMargin;
                iChildTop = iCurTop+layoutParams.topMargin;
                //下面少写了childView.getMeasuredWidth()的child导致背景连成一片
                iChildRight = iChildLeft+childView.getMeasuredWidth();
                iChildBottom = iChildTop+childView.getMeasuredHeight();
                childView.layout(iChildLeft,iChildTop,iChildRight,iChildBottom);

                //摆完一个View后，起始点要跟着挪
                iCurLeft += childView.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            }
            //画完一行后iCurTop也要累加
            iCurTop += mLineHeights.get(i);
            iCurLeft = 0;//因为换行了，所以iCurLeft归0
        }
        //特别注意:一定得清空，否则没有效果
        mViewLinesList.clear();
        mLineHeights.clear();
    }
}
