package base.splithighlightseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;

import java.util.Collections;
import java.util.List;

public class OnlyHeSeekBar extends AppCompatSeekBar {
    private int mNormalSeekBarBg, mOnlyHeGray, mOnlyHeGreen;
    private int mPaddingTop = 0;
    private int mPaddingBottom = 0;

    public OnlyHeSeekBar(Context context) {
        this(context, null);
    }

    public OnlyHeSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnlyHeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OnlyHeSeekBar);
        mNormalSeekBarBg = R.drawable.player_seekbar_bg;
        mOnlyHeGray = R.drawable.only_he_progress_item_gray;
        mOnlyHeGreen = R.drawable.only_he_progress_item_green;

        mOnlyHeGray = a.getResourceId(R.styleable.OnlyHeSeekBar_seekbarGrayBg, mOnlyHeGray);
        mOnlyHeGreen = a.getResourceId(R.styleable.OnlyHeSeekBar_seekbarGreenBg, mOnlyHeGreen);
        mNormalSeekBarBg = a.getResourceId(R.styleable.OnlyHeSeekBar_seekbarNormal, mNormalSeekBarBg);
        a.recycle();
        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private boolean isOnlyHe = false;

    public boolean isOnlyHe() {
        return isOnlyHe;
    }

    public void setProgressBackground(final boolean isOnlyHe, final List<StartEndPoint> startEndPoints) {
        this.isOnlyHe = isOnlyHe;
        if (!Utils.checkIsMainThread()) {
            post(new Runnable() {
                @Override
                public void run() {
                    setProgressBackground(isOnlyHe, startEndPoints);
                }
            });
            return;
        }
        int paddingTopBottom = (this.getHeight() - (mPaddingBottom + mPaddingTop)) / 2 - dip2Px(1);

        if (!isOnlyHe && paddingTopBottom > 0) {
            Drawable[] drawablesFull = new Drawable[1];
            drawablesFull[0] = getResources().getDrawable(mNormalSeekBarBg);

            LayerDrawable layerDrawableFull = new LayerDrawable(drawablesFull);
            LogUtil.d("myVersion515 seek bar height: " + this.getHeight() + " paddingTopBottom: " + paddingTopBottom
                    + " mPaddingBottom: " + mPaddingBottom + " mPaddingTop: " + mPaddingTop);
            layerDrawableFull.setLayerInset(0, 0, paddingTopBottom, 0, paddingTopBottom);
            this.setProgressDrawable(layerDrawableFull);
            this.setProgress(0);
            this.setMax(Constants.DURATION);
//            this.setProgress(DeviceUtil.getCurrentTime());
            invalidate();
            return;
        }

        if(startEndPoints.isEmpty()) {
            LogUtil.e("myVersion515 startEndPoints is empty.");
            return;
        }

        Drawable oldProgressDrawable = this.getProgressDrawable();
        if (oldProgressDrawable == null) return;
        Rect rect = oldProgressDrawable.getBounds();

        int length = startEndPoints.size();
        Drawable[] drawables = new Drawable[length + 1];

        drawables[0] = getResources().getDrawable(mOnlyHeGray);
        for (int i = 0; i < length; i++) {
            drawables[i + 1] = getResources().getDrawable(mOnlyHeGreen);
        }

        LayerDrawable layerDrawable = new LayerDrawable(drawables);

        layerDrawable.setLayerInset(0, 0, paddingTopBottom, 0, paddingTopBottom);
        for (int j = 0; j < length; j++) {
            StartEndPoint startEndPoint = startEndPoints.get(j);
            int leftSpace = getPointStartEnd(true, startEndPoint, rect.right);
            int rightSpace = getPointStartEnd(false, startEndPoint, rect.right);
            if (leftSpace < 0) leftSpace = 0;
            if (paddingTopBottom < 0) paddingTopBottom = 0;
            layerDrawable.setLayerInset(j + 1, leftSpace, paddingTopBottom, rect.right < 0 ? leftSpace : (rect.right - rightSpace), paddingTopBottom);
        }
        this.setProgressDrawable(layerDrawable);
    }

    private int getPointStartEnd(boolean isStart, StartEndPoint startEndPoint, int length) {
        long start = startEndPoint.start;
        long end = startEndPoint.end;
        int duration = Constants.DURATION;
        return (int) (isStart ? (start * length / duration) : (end * length / duration));
    }

    private int dip2Px(int dip) {
        return (int)(dip * getResources().getDisplayMetrics().density + 0.5);
    }

}
