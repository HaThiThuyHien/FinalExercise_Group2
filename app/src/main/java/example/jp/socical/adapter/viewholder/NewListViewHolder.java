package example.jp.socical.adapter.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import example.jp.socical.R;
import example.jp.socical.adapter.NewListAdapter;
import example.jp.socical.bean.NewBean;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;

/**
 * Created by HaHien on 10/17/2016.
 */

public class NewListViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_list_news;

    @BindView(R.id.imgAvatar)
    public ImageView ivAvatar;

    @BindView(R.id.txtUser)
    public TextView tvUser;

    @BindView(R.id.btnFollow)
    public Button btnFollow;

    @BindView(R.id.imgPicture)
    public ImageView ivPicture;

    @BindView(R.id.txtPinMap)
    public TextView tvPinMap;

    @BindView(R.id.txtCmt)
    public TextView tvCmt;

    @BindView(R.id.txtAddress)
    public TextView tvAddress;

    @BindView(R.id.imgLike)
    public ImageView ivLike;

    public NewListViewHolder(View itemView) {
        super(itemView);
    }

    public void bind (NewBean newBean) {
        //ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newBean.avatar, ivAvatar);
    }
}
