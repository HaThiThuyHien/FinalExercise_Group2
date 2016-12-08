package example.jp.socical.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.R;
import example.jp.socical.bean.DataLoginBean;
import example.jp.socical.bean.ProfileUserBean;
import example.jp.socical.callback.OnUserEdit;
import example.jp.socical.manager.UserManager;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by Toi on 10/31/2016.
 */

public class ProfileUserHeaderViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.profile_user_header;

    @BindView(R.id.user_profile_avatar)
    ImageView ivAvatar;

    @BindView(R.id.txtUserName)
    TextView tvUserName;

    @BindView(R.id.user_profile_avatar_edit)
    ImageView ivAvatarChg;

    @BindView(R.id.txtFollowSum)
    TextView tvFollowSum;

    @BindView(R.id.txtFollowerSum)
    TextView tvFollowerSum;

    @BindView(R.id.txtPostSum)
    TextView tvPostSum;

    OnUserEdit onUserEdit;

    //ProfileUserHeaderBean profileUserHeaderBean;

    public void setOnUserEdit(OnUserEdit onUserEdit) {
        this.onUserEdit = onUserEdit;
    }

    public ProfileUserHeaderViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(ProfileUserBean profileUserHeaderBean) {
        DataLoginBean currentUser = UserManager.getCurrentUser();

        if (currentUser.id.equals(profileUserHeaderBean.id)) {
            ivAvatarChg.setVisibility(View.VISIBLE);
        } else {
            ivAvatarChg.setVisibility(View.GONE);
        }

        //this.profileUserHeaderBean = profileUserHeaderBean;
        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, profileUserHeaderBean.avatar, ivAvatar);
        StringUtil.displayText(profileUserHeaderBean.username, tvUserName);
        StringUtil.displayText(Integer.toString(profileUserHeaderBean.follow), tvFollowSum);
        StringUtil.displayText(Integer.toString(profileUserHeaderBean.follower), tvFollowerSum);
        StringUtil.displayText(Integer.toString(profileUserHeaderBean.post), tvPostSum);
    }

    @OnClick(R.id.user_profile_avatar_edit)
    public void clickPhoto() {
        if (onUserEdit != null) {
            onUserEdit.onChangePhoto(getAdapterPosition());
        }
    }
}
