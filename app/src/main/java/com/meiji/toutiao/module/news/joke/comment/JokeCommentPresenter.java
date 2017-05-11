package com.meiji.toutiao.module.news.joke.comment;

import com.meiji.toutiao.RetrofitFactory;
import com.meiji.toutiao.api.IJokeApi;
import com.meiji.toutiao.bean.news.joke.JokeCommentBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/1/1.
 */

class JokeCommentPresenter implements IJokeComment.Presenter {

    private IJokeComment.View view;
    private String jokeId;
    private int count = -1;
    private int offset = 0;
    private List<JokeCommentBean.DataBean.RecentCommentsBean> commentsList = new ArrayList<>();

    JokeCommentPresenter(IJokeComment.View view) {
        this.view = view;
    }

    @Override
    public void doLoadData(String... jokeId_Count) {

        try {
            if (null == this.jokeId) {
                this.jokeId = jokeId_Count[0];
            }
            if (-1 == this.count) {
                this.count = Integer.parseInt(jokeId_Count[1]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        RetrofitFactory.getRetrofit().create(IJokeApi.class).getJokeComment(jokeId, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<JokeCommentBean, List<JokeCommentBean.DataBean.RecentCommentsBean>>() {
                    @Override
                    public List<JokeCommentBean.DataBean.RecentCommentsBean> apply(@NonNull JokeCommentBean jokeCommentBean) throws Exception {
                        return jokeCommentBean.getData().getRecent_comments();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<JokeCommentBean.DataBean.RecentCommentsBean>>bindToLife())
                .subscribe(new Observer<List<JokeCommentBean.DataBean.RecentCommentsBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        view.onShowLoading();
                    }

                    @Override
                    public void onNext(@NonNull List<JokeCommentBean.DataBean.RecentCommentsBean> recentCommentsBeen) {
                        if (recentCommentsBeen.size() > 0) {
                            doSetAdapter(recentCommentsBeen);
                        } else {
                            doShowNoMore();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        doShowNetError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        offset += 10;
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> commentsBeanList) {
        commentsList.addAll(commentsBeanList);
        view.onSetAdapter(commentsList);
        view.onHideLoading();
    }

    @Override
    public void doRefresh() {
        if (commentsList.size() != 0) {
            commentsList.clear();
            offset = 0;
        }
        doLoadData();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doShowNoMore() {
        view.onHideLoading();
        view.onShowNoMore();
    }

    @Override
    public String doGetCopyContent(int position) {
        return commentsList.get(position).getText();
    }
}