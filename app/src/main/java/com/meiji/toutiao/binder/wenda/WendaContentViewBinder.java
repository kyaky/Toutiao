package com.meiji.toutiao.binder.wenda;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.wenda.WendaContentBean;
import com.meiji.toutiao.module.wenda.detail.WendaDetailActivity;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.widget.CircleImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/11.
 */

public class WendaContentViewBinder extends ItemViewBinder<WendaContentBean.AnsListBean, WendaContentViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected WendaContentViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_wenda_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final WendaContentBean.AnsListBean item) {
        try {
            String iv_user_avatar = item.getUser().getAvatar_url();
            ImageLoader.loadCenterCrop(holder.itemView.getContext(), iv_user_avatar, holder.iv_user_avatar, R.color.viewBackground);
            String tv_user_name = item.getUser().getUname();
            String tv_like_count = item.getDigg_count() + "";
            String tv_abstract = item.getContent_abstract().getText();
            holder.tv_user_name.setText(tv_user_name);
            holder.tv_like_count.setText(tv_like_count);
            holder.tv_abstract.setText(tv_abstract);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WendaDetailActivity.launch(item);
                }
            });
        } catch (Exception e) {
            ErrorAction.print(e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_user_avatar;
        private TextView tv_user_name;
        private TextView tv_like_count;
        private TextView tv_abstract;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iv_user_avatar = (CircleImageView) itemView.findViewById(R.id.iv_user_avatar);
            this.tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            this.tv_like_count = (TextView) itemView.findViewById(R.id.tv_like_count);
            this.tv_abstract = (TextView) itemView.findViewById(R.id.tv_abstract);
        }
    }
}
