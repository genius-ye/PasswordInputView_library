package com.genius.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;

import com.vshidai.passwordinputview_library.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Desc:
 * User: genius-ye by 2016/3/22
 */
public class PasswordInputView extends EditText {

    private int textLength;

    private int borderColor;
    private float borderWidth;
    private float borderRadius;

    private int passwordLength;
    private int passwordColor;
    private float passwordWidth;
    private float passwordRadius;

    private Paint passwordPaint = new Paint(ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(ANTI_ALIAS_FLAG);

    private final int defaultContMargin = 5;
    private final int defaultSplitLineWidth = 3;
    /**
     * 是否显示密码
     **/
    private boolean isShowPwd;
    /**
     * 当前输入的字符
     **/
    private String mString;
    /** 内容区域背景 **/
    private int passwordColor_bg;

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final Resources res = getResources();

        final int defaultBorderColor = res.getColor(R.color.default_ev_border_color);
        final float defaultBorderWidth = res.getDimension(R.dimen.default_ev_border_width);
        final float defaultBorderRadius = res.getDimension(R.dimen.default_ev_border_radius);

        final int defaultPasswordLength = res.getInteger(R.integer.default_ev_password_length);
        final int defaultPasswordColor = res.getColor(R.color.default_ev_password_color);
        final float defaultPasswordWidth = res.getDimension(R.dimen.default_ev_password_width);
        final float defaultPasswordRadius = res.getDimension(R.dimen.default_ev_password_radius);
        final int defaultPasswordColor_bg=res.getColor(R.color.default_ev_password_color_bg);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputView, 0, 0);
        try {
            borderColor = a.getColor(R.styleable.PasswordInputView_borderColor, defaultBorderColor);
            borderWidth = a.getDimension(R.styleable.PasswordInputView_pw_borderWidth, defaultBorderWidth);
            borderRadius = a.getDimension(R.styleable.PasswordInputView_borderRadius, defaultBorderRadius);
            passwordLength = a.getInt(R.styleable.PasswordInputView_passwordLength, defaultPasswordLength);
            passwordColor = a.getColor(R.styleable.PasswordInputView_passwordColor, defaultPasswordColor);
            passwordWidth = a.getDimension(R.styleable.PasswordInputView_passwordWidth, defaultPasswordWidth);
            passwordRadius = a.getDimension(R.styleable.PasswordInputView_passwordRadius, defaultPasswordRadius);
            passwordColor_bg = a.getColor(R.styleable.PasswordInputView_passwordColor_bg, defaultPasswordColor_bg);
            isShowPwd = a.getBoolean(R.styleable.PasswordInputView_isShowPwd,false);
        } finally {
            a.recycle();
        }

        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        passwordPaint.setStrokeWidth(passwordWidth);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColor);

        //设置输入的长度
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(passwordLength)});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // 外边框
        RectF rect = new RectF(0, 0, width, height);
        borderPaint.setColor(borderColor);
        canvas.drawRoundRect(rect, borderRadius, borderRadius, borderPaint);

        // 内容区
        RectF rectIn = new RectF(rect.left + defaultContMargin, rect.top + defaultContMargin, rect.right - defaultContMargin, rect.bottom - defaultContMargin);
        borderPaint.setColor(passwordColor_bg);
        canvas.drawRoundRect(rectIn, borderRadius, borderRadius, borderPaint);

        // 分割线
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(defaultSplitLineWidth);
        for (int i = 1; i < passwordLength; i++) {
            float x = width * i / passwordLength;
            canvas.drawLine(x, 0, x, height, borderPaint);
        }

        if (isShowPwd) {
            // 密码
            float cx, cy = height / 2;
            float half = width / passwordLength / 2 - borderWidth;
            for (int i = 0; i < textLength; i++) {
                cx = width * i / passwordLength + half;
//                canvas.drawCircle(cx, cy, passwordWidth, passwordPaint);
                passwordPaint.setTextSize(42);
//                passwordPaint.setColor(Color.parseColor("#4f4e4e"));
                Paint.FontMetrics fontMetrics = passwordPaint.getFontMetrics();    //
                //计算文字高度
                float fontHeight = fontMetrics.bottom - fontMetrics.top;    //
                //计算文字 baseline
                float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
                canvas.drawText(mString.toCharArray(), i, 1, cx, textBaseY, passwordPaint);
            }
        } else {
            // 密码
            float cx, cy = height / 2;
            float half = width / passwordLength / 2;
            for (int i = 0; i < textLength; i++) {
                cx = width * i / passwordLength + half;
                canvas.drawCircle(cx, cy, passwordWidth, passwordPaint);
            }
        }

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        mString = text.toString();
        invalidate();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        borderPaint.setStrokeWidth(borderWidth);
        invalidate();
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
        invalidate();
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        invalidate();
    }

    public int getPasswordColor() {
        return passwordColor;
    }

    public void setPasswordColor(int passwordColor) {
        this.passwordColor = passwordColor;
        passwordPaint.setColor(passwordColor);
        invalidate();
    }

    public float getPasswordWidth() {
        return passwordWidth;
    }

    public void setPasswordWidth(float passwordWidth) {
        this.passwordWidth = passwordWidth;
        passwordPaint.setStrokeWidth(passwordWidth);
        invalidate();
    }

    public float getPasswordRadius() {
        return passwordRadius;
    }

    public void setPasswordRadius(float passwordRadius) {
        this.passwordRadius = passwordRadius;
        invalidate();
    }

    public int getPasswordColor_bg() {
        return passwordColor_bg;
    }

    /**
     * 设置密码的背景颜色
     * @param passwordColor_bg
     */
    public void setPasswordColor_bg(int passwordColor_bg) {
        this.passwordColor_bg = passwordColor_bg;
    }
    /**
     * 设置密码的背景颜色
     * @param passwordColor_bg
     */
    public void setPasswordColor_bg(String passwordColor_bg) {
        this.passwordColor_bg = Color.parseColor(passwordColor_bg);
    }

    public boolean isShowPwd() {
        return isShowPwd;
    }

    public void setIsShowPwd(boolean isShowPwd) {
        this.isShowPwd = isShowPwd;
    }
}
