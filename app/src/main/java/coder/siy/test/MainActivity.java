package coder.siy.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;

import coder.siy.view.PwTextView;

/**
 * Created by Siy on 2018/4/23.
 *
 * @author Siy
 * @date 2018/4/23.
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private PwTextView pv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pv = findViewById(R.id.pwdviewId);

        findViewById(R.id.num1).setOnClickListener(this);
        findViewById(R.id.num2).setOnClickListener(this);
        findViewById(R.id.num3).setOnClickListener(this);
        findViewById(R.id.num4).setOnClickListener(this);
        findViewById(R.id.showOrg).setOnClickListener(this);
        findViewById(R.id.showCursor).setOnClickListener(this);
        findViewById(R.id.setBorderColor).setOnClickListener(this);
        findViewById(R.id.setBorderWidth).setOnClickListener(this);
        findViewById(R.id.setPwdLen).setOnClickListener(this);
        findViewById(R.id.setPwdWidth).setOnClickListener(this);
        findViewById(R.id.setPwdColor).setOnClickListener(this);
        findViewById(R.id.setContentColor).setOnClickListener(this);
        findViewById(R.id.setContentBoardColor).setOnClickListener(this);
        findViewById(R.id.setContentMargin).setOnClickListener(this);
        findViewById(R.id.setContentBoardWidth).setOnClickListener(this);
        findViewById(R.id.bg).setOnClickListener(this);
        findViewById(R.id.setSplitLineColor).setOnClickListener(this);
        findViewById(R.id.setBorderRadius).setOnClickListener(this);
        findViewById(R.id.setContentRadius).setOnClickListener(this);
        findViewById(R.id.setCursorColor).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.setCursorMargin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bg:
                pv.setBackgroundColor(Color.parseColor("#232224"));
                break;
            case R.id.num1:
                pv.setAppendText("1");
                break;
            case R.id.num2:
                pv.setAppendText("2");
                break;
            case R.id.num3:
                pv.setAppendText("3");
                break;
            case R.id.num4:
                pv.setAppendText("4");
                break;
            case R.id.showOrg:
                pv.showPwdText(!pv.isShowPwdText());
                break;
            case R.id.showCursor:
                if (pv.isShowCursor()) {
                    pv.hideCursor();
                } else {
                    pv.showCursor();
                }
                break;
            case R.id.setBorderColor:
                pv.setBorderColor(Color.TRANSPARENT);
                break;
            case R.id.setBorderWidth:
                pv.setBorderWidth(5);
                break;
            case R.id.setPwdLen:
                pv.setPwdLen(pv.getPwdLen() + 1);
                break;
            case R.id.setPwdColor:
                pv.setPwdColor(Color.WHITE);
                break;
            case R.id.setPwdWidth:
                pv.setPwdWidth(18);
                break;
            case R.id.setContentColor:
                pv.setContentColor(Color.parseColor("#141414"));
                break;
            case R.id.setContentBoardColor:
                pv.setContentBoardColor(Color.parseColor("#33FFFFFF"));
                break;
            case R.id.setContentBoardWidth:
                pv.setContentBoardWidth(2);
                break;
            case R.id.setContentMargin:
                pv.setContentMargin(dip2px(this, 10));
                break;
            case R.id.setSplitLineColor:
                pv.setSplitLineColor(Color.TRANSPARENT);
                break;
            case R.id.setContentRadius:
                pv.setContentRadius(6);
                break;
            case R.id.setBorderRadius:
                pv.setBorderRadius(6);
                break;
            case R.id.setCursorColor:
                pv.setCursorColor(Color.WHITE);
                break;
            case R.id.delete:
                pv.deleteLast();
                break;
            case R.id.setCursorMargin:
                pv.setCursorMargin(dip2px(this,20));
                break;
            default:
        }
    }

    /**
     * 密度无关像素 装换成 像素
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, context.getResources().getDisplayMetrics()) + 0.5f);
    }
}
