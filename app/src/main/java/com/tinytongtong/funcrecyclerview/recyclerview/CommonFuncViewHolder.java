package com.tinytongtong.funcrecyclerview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Description:
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/22 3:57 PM
 * @Version
 */
public class CommonFuncViewHolder<T extends FuncBaseBean> extends RecyclerView.ViewHolder {
    public T t;

    public CommonFuncViewHolder(View itemView) {
        super(itemView);
    }

    public CommonFuncViewHolder(View itemView, T t) {
        super(itemView);
        this.t = t;
    }

    public int getViewType() {
        return t.getViewType();
    }
}
