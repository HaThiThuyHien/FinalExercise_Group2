package example.jp.socical.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import example.jp.socical.R;
import example.jp.socical.bean.ImageDetailData;
import example.jp.socical.bean.NewsBean;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by Toi on 11/21/2016.
 */

public class ImageDetailComentViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_list_coment;

    @BindView(R.id.imgUser)
    ImageView ivUser;

    @BindView(R.id.txtCmt)
    TextView tvComment;

    public ImageDetailComentViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(ImageDetailData detailData) {
        //ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, detailData.user.avatar, ivUser);
        StringUtil.displayText(detailData.comment,tvComment);
    }
}
