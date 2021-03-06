package com.meiji.toutiao.binder.media;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MediaChannelBean;
import com.meiji.toutiao.module.media.article.MediaArticleActivity;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.widget.CircleImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/12.
 */

public class MediaChannelViewBinder extends ItemViewBinder<MediaChannelBean, MediaChannelViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected MediaChannelViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_media_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final MediaChannelBean item) {
        try {
            String url = item.getAvatar();
            ImageLoader.loadCenterCrop(holder.itemView.getContext(), url, holder.cv_avatar, R.color.viewBackground);
            holder.tv_mediaName.setText(item.getName());
            holder.tv_descText.setText(item.getDescText());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaArticleActivity.startActivity(item);
                }
            });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView cv_avatar;
        private TextView tv_mediaName;
        private TextView tv_followCount;
        private TextView tv_descText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cv_avatar = (CircleImageView) itemView.findViewById(R.id.cv_avatar);
            this.tv_mediaName = (TextView) itemView.findViewById(R.id.tv_mediaName);
            this.tv_followCount = (TextView) itemView.findViewById(R.id.tv_followCount);
            this.tv_descText = (TextView) itemView.findViewById(R.id.tv_descText);
        }
    }
}
