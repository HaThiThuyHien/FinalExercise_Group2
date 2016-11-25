package example.jp.socical.adapter;

import android.view.ViewGroup;

import java.util.List;

import example.jp.socical.adapter.viewholder.FollowListViewHolder;
import example.jp.socical.bean.FollowDataBean;
import vn.app.base.adapter.AdapterWithItemClick;
import vn.app.base.util.UiUtil;

/**
 * Created by Toi on 11/24/2016.
 */

public class FollowListAdapter extends AdapterWithItemClick<FollowListViewHolder> {

    public List<FollowDataBean> followDataBeanList;

    public FollowListAdapter(List<FollowDataBean> followDataBeanList) {
        this.followDataBeanList = followDataBeanList;
    }

    @Override
    public FollowListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowListViewHolder(UiUtil.inflate(parent, FollowListViewHolder.LAYOUT_ID, false));
    }

    @Override
    public int getItemCount() {
        return followDataBeanList.size();
    }

    @Override
    public void onBindViewHolder(FollowListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(followDataBeanList.get(position));
    }
}
