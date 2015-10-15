package com.ghimtim.changelayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
    private RelativeLayout root;
    private ImageView logo;
    private TextView label;
    private EditText input;
    private int rootBottom = Integer.MIN_VALUE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		root = (RelativeLayout) findViewById(R.id.root);
		logo = (ImageView) findViewById(R.id.logo);
		label = (TextView) findViewById(R.id.label1);
		input = (EditText) findViewById(R.id.input);
//		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//		boolean isOpen=imm.isActive();
		root.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				Rect r = new Rect();
				root.getGlobalVisibleRect(r);
				// 进入Activity时会布局，第一次调用onGlobalLayout，先记录开始软键盘没有弹出时底部的位置
				if(r.bottom == Integer.MIN_VALUE){
					rootBottom = r.bottom;
					return;
				}
				Log.i("LJT", "******"+r.bottom+rootBottom);
				 // adjustResize，软键盘弹出后高度会变小
				if(r.bottom < rootBottom){
					RelativeLayout.LayoutParams lp = (LayoutParams) logo.getLayoutParams();
					 // 如果Logo不是水平居中，说明是因为接下来的改变Logo大小位置导致的再次布局，忽略掉，否则无限循环
					if(lp.getRules()[RelativeLayout.CENTER_HORIZONTAL] != 0){
						// Logo显示到左上角
						lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);// 取消水平居中
						 lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT); // 左对齐

	                        // 缩小Logo为1/2
	                        int height = logo.getHeight(); // getMeasuredHeight()
	                        int width = logo.getWidth();
	                        lp.width = width / 2;
	                        lp.height = height / 2;
	                        logo.setLayoutParams(lp);

	                        // Logo下的文字
	                        RelativeLayout.LayoutParams labelParams = (LayoutParams) label.getLayoutParams();
	                        labelParams.addRule(RelativeLayout.CENTER_HORIZONTAL, 0); // 取消水平居中
	                        labelParams.addRule(RelativeLayout.BELOW, 0); // 取消显示到logo的下方
	                        labelParams.addRule(RelativeLayout.RIGHT_OF, R.id.logo); // 显示到Logo的右方
	                        labelParams.addRule(RelativeLayout.CENTER_VERTICAL); // 垂直居中
	                        label.setLayoutParams(labelParams);
				}
				}else{ // 软键盘收起或初始化时
					 RelativeLayout.LayoutParams lp = (LayoutParams) logo.getLayoutParams();
	                    // 如果没有水平居中，说明是软键盘收起，否则是开始时的初始化或者因为此处if条件里的语句修改控件导致的再次布局，忽略掉，否则无限循环
	                    if (lp.getRules()[RelativeLayout.CENTER_HORIZONTAL] == 0) {
	                        // 居中Logo
	                        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
	                        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);

	                        // 还原Logo为原来大小
	                        int height = logo.getHeight();
	                        int width = logo.getWidth();
	                        lp.width = width * 2;
	                        lp.height = height * 2;
	                        logo.setLayoutParams(lp);

	                        // Logo下的文字
	                        RelativeLayout.LayoutParams labelParams = (LayoutParams) label.getLayoutParams();
	                        labelParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // 设置水平居中
	                        labelParams.addRule(RelativeLayout.BELOW, R.id.logo); // 设置显示到Logo下面
	                        labelParams.addRule(RelativeLayout.RIGHT_OF, 0); // 取消显示到Logo右面
	                        labelParams.addRule(RelativeLayout.CENTER_VERTICAL, 0); // 取消垂直居中
	                        label.setLayoutParams(labelParams);
	                    }
				}
			}
		});
	}


}
