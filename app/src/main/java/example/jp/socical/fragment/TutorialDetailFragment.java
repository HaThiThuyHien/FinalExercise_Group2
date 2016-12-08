package example.jp.socical.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import example.jp.socical.R;
import example.jp.socical.bean.TutorialBean;
import example.jp.socical.bean.UserBean;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by Toi on 12/6/2016.
 */

public class TutorialDetailFragment extends NoHeaderFragment{

    public static final String TUTORIAL_TITLE = "Title";
    public static final String TUTORIAL_AVATAR = "Avatar";
    public static final String TUTORIAL_SHOWAVATAR = "ShowAva";
    public static final String TUTORIAL_IMG = "Image";

    @BindView(R.id.imgAvatar)
    CircleImageView ivAvatar;

    @BindView(R.id.txtTitle)
    TextView tvTitle;

    @BindView(R.id.imgBackground)
    ImageView ivBackground;

    String userAvatar;
    String title;
    String imgBackground;
    boolean showAvatar;

    public static TutorialDetailFragment newInstance(TutorialBean tutorial, UserBean user) {
        TutorialDetailFragment tutorialDetailFragment = new TutorialDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TUTORIAL_AVATAR, user.avatar);
        bundle.putString(TUTORIAL_TITLE, tutorial.title);
        bundle.putBoolean(TUTORIAL_SHOWAVATAR, tutorial.showAvatar);
        bundle.putString(TUTORIAL_IMG, tutorial.image);
        tutorialDetailFragment.setArguments(bundle);
        return tutorialDetailFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tutorial_detail;
    }

    @Override
    protected void getArgument(Bundle bundle) {
        title = bundle.getString(TUTORIAL_TITLE);
        imgBackground = bundle.getString(TUTORIAL_IMG);
        showAvatar = bundle.getBoolean(TUTORIAL_SHOWAVATAR);
        userAvatar = bundle.getString(TUTORIAL_AVATAR);
    }

    @Override
    protected void initData() {
        if (!showAvatar) {
            ivAvatar.setVisibility(View.GONE);
        } else {
            if (userAvatar != null) {
                ImageLoader.loadImage(getContext(), userAvatar, ivAvatar);
            } else {
                ivAvatar.setVisibility(View.GONE);
            }
        }

        StringUtil.displayText(title, tvTitle);
        ImageLoader.loadImage(getContext(), imgBackground, ivBackground);
    }
}
