package example.jp.socical.adapter;

import android.view.ViewGroup;
import java.util.List;

import example.jp.socical.adapter.viewholder.NewListViewHolder;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.callback.OnChangeFavourite;
import example.jp.socical.callback.OnChangeFollow;
import example.jp.socical.callback.OnClickAvatar;
import example.jp.socical.callback.OnClickPicture;
import vn.app.base.adapter.AdapterWithItemClick;
import vn.app.base.util.UiUtil;

/**
 * Created by HaHien on 10/17/2016.
 */

public class NewListAdapter extends AdapterWithItemClick<NewListViewHolder> {

    public List<NewsBean> newBeanList;

    OnClickPicture onClickPicture;

    OnChangeFollow onChangeFollow;

    OnChangeFavourite onChangeFavourite;

    OnClickAvatar onClickAvatar;

    public void setOnClickPicture(OnClickPicture onClickPicture) {
        this.onClickPicture = onClickPicture;
    }

    public void setOnChangeFollow(OnChangeFollow onChangeFollow) {
        this.onChangeFollow = onChangeFollow;
    }

    public void setOnChangeFavourite(OnChangeFavourite onChangeFavourite) {
        this.onChangeFavourite = onChangeFavourite;
    }

    public void setOnClickAvatar(OnClickAvatar onClickAvatar) {
        this.onClickAvatar = onClickAvatar;
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
        holder.bind(newBeanList.get(position),
                onClickPicture,
                onChangeFollow,
                onChangeFavourite,
                onClickAvatar);
    }
}
