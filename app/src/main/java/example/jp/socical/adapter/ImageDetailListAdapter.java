package example.jp.socical.adapter;

import android.view.ViewGroup;

import example.jp.socical.adapter.viewholder.ImageDetailComentViewHolder;
import example.jp.socical.adapter.viewholder.ImageDetailHeaderViewHolder;
import example.jp.socical.adapter.viewholder.NewListViewHolder;
import example.jp.socical.adapter.viewholder.ProfileUserHeaderViewHolder;
import example.jp.socical.bean.ImageDetailData;
import example.jp.socical.bean.NewsBean;
import vn.app.base.adapter.HeaderAdapterWithItemClick;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.util.UiUtil;

/**
 * Created by Toi on 11/21/2016.
 */

public class ImageDetailListAdapter extends HeaderAdapterWithItemClick<OnClickViewHolder, NewsBean, ImageDetailData, String> {

    @Override
    protected OnClickViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new ImageDetailHeaderViewHolder(UiUtil.inflate(parent, ImageDetailHeaderViewHolder.LAYOUT_ID, false));
    }

    @Override
    protected OnClickViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ImageDetailComentViewHolder(UiUtil.inflate(parent, ImageDetailComentViewHolder.LAYOUT_ID, false));
    }

    @Override
    protected void onBindHeaderViewHolder(OnClickViewHolder holder, int position) {
        super.onBindHeaderViewHolder(holder, position);
        ((ImageDetailHeaderViewHolder)holder).bind(getHeader());
    }

    @Override
    protected void onBindItemViewHolder(OnClickViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);
        ImageDetailData imageDetailData = getItem(position);
        ((ImageDetailComentViewHolder)holder).bind(imageDetailData);
    }
}
