package com.qiyue.liveeducation.net;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.third.party.library.views.CircleProgressDialog;

/**
 *
 * HttpDialog
 */
public class HttpDialogUtils {

	/**
	 * showDialog & dismissDialog 在http 请求开始的时候显示，结束的时候消失
	 * 当然不是必须需要显示的 !
	 */
	public static void showDialog(final Context mContext) {
		if (mContext == null || !(mContext instanceof Activity) || ((Activity) mContext).isFinishing())
			return;
		showCircleProgressDialog(mContext);
	}

	public static void dismissDialog(final Context mContext) {
		if (mContext == null || !(mContext instanceof Activity) || ((Activity) mContext).isFinishing())
			return;             //maybe not good !
		dismissCircleProgressDialog();
	}

	/**
	 * 转圈圈dialog
	 **/
	protected static CircleProgressDialog mCircleProgressDialog = null;

	/**
	 * 转圈圈dialog show
	 */
	protected static void showCircleProgressDialog(Context mContext) {
		if (mCircleProgressDialog == null) {
			mCircleProgressDialog = new CircleProgressDialog(mContext);
		}
		if (!mCircleProgressDialog.isShowing()) {
			mCircleProgressDialog.show();
		}
	}

	/**
	 * 转圈圈dialog dismiss
	 */
	protected static void dismissCircleProgressDialog() {
		if (mCircleProgressDialog != null) {
			mCircleProgressDialog.dismiss();
		}
		mCircleProgressDialog = null;
	}



}
