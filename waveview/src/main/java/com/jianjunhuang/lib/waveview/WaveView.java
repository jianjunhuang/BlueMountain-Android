package com.jianjunhuang.lib.waveview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class WaveView extends View {

    private Paint mWavePaint;
    private Shader mShader;
    private Paint mBorderPaint;
    private List<Wave> waves = new ArrayList<>();
    private Matrix mShaderMatrix;
    private static final float DEFAULT_AMPLITUDE_RATIO = 0.05f;
    private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
    private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;

    private float lengthRatio = DEFAULT_WAVE_LENGTH_RATIO;
    private float amplitudeRatio = DEFAULT_AMPLITUDE_RATIO;
    private float levelRatio = DEFAULT_WATER_LEVEL_RATIO;
    private float shiftRatio = DEFAULT_WAVE_SHIFT_RATIO;

    private float mDefaultAmplitude;
    private float mDefaultWaterLevel;
    private float mDefaultWaveLength;
    private double mDefaultAngularFrequency;

    public static final int SHAPE_CIRCLE = 0;
    public static final int SHAPE_SQUARE = 1;

    private int mShape = SHAPE_CIRCLE;
    private int mBorderColor = Color.DKGRAY;
    private float mBorderWidth = 0;
    private float mBorderAlphaRatio = 1.0f;

    private OnDrawShape onDrawShape;

    private int mBackgroundColor = Color.WHITE;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mShaderMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createShader();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (waves.size() == 0) {
            return;
        }
        if (null == mShader) {
            createShader();
        }

        mShaderMatrix.setScale(
                lengthRatio / DEFAULT_WAVE_LENGTH_RATIO,
                amplitudeRatio / DEFAULT_AMPLITUDE_RATIO,
                0,
                mDefaultWaterLevel);

        mShaderMatrix.postTranslate(
                shiftRatio * getWidth(),
                (DEFAULT_WATER_LEVEL_RATIO - levelRatio) * getHeight());

        mShader.setLocalMatrix(mShaderMatrix);

        if (null != onDrawShape) {
            onDrawShape.onDrawShape(canvas, this);
            return;
        }

        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setAlpha((int) (255 * mBorderAlphaRatio));
        switch (mShape) {
            case SHAPE_CIRCLE: {
                float size = getWidth() < getHeight() ? getWidth() : getHeight();
                if (mBorderWidth > 0) {
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2,
                            (size - mBorderWidth * 2) / 2 - 1, mBorderPaint);
                }
                canvas.drawCircle(getWidth() / 2, getHeight() / 2,
                        size / 2 - mBorderWidth, mWavePaint);
                break;
            }
            case SHAPE_SQUARE: {
                if (mBorderWidth > 0) {
                    canvas.drawRect(
                            mBorderWidth / 2,
                            mBorderWidth / 2,
                            getWidth() - mBorderWidth / 2 - 0.5f,
                            getHeight() - mBorderWidth / 2 - 0.5f,
                            mBorderPaint);
                }
                canvas.drawRect(
                        mBorderWidth,
                        mBorderWidth,
                        getWidth() - mBorderWidth,
                        getHeight() - mBorderWidth,
                        mWavePaint
                );
                break;
            }
        }

    }

    private void createShader() {
        if (waves.size() == 0) {
            return;
        }
        mDefaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / getWidth();
        mDefaultAmplitude = getHeight() * DEFAULT_AMPLITUDE_RATIO;
        mDefaultWaterLevel = getHeight() * DEFAULT_WATER_LEVEL_RATIO;
        mDefaultWaveLength = getWidth();

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(mBackgroundColor);
        Paint wavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wavePaint.setStrokeWidth(2);

        final int endX = getWidth() + 1;
        final int endY = getHeight() + 1;

        float[] waveY = new float[endX];

        Wave wave = waves.get(0);
        wavePaint.setColor(wave.getColor());
        wavePaint.setAlpha((int) (wave.getAlphaRatio() * 255));

        for (int beginX = 0; beginX < endX; beginX++) {
            double wx = beginX * mDefaultAngularFrequency;
            float beginY = (float) (mDefaultWaterLevel + mDefaultAmplitude * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);
            waveY[beginX] = beginY;
        }
        for (int i = 1; i < waves.size(); i++) {
            wave = waves.get(i);
            wavePaint.setColor(wave.getColor());
            wavePaint.setAlpha((int) (wave.getAlphaRatio() * 255));
            int shift = (int) wave.getSpaceRatio();
            for (int beginX = 0; beginX < endX; beginX++) {
                canvas.drawLine(beginX, waveY[(beginX + shift) % endX], beginX, endY, wavePaint);
            }
        }

        mShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mWavePaint.setShader(mShader);
    }

    public void addWave(Wave wave) {
        waves.add(wave);
        notifyView();
    }

    public void addWaves(List<Wave> waves) {
        this.waves = waves;
        notifyView();
    }

    public float getLengthRatio() {
        return lengthRatio;
    }

    public void setLengthRatio(float lengthRatio) {
        this.lengthRatio = lengthRatio;
        notifyView();
    }

    public float getAmplitudeRatio() {
        return amplitudeRatio;
    }

    public void setAmplitudeRatio(float amplitudeRatio) {
        this.amplitudeRatio = amplitudeRatio;
        notifyView();
    }

    public float getLevelRatio() {
        return levelRatio;
    }

    public void setLevelRatio(float levelRatio) {
        this.levelRatio = levelRatio;
        notifyView();
    }

    public float getShiftRatio() {
        return shiftRatio;
    }

    public void setShiftRatio(float shiftRatio) {
        this.shiftRatio = shiftRatio;
        notifyView();
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
        notifyView();
    }

    public int getShape() {
        return mShape;
    }

    public void setShape(int mShape) {
        this.mShape = mShape;
        notifyView();
    }

    public float getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(float mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
    }

    public float getBorderAlphaRatio() {
        return mBorderAlphaRatio;
    }

    public void setBorderAlphaRatio(float mBorderAlphaRatio) {
        this.mBorderAlphaRatio = mBorderAlphaRatio;
        notifyView();
    }

    private void notifyView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public void setOnDrawShape(OnDrawShape onDrawShape) {
        this.onDrawShape = onDrawShape;
        notifyView();
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
        createShader();
        notifyView();
    }

    public interface OnDrawShape {
        void onDrawShape(Canvas canvas, WaveView waveView);
    }
}
