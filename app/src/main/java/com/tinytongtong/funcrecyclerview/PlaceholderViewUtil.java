package com.tinytongtong.funcrecyclerview;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description: 生成占位View
 * @Author wangjianzhou@qding.me
 * @Date 2019-11-20 13:55
 * @Version v6.1.2
 */
public class PlaceholderViewUtil {

    public static View createDefaultEmptyView(Context context) {
        if (context == null) {
            return null;
        }
        View view = View.inflate(context, R.layout.layout_placeholder_view, null);
        return view;
    }

    public static View createEmptyView(Context context, int resId, String emptyTxt) {
        if (context == null) {
            return null;
        }
        View view = View.inflate(context, R.layout.layout_placeholder_view, null);
        ImageView iv = view.findViewById(R.id.iv);
        TextView tv = view.findViewById(R.id.tv);
        iv.setImageResource(resId);
        tv.setText(emptyTxt);
        return view;
    }

    public static View createDefaultErrorView(Context context, View.OnClickListener onClickListener) {
        if (context == null) {
            return null;
        }
        View view = View.inflate(context, R.layout.layout_placeholder_view, null);
        ImageView iv = view.findViewById(R.id.iv);
        TextView tv = view.findViewById(R.id.tv);
        Button btnRetry = view.findViewById(R.id.btn_retry);
        iv.setImageResource(R.drawable.ic_error_placeholder_default);
        tv.setText(context.getResources().getString(R.string.placeholder_hint_error_default));
        btnRetry.setVisibility(View.VISIBLE);
        btnRetry.setOnClickListener(onClickListener);
        return view;
    }

    public static View createErrorView(Context context, int resId, String errorTxt, View.OnClickListener onClickListener) {
        if (context == null) {
            return null;
        }
        View view = View.inflate(context, R.layout.layout_placeholder_view, null);
        ImageView iv = view.findViewById(R.id.iv);
        TextView tv = view.findViewById(R.id.tv);
        Button btnRetry = view.findViewById(R.id.btn_retry);
        iv.setImageResource(resId);
        tv.setText(errorTxt);
        btnRetry.setVisibility(View.VISIBLE);
        btnRetry.setOnClickListener(onClickListener);
        return view;
    }

    public static View createNetErrorView(Context context, View.OnClickListener onClickListener) {
        if (context == null) {
            return null;
        }
        View view = View.inflate(context, R.layout.layout_placeholder_view, null);
        ImageView iv = view.findViewById(R.id.iv);
        TextView tv = view.findViewById(R.id.tv);
        Button btnRetry = view.findViewById(R.id.btn_retry);
        iv.setImageResource(R.drawable.ic_error_placeholder_network);
        tv.setText(context.getResources().getString(R.string.placeholder_hint_error_network));
        btnRetry.setVisibility(View.VISIBLE);
        btnRetry.setOnClickListener(onClickListener);
        return view;
    }

}
