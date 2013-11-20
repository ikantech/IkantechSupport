package com.ikantech.support.proxy;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ikantech.localservicesupport.R;

public class YiDialogProxy {
	private static int mMsgDialogLayoutRes = -1;
	private static int mMsgDialogTheme = -1;

	private Context mContext;
	private Dialog mMsgDialog;

	private View mMsgDialogTextRoot;
	private View mMsgDialogBtnRoot;
	private TextView mMsgDialogTitle;
	private TextView mMsgDialogDetailMsg;

	private Button mMsgDialogBtnLeft;
	private Button mMsgDialogBtnRight;

	private OnClickListener mMsgBtnLeftClickListener;
	private OnClickListener mMsgBtnRightClickListener;

	public YiDialogProxy(Context context) {
		if (context == null) {
			throw new NullPointerException("context non-null");
		}
		mContext = context;
		mMsgDialog = null;
		mMsgBtnLeftClickListener = null;
		mMsgBtnRightClickListener = null;
	}

	public static void setMsgDialogLayoutRes(int res) {
		mMsgDialogLayoutRes = res;
	}

	public static void setMsgDialogTheme(int theme) {
		mMsgDialogTheme = theme;
	}

	protected View createMsgDialogView() {
		return LayoutInflater.from(mContext).inflate(mMsgDialogLayoutRes, null);
	}

	protected Dialog createMsgDialog(View view) {
		Dialog ret = new Dialog(mContext, mMsgDialogTheme);
		ret.setContentView(view);
		return ret;
	}

	protected void initMsgDialog() {
		if (mMsgDialog == null) {
			View view = createMsgDialogView();
			mMsgDialogTextRoot = view.findViewById(R.id.msg_dialog_text_root);
			mMsgDialogBtnRoot = view.findViewById(R.id.msg_dialog_btn_root);
			mMsgDialogTitle = (TextView) view
					.findViewById(R.id.msg_dialog_title);
			mMsgDialogDetailMsg = (TextView) view
					.findViewById(R.id.msg_dialog_detail_msg);
			mMsgDialogBtnLeft = (Button) view
					.findViewById(R.id.msg_dialog_btn_left);
			mMsgDialogBtnLeft.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mMsgDialog != null && mMsgDialog.isShowing()) {
						mMsgDialog.dismiss();
					}
					if (mMsgBtnLeftClickListener != null) {
						mMsgBtnLeftClickListener.onClick(v);
					}
				}
			});

			mMsgDialogBtnRight = (Button) view
					.findViewById(R.id.msg_dialog_btn_right);
			mMsgDialogBtnRight.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mMsgDialog != null && mMsgDialog.isShowing()) {
						mMsgDialog.dismiss();
					}
					if (mMsgBtnRightClickListener != null) {
						mMsgBtnRightClickListener.onClick(v);
					}
				}
			});
			mMsgDialog = createMsgDialog(view);
		}
	}

	public void showMsgDialog() {
		initMsgDialog();
		mMsgDialog.show();
	}

	public void showMsgDialog(int width, int height) {
		showMsgDialog();
		mMsgDialog.getWindow().setLayout(width, height);
	}

	public void setMsgDialogCanceledOnTouchOutside(boolean v) {
		initMsgDialog();
		mMsgDialog.setCanceledOnTouchOutside(v);
	}

	public void setMsgDialogTitle(int resId) {
		initMsgDialog();
		mMsgDialogTitle.setText(resId);
	}

	public void setMsgDialogTitle(String str) {
		initMsgDialog();
		mMsgDialogTitle.setText(str);
	}

	public void setMsgDialogDetailMsg(int resId) {
		initMsgDialog();
		mMsgDialogDetailMsg.setText(resId);
	}

	public void setMsgDialogDetailMsg(String str) {
		initMsgDialog();
		mMsgDialogDetailMsg.setText(str);
	}

	public void setMsgDialogBtnLeftText(int resId) {
		initMsgDialog();
		mMsgDialogBtnLeft.setText(resId);
	}

	public void setMsgDialogBtnLeftText(String str) {
		initMsgDialog();
		mMsgDialogBtnLeft.setText(str);
	}

	public void setMsgDialogBtnRightText(int resId) {
		initMsgDialog();
		mMsgDialogBtnRight.setText(resId);
	}

	public void setMsgDialogBtnRightText(String str) {
		initMsgDialog();
		mMsgDialogBtnRight.setText(str);
	}

	public void hideMsgDialogTitle() {
		initMsgDialog();
		mMsgDialogTitle.setVisibility(View.GONE);
	}

	public void showMsgDialogTitle() {
		initMsgDialog();
		mMsgDialogTitle.setVisibility(View.VISIBLE);
	}

	public void hideMsgDialogDetailMsg() {
		initMsgDialog();
		mMsgDialogDetailMsg.setVisibility(View.GONE);
	}

	public void showMsgDialogDetailMsg() {
		initMsgDialog();
		mMsgDialogDetailMsg.setVisibility(View.VISIBLE);
	}

	public void hideMsgDialogTextRoot() {
		initMsgDialog();
		mMsgDialogTextRoot.setVisibility(View.GONE);
	}

	public void showMsgDialogTextRoot() {
		initMsgDialog();
		mMsgDialogTextRoot.setVisibility(View.VISIBLE);
	}

	public void hideMsgDialogBtnRoot() {
		initMsgDialog();
		mMsgDialogBtnRoot.setVisibility(View.GONE);
	}

	public void showMsgDialogBtnRoot() {
		initMsgDialog();
		mMsgDialogBtnRoot.setVisibility(View.VISIBLE);
	}

	public void hideMsgDialogBtnLeft() {
		initMsgDialog();
		mMsgDialogBtnLeft.setVisibility(View.GONE);
	}

	public void showMsgDialogBtnLeft() {
		initMsgDialog();
		mMsgDialogBtnLeft.setVisibility(View.VISIBLE);
	}

	public void hideMsgDialogBtnRight() {
		initMsgDialog();
		mMsgDialogBtnRight.setVisibility(View.GONE);
	}

	public void showMsgDialogBtnRight() {
		initMsgDialog();
		mMsgDialogBtnRight.setVisibility(View.VISIBLE);
	}

	public void cancelMsgDialog() {
		if (mMsgDialog != null && mMsgDialog.isShowing()) {
			mMsgDialog.dismiss();
		}
	}

	public interface YiDialogProxiable {
		void showMsgDialog();

		void showMsgDialog(int width, int height);

		YiDialogProxy getMsgDialog();

		void cancelMsgDialog();
	}
}
