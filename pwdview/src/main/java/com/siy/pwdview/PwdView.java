package com.siy.pwdview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 *
 * 整理思路：先绘制一整块区域，然后绘制一个一个正方形，然后在正方形中绘制密码
 *
 *
 * 密码输入框
 * <p>
 * Created by Siy on 2017/8/18.
 *
 * @author Siy
 */
@SuppressLint("AppCompatCustomView")
public class PwdView extends TextView {

    /**
     * 绘制的开始X,如果不做处理就是0，如果做正方形处理会改变
     */
    private float drawStartX;

    /**
     * 绘制的开始Y,如果不做处理就是0，如果做正方形处理会改变
     */
    private float drawStartY;

    /**
     * 当前输入的密码的个数
     */
    private int curLenght;

    /**
     * 边框的颜色
     */
    private int borderColor;

    /**
     * 边框的宽度,单位px
     */
    private int borderWidth;

    /**
     * 边框的圆角,单位px
     */
    private int borderRadius;

    /**
     * 内容的颜色
     */
    private int contentColor;

    /**
     * 内容边界颜色
     */
    private int contentBoardColor;

    /**
     * 内容边界宽度
     */
    private int contentBoardWidth;

    /**
     * 内容的圆角角度
     */
    private int contentRadius;

    /**
     * 内容框之间的间距,单位px
     */
    private int contentMargin;

    /**
     * 内容的宽度
     */
    private float contentWidth;

    /**
     * 内容的高度
     */
    private float contentHeight;

    /**
     * 分割线的颜色
     */
    private int splitLineColor;

    /**
     * 光标的颜色
     */
    private int cursorColor;

    /**
     * 光标的上下间距,单位px
     */
    private int cursorMargin;

    /**
     * 控制光标闪烁Switch
     */
    private boolean showCursorSwitch;

    /**
     * 控件的光标是否正在显示
     */
    private boolean isShowCursor;

    /**
     * 是否显示明文，默认false
     */
    private boolean isShowPwdText = false;

    /**
     * 密码的长度
     */
    private int pwdLen;

    /**
     * 密码的颜色
     */
    private int pwdColor;

    /**
     * 密码显示形状的宽度
     */
    private int pwdWidth;

    /**
     * 绘制控件宽
     */
    private float width;

    /**
     * 绘制控件高
     */
    private float height;

    /**
     * 外边框
     */
    private RectF rect;

    /**
     * 内容区域
     */
    private RectF rectIn;

    /**
     * 光标的显示闪烁handler
     */
    private TimerHandler handler;

    /**
     * 边框的画笔
     */
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 内容的画笔
     */
    private Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 内容边界画笔
     */
    private Paint contentBoardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 分割线画笔
     */
    private Paint splitLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 密码画笔
     */
    private Paint pwdPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 明文画笔
     */
    private Paint pwdTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 文本的Y轴偏移值
     */
    private float pwdTextOffsetY;

    /**
     * 光标画笔
     */
    private Paint cursorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PwdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PwdView(Context context) {
        super(context);
        init();
    }

    public PwdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        borderColor = Color.parseColor("#818b90");
        borderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        borderRadius = 0;

