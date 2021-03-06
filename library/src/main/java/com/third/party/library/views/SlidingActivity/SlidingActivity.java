package com.third.party.library.views.SlidingActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import com.third.party.library.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by chenjishi on 14-3-17.
 */
public class SlidingActivity extends FragmentActivity implements SlidingLayout.SlidingListener {
    private View mPreview;

    private float mInitOffset;
    private boolean hideTitle = false;
    private int titleResId = -1;

    private String mBitmapId;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.slide_layout);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        LayoutInflater inflater = LayoutInflater.from(this);
        mInitOffset = -(1.f / 3) * metrics.widthPixels;

        mPreview = findViewById(R.id.iv_preview);
        FrameLayout contentView = (FrameLayout) findViewById(R.id.content_view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(MATCH_PARENT,
                MATCH_PARENT, Gravity.BOTTOM);
        contentView.addView(inflater.inflate(layoutResID, null), lp);
        final SlidingLayout slideLayout = (SlidingLayout) findViewById(R.id.slide_layout);
        slideLayout.setShadowResource(R.drawable.sliding_back_shadow);
        slideLayout.setSlidingListener(this);
        slideLayout.setEdgeSize((int) (metrics.density * 20));

        mBitmapId = getIntent().getExtras().getString("bitmap_id");
        Bitmap bitmap = IntentUtils.getInstance().getBitmap(mBitmapId);
        if (null != bitmap) {
            if (Build.VERSION.SDK_INT >= 16) {
                mPreview.setBackground(new BitmapDrawable(bitmap));
            } else {
                mPreview.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }

            IntentUtils.getInstance().setIsDisplayed(mBitmapId, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IntentUtils.getInstance().setIsDisplayed(mBitmapId, false);
    }


    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        if (slideOffset <= 0) {
        } else if (slideOffset < 1) {
            mPreview.setTranslationX(mInitOffset * (1 - slideOffset));
        } else {
            mPreview.setTranslationX(0);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    protected void setContentView(int layoutResID, int titleResId) {
        this.titleResId = titleResId;
        setContentView(layoutResID);
    }

    protected void setContentView(int layoutResID, boolean hideTitle) {
        this.hideTitle = hideTitle;
        setContentView(layoutResID);
    }
}
