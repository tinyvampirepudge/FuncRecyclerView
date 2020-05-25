package com.tinytongtong.funcrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.dialog.LoadingDialog;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tinytongtong.funcrecyclerview.recyclerview.FuncBaseBean;
import com.tinytongtong.funcrecyclerview.recyclerview.FuncRecyclerView;
import com.tinytongtong.funcrecyclerview.recyclerview.PageListHelper;
import com.tinytongtong.funcrecyclerview.recyclerview.test.TestCommonFuncItem;
import com.tinytongtong.funcrecyclerview.recyclerview.test.TestFuncBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 支持分页
 * @Author wangjianzhou
 * @Date 2020/5/23 4:24 PM
 * @Version
 */
public class MultiPageListActivity extends AppCompatActivity {
    private RefreshLayout refreshLayout;
    private FuncRecyclerView recyclerView;
    /**
     * 页码从1开始
     */
    private int pageNo = 1;
    private final int pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_page_list);

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                request();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@android.support.annotation.NonNull RefreshLayout refreshLayout) {
                request();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        TestFuncBean testFuncBean = new TestFuncBean();
        recyclerView.addCommonFuncItem(new TestCommonFuncItem(testFuncBean));
        recyclerView.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MultiPageListActivity.this, "点击重试", Toast.LENGTH_SHORT).show();
            }
        });
        request();
    }

    public void request() {
        // mock网络请求数据
        Observable.create(new ObservableOnSubscribe<List<FuncBaseBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<FuncBaseBean>> emitter) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (pageNo < 2) {
                    emitter.onNext(mockData(pageNo, 20));
                    emitter.onComplete();
                } else {
                    emitter.onNext(mockData(pageNo, 0));
                    emitter.onComplete();
//                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("就是想抛个异常");
//                    emitter.onError(illegalArgumentException);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FuncBaseBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showLoading();
                    }

                    @Override
                    public void onNext(@NonNull List<FuncBaseBean> s) {
                        onSuccess(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideLoading();
                        onFailure(e);
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }

    public void onSuccess(List<FuncBaseBean> list) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        PageListHelper.updateMultiPageList(recyclerView, list, pageNo, pageSize, new PageListHelper.OnPageListCallback() {
            @Override
            public void result(boolean canLoadMore) {
                refreshLayout.setEnableLoadMore(canLoadMore);
            }
        });
        pageNo++;
    }

    public void onFailure(Throwable e) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        PageListHelper.showError(recyclerView, e);
    }

    public List<FuncBaseBean> mockData(int no, int size) {
        List<FuncBaseBean> result = new ArrayList<>();
        int start = (no - 1) * pageSize;
        for (int i = start; i < start + size; i++) {
            TestFuncBean rib = new TestFuncBean();
            rib.setName("猫了个咪" + i);
            result.add(rib);
        }
        return result;
    }

    private LoadingDialog mLoadingDialog;

    private void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog()
                    .withMsg(true)
                    .message("正在加载");
        }
        mLoadingDialog.showInActivity(this);
    }

    private void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
