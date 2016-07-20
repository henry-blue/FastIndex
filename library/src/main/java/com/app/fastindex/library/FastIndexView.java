package com.app.fastindex.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FastIndexView extends View {

    private static final int DEFAULT_TEXT_SIZE = 20;
    private static final int TRAN_GRAY = 0x88808080;
    private static final int TRAN_BLACK = 0x88000000;
    private static final String[] WORDS = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"};

    private Context mContext;
    private int mCellWidth;
    private float mCellHeight;
    private Paint mPaint;

    private int mTextSize;
    private int mTextColor;
    private int mTouchBackgroundColor;
    private boolean mIsBold;

    private int mTouchIndex;
    private OnLetterUpdateListener mListener;

    public FastIndexView(Context context) {
        this(context, null);
    }

    public FastIndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttributeSet(attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        if (mIsBold) {
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        }

        mTouchIndex = -1;
    }

    private void initAttributeSet(AttributeSet attrs) {
        TypedArray arr = mContext.obtainStyledAttributes(attrs, R.styleable.FastIndexView);
        mTextColor = arr.getColor(R.styleable.FastIndexView_TextColor, TRAN_BLACK);
        mTextSize = arr.getDimensionPixelOffset(R.styleable.FastIndexView_TextSize, DEFAULT_TEXT_SIZE);
        mIsBold = arr.getBoolean(R.styleable.FastIndexView_IsBold, false);
        mTouchBackgroundColor = arr.getColor(R.styleable.FastIndexView_TouchBackgroundColor, TRAN_GRAY);
        arr.recycle();
    }

    public void setListener(OnLetterUpdateListener listener) {
        mListener = listener;
    }

    public OnLetterUpdateListener getListener() {
        return mListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < WORDS.length; i++) {
            String text = WORDS[i];
            int x = (int) (mCellWidth / 2.0f - mPaint.measureText(text) / 2.0f);
            Rect bounds = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), bounds);
            int textHeight = bounds.height();
            int y = (int) (mCellHeight / 2.0f + textHeight / 2.0f + i * mCellHeight);
            canvas.drawText(text, x, y, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCellWidth = getMeasuredWidth();
        int height = getMeasuredHeight();
        mCellHeight = height * 1.0f / WORDS.length;
    }

    private void setTouchIndex(float y) {
        int index = (int) (y / mCellHeight);
        if (index >= 0 && index < WORDS.length) {
            if (index != mTouchIndex) {
                if(mListener != null){
                    mListener.onLetterUpdate(WORDS[index]);
                }
                mTouchIndex = index;
            }
        }
        setBackgroundColor(mTouchBackgroundColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                setTouchIndex(event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                setTouchIndex(event.getY());
                break;
            case MotionEvent.ACTION_UP:
                mTouchIndex = -1;
                setBackgroundColor(Color.TRANSPARENT);
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public interface OnLetterUpdateListener {
        void onLetterUpdate(String letter);
    }

}
