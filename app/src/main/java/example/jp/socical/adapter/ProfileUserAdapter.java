package example.jp.socical.adapter;

import android.view.ViewGroup;

import example.jp.socical.adapter.viewholder.NewListViewHolder;
import example.jp.socical.adapter.viewholder.ProfileUserHeaderViewHolder;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.bean.ProfileUserBean;
import example.jp.socical.callback.OnNewItemClick;
import example.jp.socical.callback.OnUserEdit;
import vn.app.base.adapter.HeaderAdapterWithItemClick;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.util.UiUtil;

/**
 * Created by Toi on 10/31/2016.
 */

public class ProfileUserAdapter extends HeaderAdapterWithItemClick<OnClickViewHolder, ProfileUserBean, NewsBean, String>{

    OnUserEdit onUserEdit;

    OnNewItemClick onNewItemClick;

    public void setOnNewItemClick(OnNewItemClick onNewItemClick) {
        this.onNewItemClick = onNewItemClick;
    }

    public void setOnUserEdit(OnUserEdit onUserEdit) {
        this.onUserEdit = onUserEdit;
    }

    @Override
    protected OnClickViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new ProfileUserHeaderViewHolder(UiUtil.inflate(parent, ProfileUserHeaderViewHolder.LAYOUT_ID, false));
    }

    @Override
    protected OnClickViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new NewListViewHolder(UiUtil.inflate(parent, NewListViewHolder.LAYOUT_ID, false));
    }

    @Override
    protected void onBindHeaderViewHolder(OnClickViewHolder holder, int position) {
        super.onBindHeaderViewHolder(holder, position);
        ((ProfileUserHeaderViewHolder)holder).bind(getHeader());
        ((ProfileUserHeaderViewHolder)holder).setOnUserEdit(onUserEdit);
    }

    @Override
    protected void onBindItemViewHolder(OnClickViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);
        NewsBean profileUserBean = getItem(position);
        ((NewListViewHolder)holder).bind(profileUserBean, onNewItemClick);
    }
}
