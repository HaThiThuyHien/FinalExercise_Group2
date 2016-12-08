package example.jp.socical.adapter.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import example.jp.socical.R;
import example.jp.socical.bean.FollowDataBean;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by Toi on 11/24/2016.
 */

public class FollowListViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_list_follow;

    @BindView(R.id.imgAvatar)
    ImageView ivAvatar;

    @BindView(R.id.txtUserName)
    TextView tvUserName;

    @BindView(R.id.btnFollow)
    Button btnFollow;

    public FollowListViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(FollowDataBean followDataBean) {

        if (followDataBean == null) {
            return;
        }

        //if (followDataBean.user.avatar != null) {
        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, followDataBean.user.avatar, ivAvatar);
        //} else {
        //    ivAvatar.setImageResource(R.drawable.loading_list_image_220);
        //}

        StringUtil.displayText(followDataBean.user.username,tvUserName);
        if (followDataBean.user.isFollowing) {
            btnFollow.setBackgroundResource(R.drawable.btnfollowing_bg);
            btnFollow.setText("Following");
        }else {
            btnFollow.setBackgroundResource(R.drawable.btnfollow_bg);
            btnFollow.setText("Follow");
        }
    }
}
