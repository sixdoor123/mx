package com.baiyi.jj.app.views;

/**
 * Created by Administrator on 2017/6/15.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.utils.ChannelIconManager;
import com.turbo.turbo.mexico.R;

import java.util.List;

public class ChannelFlowLayout extends ViewGroup {

    private static final int DEFAULT_HORIZONTAL_SPACING = BaseActivity.getDensity_int(15);
    private static final int DEFAULT_VERTICAL_SPACING = BaseActivity.getDensity_int(20);

    private int mVerticalSpacing;
    private int mHorizontalSpacing;

    public ChannelFlowLayout(Context context) {
        super(context);
    }

    public ChannelFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        try {
            mHorizontalSpacing = DEFAULT_HORIZONTAL_SPACING;
            mVerticalSpacing = DEFAULT_VERTICAL_SPACING;
        } finally {
        }
    }


    public void setHorizontalSpacing(int pixelSize) {
        mHorizontalSpacing = pixelSize;
    }

    public void setVerticalSpacing(int pixelSize) {
        mVerticalSpacing = pixelSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int myWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        // Measure each child and put the child to the right of previous child
        // if there's enough room for it, otherwise, wrap the line and put the child to next line.
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            } else {
                continue;
            }

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            } else {
                childLeft += childWidth + mHorizontalSpacing;
            }
        }

        int wantedHeight = childTop + lineHeight + paddingBottom;

        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }

            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }
    }

    private List<ChannelItem> AllList;

    public void setTags(List<ChannelItem> tagList) {
        this.removeAllViews();

        AllList = tagList;
        for (int i = 0; i < AllList.size(); i++) {
            final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tag_channel, null);
            final TextView textView = (TextView) view.findViewById(R.id.txt_channel_name);
            textView.setText(AllList.get(i).getChannel_name());
            textView.setTextSize(18);
            textView.setId(i);
            textView.setIncludeFontPadding(false);
            final ImageView imageView = (ImageView) view.findViewById(R.id.img_channel);
            imageView.setActivated(false);
            imageView.setBackgroundResource(ChannelIconManager.getInstance(getContext()).getIconUn(Integer.parseInt(AllList.get(i).getCid())));

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (imageView.isActivated()) {
                        textView.setTextColor(getResources().getColor(R.color.day_text_color_black));
                        imageView.setActivated(false);
                        AllList.get(textView.getId()).setIs_default("false");
                        view.setBackgroundResource(R.drawable.bg_channel_hui);
                        imageView.setBackgroundResource(ChannelIconManager.getInstance(getContext()).getIconUn(Integer.parseInt(AllList.get(textView.getId()).getCid())));

                    } else {
                        textView.setTextColor(getResources().getColor(R.color.day_text_color_write));
                        imageView.setActivated(true);
                        AllList.get(textView.getId()).setIs_default("true");
                        view.setBackgroundResource(R.drawable.bg_channel_red);
                        imageView.setBackgroundResource(ChannelIconManager.getInstance(getContext()).getIconOn(Integer.parseInt(AllList.get(textView.getId()).getCid())));
                    }
                }
            });
            addView(view);
        }

    }
}
