package com.tinytongtong.funcrecyclerview.recyclerview;

import java.util.List;

/**
 * @Description: 分页的工具类
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/25 9:14 AM
 * @Version
 */
public class PageListHelper {

    public static void updateMultiPageList(FuncRecyclerView recyclerView, List<FuncBaseBean> list, int pageNo, int pageSize, OnPageListCallback onPageListCallback) {
        if (pageNo == 1) {
            recyclerView.cleanList();
        }
        recyclerView.addList(list);
        if (recyclerView.getListSize() == 0) {
            recyclerView.showEmpty();
            if (onPageListCallback != null) {
                onPageListCallback.result(false);
            }
        } else if (recyclerView.getListSize() < pageNo * pageSize) {
            recyclerView.showNoMore();
            if (onPageListCallback != null) {
                onPageListCallback.result(false);
            }
        } else {
            if (onPageListCallback != null) {
                onPageListCallback.result(true);
            }
        }
    }

    /**
     * 不分页的页面，更新数据
     *
     * @param recyclerView
     * @param list
     */
    public static void updateSinglePageList(FuncRecyclerView recyclerView, List<FuncBaseBean> list) {
        if (list == null || list.isEmpty()) {
            recyclerView.showEmpty();
        } else {
            recyclerView.setList(list);
        }
    }

    public static void showError(FuncRecyclerView recyclerView, Throwable e) {
        if (recyclerView.getListSize() == 0) {
            recyclerView.showError();
        }
    }

    public interface OnPageListCallback {
        void result(boolean canLoadMore);
    }
}
