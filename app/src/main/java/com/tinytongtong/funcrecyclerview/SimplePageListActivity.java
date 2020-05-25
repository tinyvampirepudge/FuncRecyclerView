package com.tinytongtong.funcrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.dialog.LoadingDialog;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tinytongtong.funcrecyclerview.recyclerview.FuncBaseBean;
import com.tinytongtong.funcrecyclerview.recyclerview.FuncRecyclerView;
import com.tinytongtong.funcrecyclerview.recyclerview.PageListHelper;
import com.tinytongtong.funcrecyclerview.recyclerview.test.Test1CommonFuncItem;
import com.tinytongtong.funcrecyclerview.recyclerview.test.Test1FuncBean;
import com.tinytongtong.funcrecyclerview.recyclerview.test.TestFuncBean;
import com.tinytongtong.funcrecyclerview.recyclerview.test.TestCommonFuncItem;

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
 * @Description: 不支持分页，全部获取
 * @Author wangjianzhou
 * @Date 2020/5/23 4:24 PM
 * @Version
 */
public class SimplePageListActivity extends AppCompatActivity {

    private RefreshLayout refreshLayout;
    private FuncRecyclerView recyclerView;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_page_list);

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        TestFuncBean testFuncBean = new TestFuncBean();
        recyclerView.addCommonFuncItem(new TestCommonFuncItem(testFuncBean));
        Test1FuncBean test1FuncBean = new Test1FuncBean();
        recyclerView.addCommonFuncItem(new Test1CommonFuncItem(test1FuncBean));
        recyclerView.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SimplePageListActivity.this, "点击重试", Toast.LENGTH_SHORT).show();
            }
        });
        request();
    }

    public void request() {
        count++;
        Observable.create(new ObservableOnSubscribe<List<FuncBaseBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<FuncBaseBean>> emitter) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (count % 3 == 0) {
                    emitter.onNext(mockData(0));
                    emitter.onComplete();
                } else if (count % 3 == 1) {
                    emitter.onNext(mockData1(20));
                    emitter.onComplete();
                } else {
                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("就是想抛个异常");
                    emitter.onError(illegalArgumentException);
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
        PageListHelper.updateSinglePageList(recyclerView, list);
    }

    public void onFailure(Throwable e) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        PageListHelper.showError(recyclerView,e);
    }

    public List<FuncBaseBean> mockData(int size) {
        List<FuncBaseBean> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TestFuncBean rib = new TestFuncBean();
            rib.setName("猫了个咪" + i);
            result.add(rib);
        }
        return result;
    }

    public List<FuncBaseBean> mockData1(int size) {
        List<FuncBaseBean> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TestFuncBean rib = new TestFuncBean();
            rib.setName("猫了个咪" + i);
            result.add(rib);
            Test1FuncBean rib1 = new Test1FuncBean();
            rib1.setName("阿西吧" + i);
            result.add(rib1);
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
