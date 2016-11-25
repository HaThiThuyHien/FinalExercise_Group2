package example.jp.socical.adapter.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import example.jp.socical.R;
import example.jp.socical.bean.NewsBean;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by Toi on 11/21/2016.
 */

public class ImageDetailHeaderViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_image_detail_header;

    @BindView(R.id.imgAvatar)
    ImageView ivAvatar;

    @BindView(R.id.txtUser)
    TextView tvUserName;

    @BindView(R.id.btnFollow)
    Button btnFollow;

    @BindView(R.id.imgPicture)
    ImageView ivPicture;

    @BindView(R.id.txtPinMap)
    TextView tvPinMap;

    @BindView(R.id.txtCaption)
    TextView tvCaption;

    @BindView(R.id.txtHashtag)
    TextView tvHashtag;

    @BindView(R.id.imgFavourite)
    CircleImageView cvFavourite;

    NewsBean newsBean;

    public ImageDetailHeaderViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(NewsBean newsBean) {
        this.newsBean = newsBean;

        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newsBean.user.avatar, ivAvatar);
        //ivAvatar.setImageResource(R.drawable.dummy_avatar);
        StringUtil.displayText(newsBean.user.username,tvUserName);

        if (newsBean.user.isFollowing) {
            btnFollow.setBackgroundResource(R.drawable.btnfollowing_bg);
            btnFollow.setText("Following");
        }else {
            btnFollow.setBackgroundResource(R.drawable.btnfollow_bg);
            btnFollow.setText("Follow");
        }

        StringUtil.displayText(newsBean.image.caption, tvCaption);

        int size = newsBean.image.hashtag.size();
        String strHashtag ="";
        for (int i = 0; i < size; i++) {
            strHashtag += newsBean.image.hashtag.get(i);
            strHashtag += " ";
        }
        StringUtil.displayText(strHashtag, tvHashtag);
        StringUtil.displayText(newsBean.image.location, tvPinMap);

        if (newsBean.image.isFavourite) {
            cvFavourite.setImageResource(R.drawable.icon_favourite);
        }else {
            cvFavourite.setImageResource(R.drawable.icon_no_favourite);
        }

        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newsBean.image.url, ivPicture);
        //ivPicture.setImageResource(R.drawable.placeholer_image_1600);
    }
}
