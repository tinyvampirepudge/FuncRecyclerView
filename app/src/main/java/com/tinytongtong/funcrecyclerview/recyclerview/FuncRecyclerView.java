package com.tinytongtong.funcrecyclerview.recyclerview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.tinytongtong.funcrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/22 10:57 AM
 * @Version
 */
public class FuncRecyclerView extends FrameLayout {
    private Context mContext;
    private RecyclerView recyclerView;
    private CommonFuncAdapter adapter;
    private List<FuncBaseBean> allList;

    public FuncRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public FuncRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FuncRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FuncRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View v = View.inflate(context, R.layout.layout_func_recycler_view, null);
        recyclerView = v.findViewById(R.id.recyclerView);
        addView(v, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        allList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new CommonFuncAdapter(mContext);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 必须在{@link FuncRecyclerView#setList(List)} 之前调用
     *
     * @param commonFuncItem
     */
    public void addCommonFuncItem(CommonFuncItem<FuncBaseBean> commonFuncItem) {
        adapter.addCommonFuncItem(commonFuncItem);
    }

    public void setRetryListener(View.OnClickListener retryListener) {
        this.adapter.setRetryListener(retryListener);
    }

    /**
     * 展示无数据布局
     */
    public void showEmpty() {
        FuncBaseBean bean = new FuncBaseBean() {
            @Override
            public int getViewType() {
                return FuncConst.EMPTY_DATA_TYPE;
            }
        };
        allList.clear();
        allList.add(bean);
        adapter.setList(allList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 展示错误布局
     */
    public void showError() {
        ErrorFuncBean bean = new ErrorFuncBean();
        allList.clear();
        allList.add(bean);
        adapter.setList(allList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 展示错误布局
     */
    public void showError(int errorType) {
        ErrorFuncBean bean = new ErrorFuncBean();
        bean.setErrorType(errorType);
        allList.clear();
        allList.add(bean);
        adapter.setList(allList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置列表中的数据
     *
     * @param list
     * @param <T>
     */
    public <T extends FuncBaseBean> void setList(List<T> list) {
        allList.clear();
        if (list == null) {
            list = new ArrayList<>();
        }
        allList.addAll(list);
        adapter.setList(allList);
        adapter.notifyDataSetChanged();
    }

    public int getListSize() {
        return allList.size();
    }

    /**
     * 向列表中增加数据
     *
     * @param list
     * @param <T>
     */
    public <T extends FuncBaseBean> void addList(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        allList.addAll(list);
        adapter.setList(allList);
        adapter.notifyDataSetChanged();
    }

    public void cleanList() {
        setList(null);
    }

    /**
     * 结尾新增无数据布局
     */
    public void showNoMore() {
        FuncBaseBean bean = new FuncBaseBean() {
            @Override
            public int getViewType() {
                return FuncConst.NO_MORE_DATA_TYPE;
            }
        };
        allList.add(bean);
        adapter.setList(allList);
        adapter.notifyDataSetChanged();
    }
}
