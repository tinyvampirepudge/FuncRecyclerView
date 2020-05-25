package com.tinytongtong.funcrecyclerview.recyclerview.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tinytongtong.funcrecyclerview.R;
import com.tinytongtong.funcrecyclerview.recyclerview.CommonFuncItem;
import com.tinytongtong.funcrecyclerview.recyclerview.CommonFuncViewHolder;
import com.tinytongtong.funcrecyclerview.recyclerview.FuncBaseBean;


/**
 * @Description: TODO
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/23 11:36 AM
 * @Version TODO
 */
public class TestCommonFuncItem<V extends FuncBaseBean> extends CommonFuncItem<V> {
    public TestCommonFuncItem(V v) {
        super(v);
    }

    @Override
    public CommonFuncViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new TestCommonFuncItem.NormalViewHolder(v, t);
    }

    @Override
    public void onBindViewHolder(final CommonFuncViewHolder vh, FuncBaseBean funcBaseBean, final int position) {
        final TestCommonFuncItem.NormalViewHolder holder = (NormalViewHolder) vh;
        TestFuncBean rib = (TestFuncBean) funcBaseBean;
        holder.tvName.setText(rib.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), String.format("点击了第%d条数据", position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class NormalViewHolder extends CommonFuncViewHolder {
        public TextView tvName;

        public NormalViewHolder(View itemView) {
            super(itemView);
        }

        public NormalViewHolder(View itemView, FuncBaseBean funcBaseBean) {
            super(itemView, funcBaseBean);
            tvName = itemView.findViewById(R.id.tv);
        }
    }
}
