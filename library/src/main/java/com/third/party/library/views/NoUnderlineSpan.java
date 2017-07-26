package com.third.party.library.views;

import android.text.TextPaint;
import android.text.style.UnderlineSpan;

/**
 * 无下划线的Span
 * 
 * @author Jason
 *
 */
public class NoUnderlineSpan extends UnderlineSpan {
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }
}
