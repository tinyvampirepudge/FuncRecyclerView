package com.tinytongtong.funcrecyclerview.recyclerview;

import android.view.ViewGroup;

/**
 * @Description:
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/22 3:55 PM
 * @Version
 */
public abstract class CommonFuncItem<T extends FuncBaseBean> {
    public T t;

    public CommonFuncItem(T t) {
        this.t = t;
    }

    public int getViewType() {
        return t.getViewType();
    }

    public abstract CommonFuncViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(CommonFuncViewHolder vh, FuncBaseBean funcBaseBean, final int position);
}
