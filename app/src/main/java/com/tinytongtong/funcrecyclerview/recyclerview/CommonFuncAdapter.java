package com.tinytongtong.funcrecyclerview.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.tinytongtong.funcrecyclerview.PlaceholderViewUtil;
import com.tinytongtong.funcrecyclerview.R;

import java.util.List;

/**
 * @Description: 支持无数据页面、错误重试页面、列表尾部"就这么多了~"
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/22 10:26 AM
 * @Version
 */
public class CommonFuncAdapter extends RecyclerView.Adapter<CommonFuncViewHolder> {

    private Context mContext;
    private List<FuncBaseBean> list;

    private View.OnClickListener retryListener;

    SparseArray<CommonFuncItem<FuncBaseBean>> commonFuncItemSparseArray;

    public CommonFuncAdapter(Context mContext) {
        this.mContext = mContext;
        commonFuncItemSparseArray = new SparseArray<>();
    }

    public void setList(List<FuncBaseBean> list) {
        this.list = list;
    }

    public void setRetryListener(View.OnClickListener retryListener) {
        this.retryListener = retryListener;
    }

    public void addCommonFuncItem(CommonFuncItem<FuncBaseBean> commonFuncItem) {
        commonFuncItemSparseArray.put(commonFuncItem.getViewType(), commonFuncItem);
    }

    @Override
    public CommonFuncViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (FuncConst.EMPTY_DATA_TYPE == viewType) {// 无数据布局
            FrameLayout root = new FrameLayout(mContext);
            root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            View emptyView = PlaceholderViewUtil.createEmptyView(mContext, R.drawable.ic_empty_placeholder_msg, "还没有消息哦~");
            root.addView(emptyView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new EmptyViewHolder(root);
        } else if (FuncConst.ERROR_DATA_TYPE == viewType) {// 网络错误布局
            FrameLayout root = new FrameLayout(mContext);
            root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            View errorView = PlaceholderViewUtil.createDefaultErrorView(mContext, retryListener);
            root.addView(errorView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new ErrorViewHolder(root);
        } else if (FuncConst.NO_MORE_DATA_TYPE == viewType) {// 列表尾部的"没有更多了"
            View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_no_more_data, parent, false);
            return new NoMoreDataViewHolder(v);
        } else {// 普通Item
            return commonFuncItemSparseArray.get(viewType).onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(CommonFuncViewHolder vh, final int position) {
        if (vh instanceof EmptyViewHolder) {
            showEmptyItemView(vh, position);
        } else if (vh instanceof ErrorViewHolder) {
            showErrorItemView(vh, position);
        } else if (vh instanceof NoMoreDataViewHolder) {
            showNoMoreItemView(vh, position);
        } else {
            commonFuncItemSparseArray.get(vh.getViewType()).onBindViewHolder(vh, list.get(position), position);
        }
    }

    private void showNoMoreItemView(CommonFuncViewHolder vh, int position) {

    }

    private void showErrorItemView(CommonFuncViewHolder vh, int position) {
        ErrorViewHolder holder = (ErrorViewHolder) vh;
        holder.btnRetry.setOnClickListener(retryListener);
        ErrorFuncBean errorFuncBean = (ErrorFuncBean) list.get(position);
    }

    private void showEmptyItemView(CommonFuncViewHolder vh, int position) {
        // do nothing
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    /**
     * 无数据的ViewHolder
     */
    static class EmptyViewHolder extends CommonFuncViewHolder {

        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 错误重试布局
     */
    static class ErrorViewHolder extends CommonFuncViewHolder {

        private Button btnRetry;

        ErrorViewHolder(View itemView) {
            super(itemView);
            btnRetry = itemView.findViewById(R.id.btn_retry);
        }
    }

    /**
     * 没有更多了
     */
    static class NoMoreDataViewHolder extends CommonFuncViewHolder {

        NoMoreDataViewHolder(View itemView) {
            super(itemView);
        }
    }
}
