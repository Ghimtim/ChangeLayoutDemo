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
				// ����Activityʱ�᲼�֣���һ�ε���onGlobalLayout���ȼ�¼��ʼ�����û�е���ʱ�ײ���λ��
				if(r.bottom == Integer.MIN_VALUE){
					rootBottom = r.bottom;
					return;
				}
				Log.i("LJT", "******"+r.bottom+rootBottom);
				 // adjustResize������̵�����߶Ȼ��С
				if(r.bottom < rootBottom){
					RelativeLayout.LayoutParams lp = (LayoutParams) logo.getLayoutParams();
					 // ���Logo����ˮƽ���У�˵������Ϊ�������ĸı�Logo��Сλ�õ��µ��ٴβ��֣����Ե�����������ѭ��
					if(lp.getRules()[RelativeLayout.CENTER_HORIZONTAL] != 0){
						// Logo��ʾ�����Ͻ�
						lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);// ȡ��ˮƽ����
						 lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT); // �����

	                        // ��СLogoΪ1/2
	                        int height = logo.getHeight(); // getMeasuredHeight()
	                        int width = logo.getWidth();
	                        lp.width = width / 2;
	                        lp.height = height / 2;
	                        logo.setLayoutParams(lp);

	                        // Logo�µ�����
	                        RelativeLayout.LayoutParams labelParams = (LayoutParams) label.getLayoutParams();
	                        labelParams.addRule(RelativeLayout.CENTER_HORIZONTAL, 0); // ȡ��ˮƽ����
	                        labelParams.addRule(RelativeLayout.BELOW, 0); // ȡ����ʾ��logo���·�
	                        labelParams.addRule(RelativeLayout.RIGHT_OF, R.id.logo); // ��ʾ��Logo���ҷ�
	                        labelParams.addRule(RelativeLayout.CENTER_VERTICAL); // ��ֱ����
	                        label.setLayoutParams(labelParams);
				}
				}else{ // �����������ʼ��ʱ
					 RelativeLayout.LayoutParams lp = (LayoutParams) logo.getLayoutParams();
	                    // ���û��ˮƽ���У�˵������������𣬷����ǿ�ʼʱ�ĳ�ʼ��������Ϊ�˴�if�����������޸Ŀؼ����µ��ٴβ��֣����Ե�����������ѭ��
	                    if (lp.getRules()[RelativeLayout.CENTER_HORIZONTAL] == 0) {
	                        // ����Logo
	                        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
	                        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);

	                        // ��ԭLogoΪԭ����С
	                        int height = logo.getHeight();
	                        int width = logo.getWidth();
	                        lp.width = width * 2;
	                        lp.height = height * 2;
	                        logo.setLayoutParams(lp);

	                        // Logo�µ�����
	                        RelativeLayout.LayoutParams labelParams = (LayoutParams) label.getLayoutParams();
	                        labelParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // ����ˮƽ����
	                        labelParams.addRule(RelativeLayout.BELOW, R.id.logo); // ������ʾ��Logo����
	                        labelParams.addRule(RelativeLayout.RIGHT_OF, 0); // ȡ����ʾ��Logo����
	                        labelParams.addRule(RelativeLayout.CENTER_VERTICAL, 0); // ȡ����ֱ����
	                        label.setLayoutParams(labelParams);
	                    }
				}
			}
		});
	}


}