        contentColor = Color.WHITE;
        contentRadius = 0;
        contentBoardColor = Color.TRANSPARENT;
        contentBoardWidth = 0;
        contentMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());

        splitLineColor = Color.parseColor("#818b90");

        cursorColor = Color.parseColor("#000000");
        cursorMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        showCursorSwitch = false;
        isShowCursor = false;

        pwdLen = 6;
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(pwdLen)});

        pwdColor = Color.parseColor("#000000");
        pwdWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 28, getResources().getDisplayMetrics()) / 2;

        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(borderColor);

        contentPaint.setStyle(Paint.Style.FILL);
        contentPaint.setColor(contentColor);

        contentBoardPaint.setStyle(Paint.Style.STROKE);
        contentBoardPaint.setColor(contentBoardColor);
        contentBoardPaint.setStrokeWidth(contentBoardWidth);

        splitLinePaint.setStyle(Paint.Style.FILL);
        splitLinePaint.setColor(splitLineColor);

        cursorPaint.setColor(cursorColor);

        pwdPaint.setStyle(Paint.Style.FILL);
        pwdPaint.setColor(pwdColor);

        pwdTextPaint.setStyle(Paint.Style.FILL);
        pwdTextPaint.setColor(getTextColors().getDefaultColor());
        pwdTextPaint.setTextSize(getTextSize());
        pwdTextPaint.setTextAlign(Paint.Align.CENTER);

        pwdTextOffsetY = (pwdTextPaint.descent() + pwdTextPaint.ascent()) / 2;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateBorderAndContentSize(w,h);
    }

    /**
     * 当borderWidth,pwdLen,contentMargin修改之后需要调用此方法
     * @param w TextView控件的宽
     * @param h TextView控件的高
     */
    private void calculateBorderAndContentSize(int w,int h){
        //算出本应该的宽高
        contentWidth = (w - 2 * borderWidth - (pwdLen - 1) * contentMargin) / (float) pwdLen;
        contentHeight = h - 2 * borderWidth;

        //为了绘制正方形,取小的数值
        contentHeight = contentWidth = contentHeight > contentWidth ? contentWidth : contentHeight;

        //变成正方形之后的宽度和高度
        height = contentHeight + 2 * borderWidth;
        width = (contentHeight * pwdLen) + 2 * borderWidth + contentMargin * (pwdLen - 1);

        //变成正方形之后，重新计算绘制的起点
        drawStartX = (w - width) / 2.0f;
        drawStartY = (h - height) / 2.0f;

        // 外边框
        rect = new RectF(drawStartX, drawStartY, drawStartX + width, drawStartY + height);

        //内容区域边框,也就是输入密码数字的那个格子
        rectIn = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBoarder(canvas);
        drawContent(canvas);
        drawSplitLine(canvas);
        drawPwd(canvas);
        if (showCursorSwitch) {
            drawCursor(canvas);
        }
    }

    /**
     * 绘制边框,先绘制一整块区域
     */
    private void drawBoarder(Canvas canvas) {
        canvas.drawRoundRect(rect, borderRadius, borderRadius, borderPaint);
    }

    /**
     * 绘制内容区域,和内容边界
     *
     * @param canvas
     */
    private void drawContent(Canvas canvas) {
        //每次绘制是都要重置rectIn的位置
        rectIn.left = rect.left + borderWidth;
        rectIn.top = rect.top + borderWidth;
        rectIn.right = rectIn.left + contentWidth;
        rectIn.bottom = rectIn.top + contentHeight;

        for (int i = 0; i < pwdLen; i++) {
            canvas.drawRoundRect(rectIn, contentRadius, contentRadius, contentPaint);
            canvas.drawRoundRect(rectIn, contentRadius, contentRadius, contentBoardPaint);
            rectIn.left = rectIn.right + contentMargin;
            rectIn.right = rectIn.left + contentWidth;
        }
    }

    /**
     * 绘制分割线
     *
     * @param canvas
     */

    private void drawSplitLine(Canvas canvas) {
        float startX = rect.left + borderWidth + contentWidth + (contentMargin / 2.0f);
        for (int i = 1; i < pwdLen; i++) {
            canvas.drawLine(startX, rect.top + borderWidth, startX, rect.bottom - borderWidth, splitLinePaint);
            startX = startX + contentWidth + contentMargin;
        }
    }

    /**
     * 绘制密码
     *
     * @param canvas
     */
    private void drawPwd(Canvas canvas) {
        float cy = rect.top + height / 2;
        float cx = rect.left + contentWidth / 2 + borderWidth;

        CharSequence nowText = getText();
        for (int i = 0; i < curLenght; i++) {
            if (isShowPwdText) {
                String drawText = String.valueOf(nowText.charAt(i));
                canvas.drawText(drawText, 0, drawText.length(), cx, cy - pwdTextOffsetY, pwdTextPaint);
            } else {
                canvas.drawCircle(cx, cy, pwdWidth / 2, pwdPaint);
            }
            cx = cx + contentWidth + contentMargin;
        }
    }

    /**
     * 绘制光标
     *
     * @param canvas
     */
    private void drawCursor(Canvas canvas) {
        float startX, startY, stopY;
        int sin = curLenght - 1;
        float half = contentWidth / 2;

        if (sin == -1) {
            startX = borderWidth + half;
            startY = cursorMargin + borderWidth;
            stopY = height - borderWidth - cursorMargin;
            canvas.drawLine(drawStartX + startX, drawStartY + startY, drawStartX + startX, drawStartY + stopY, cursorPaint);
        } else {
            startX = borderWidth + sin * (contentWidth + contentMargin) + half + pwdWidth;
            startY = cursorMargin + borderWidth;
            stopY = height - borderWidth - cursorMargin;
            canvas.drawLine(drawStartX + startX, drawStartY + startY, drawStartX + startX, drawStartY + stopY, cursorPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.curLenght = text.toString().length();
        invalidate();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putString("curtext", getText().toString());
        bundle.putBoolean("isShowCursor",isShowCursor);
        bundle.putBoolean("isShowPwdText",isShowPwdText);
        bundle.putInt("borderColor",borderColor);
        bundle.putInt("borderRadius",borderRadius);
        bundle.putInt("contentColor",contentColor);
        bundle.putInt("contentBoardColor",contentBoardColor);
        bundle.putInt("contentBoardWidth",contentBoardWidth);
        bundle.putInt("contentRadius",contentRadius);
        bundle.putInt("contentMargin",contentMargin);
        bundle.putInt("splitLineColor",splitLineColor);
        bundle.putInt("cursorColor",cursorColor);
        bundle.putInt("cursorMargin",cursorMargin);
        bundle.putInt("pwdLen",pwdLen);
        bundle.putInt("pwdColor",pwdColor);
        bundle.putInt("pwdWidth",pwdWidth);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            String curText = bundle.getString("curtext");
            if (!TextUtils.isEmpty(curText)) {
                setText(curText);
            }

            boolean isShowCursor = bundle.getBoolean("isShowCursor");
            if (isShowCursor){
                showCursor();
            }else{
                hideCursor();
            }

            boolean isShowPwdText = bundle.getBoolean("isShowPwdText");
            showPwdText(isShowPwdText);

            int borderColor = bundle.getInt("borderColor");
            setBorderColor(borderColor);

            int borderRadius = bundle.getInt("borderRadius");
            setBorderRadius(borderRadius);

            int contentColor = bundle.getInt("contentColor");
            setContentColor(contentColor);

            int contentBoardColor = bundle.getInt("contentBoardColor");
            setContentBoardColor(contentBoardColor);

            int contentBoardWidth = bundle.getInt("contentBoardWidth");
            setContentBoardWidth(contentBoardWidth);

            int contentRadius = bundle.getInt("contentRadius");
            setContentRadius(contentRadius);

            int contentMargin = bundle.getInt("contentMargin");
            setContentMargin(contentMargin);

            int splitLineColor = bundle.getInt("splitLineColor");
            setSplitLineColor(splitLineColor);

            int cursorColor = bundle.getInt("cursorColor");
            setCursorColor(cursorColor);

            int cursorMargin = bundle.getInt("cursorMargin");
            setCursorMargin(cursorMargin);

            int pwdLen = bundle.getInt("pwdLen");
            setPwdLen(pwdLen);

            int pwdColor = bundle.getInt("pwdColor");
            setPwdColor(pwdColor);

            int pwdWidth = bundle.getInt("pwdWidth");
            setPwdWidth(pwdWidth);

            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * 显示光标
     */
    public void showCursor() {
        if (handler == null) {
            handler = new TimerHandler(this);
        }

        //只有当光标没有显示的时候才让它显示
        if (!isShowCursor) {
            isShowCursor = true;
            handler.sendEmptyMessageDelayed(0, 500);
        }
    }

    /**
     * 隐藏光标
     */
    public void hideCursor() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        showCursorSwitch = false;
        isShowCursor = false;
        invalidateView();
    }

    private static class TimerHandler extends Handler {
        private WeakReference<PwdView> reference;

        TimerHandler(PwdView view) {
            reference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            PwdView view = reference.get();
            if (view != null) {
                view.showCursorSwitch = !view.showCursorSwitch;
                view.invalidateView();
                sendEmptyMessageDelayed(0, 500);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hideCursor();
    }

    /**
     * 通知重新绘制视图
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    /**
     * 返回光标是否正在显示
     *
     * @return
     */
    public boolean isShowCursor() {
        return this.isShowCursor;
    }

    /**
     * 设置边框的颜色
     *
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidateView();
    }

    /**
     * 设置边框的宽
     *
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        calculateBorderAndContentSize(getWidth(),getHeight());
        invalidateView();
    }

    /**
     * 设置边框的圆角
     *
     * @param borderRadius
     */
    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        invalidateView();
    }

    /**
     * 设置内容的颜色
     *
     * @param contentColor
     */
    public void setContentColor(int contentColor) {
        this.contentColor = contentColor;
        contentPaint.setColor(contentColor);
        invalidateView();
    }

    /**
     * 设置内容的圆角
     *
     * @param contentRadius
     */
    public void setContentRadius(int contentRadius) {
        this.contentRadius = contentRadius;
        invalidateView();
    }

    public void setSplitLineColor(int splitLineColor) {
        this.splitLineColor = splitLineColor;
        splitLinePaint.setColor(splitLineColor);
        invalidateView();
    }

    /**
     * 光标的颜色
     *
     * @param cursorColor
     */
    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
        cursorPaint.setColor(cursorColor);
        invalidateView();
    }

    /**
     * 光标上下的间隔
     *
     * @param cursorMargin
     */
    public void setCursorMargin(int cursorMargin) {
        this.cursorMargin = cursorMargin;
        invalidateView();
    }

    /**
     * 设置密码长度
     *
     * @param pwdLen
     */
    public void setPwdLen(int pwdLen) {
        this.pwdLen = pwdLen;
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(pwdLen)});
        calculateBorderAndContentSize(getWidth(),getHeight());
        invalidateView();
    }

    /**
     * 获取密码长度
     * @return 密码长度
     */
    public int getPwdLen(){
        return this.pwdLen;
    }


    /**
     * 密码颜色
     *
     * @param pwdColor
     */
    public void setPwdColor(int pwdColor) {
        this.pwdColor = pwdColor;
        pwdPaint.setColor(pwdColor);
        invalidateView();
    }

    /**
     * 设置密码圆点的宽度(直径)
     * @param pwdWidth
     */
    public void setPwdWidth(int pwdWidth) {
        this.pwdWidth = pwdWidth;
        invalidateView();
    }

    /**
     * 设置内容框直接的间隔
     * @param contentMargin
     */
    public void setContentMargin(int contentMargin) {
        this.contentMargin = contentMargin;
        calculateBorderAndContentSize(getWidth(),getHeight());
        invalidateView();
    }

    /**
     * 内容边界颜色
     * @param contentBoardColor
     */
    public void setContentBoardColor(int contentBoardColor) {
        this.contentBoardColor = contentBoardColor;
        contentBoardPaint.setColor(contentBoardColor);
        invalidateView();
    }

    /**
     * 内容边界宽度
     * @param contentBoardWidth
     */
    public void setContentBoardWidth(int contentBoardWidth) {
        this.contentBoardWidth = contentBoardWidth;
        contentBoardPaint.setStrokeWidth(contentBoardWidth);
        invalidateView();
    }

    /**
     * 用追加的形式传入信息
     *
     * @param appendText 追加的信息
     */
    public void setAppendText(CharSequence appendText) {
        if (!TextUtils.isEmpty(appendText)) {
            CharSequence nowText = getText();
            setText(nowText.toString() + appendText);
        }
    }

    /**
     * 从最后一个字符一个字符删除
     */
    public void deleteLast() {
        CharSequence nowText = getText();
        if (nowText.length() > 0) {
            setText(nowText.subSequence(0, nowText.length() - 1));
        } else {
            setText(null);
        }
    }

    /**
     * 是否显示明文
     *
     * @param showPwdText true 显示 false 不显示
     */
    public void showPwdText(boolean showPwdText) {
        isShowPwdText = showPwdText;
        invalidateView();
    }

    /**
     * 明文是否显示
     *
     * @return true 显示，false 不显示
     */
    public boolean isShowPwdText() {
        return this.isShowPwdText;
    }
}
