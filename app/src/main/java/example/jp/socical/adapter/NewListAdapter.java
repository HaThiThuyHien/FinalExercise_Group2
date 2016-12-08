package example.jp.socical.adapter;

import android.view.ViewGroup;
import java.util.List;

import example.jp.socical.adapter.viewholder.NewListViewHolder;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.callback.OnNewItemClick;
import vn.app.base.adapter.AdapterWithItemClick;
import vn.app.base.util.UiUtil;

/**
 * Created by HaHien on 10/17/2016.
 */

public class NewListAdapter extends AdapterWithItemClick<NewListViewHolder> {

    public List<NewsBean> newBeanList;

    OnNewItemClick onNewItemClick;

    public void setOnNewItemClick(OnNewItemClick onNewItemClick) {
        this.onNewItemClick = onNewItemClick;
    }

    public NewListAdapter(List<NewsBean> newBeanList) {
        this.newBeanList = newBeanList;
    }

    @Override
    public NewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewListViewHolder(UiUtil.inflate(parent, NewListViewHolder.LAYOUT_ID, false));
    }

    @Override
    public int getItemCount() {
        return newBeanList.size();
    }

    @Override
    public void onBindViewHolder(NewListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(newBeanList.get(position), onNewItemClick);
    }
}
