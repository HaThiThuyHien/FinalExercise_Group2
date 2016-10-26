package example.jp.socical.adapter.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import example.jp.socical.R;
import example.jp.socical.bean.NewsBean;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.DebugLog;
import vn.app.base.util.StringUtil;

/**
 * Created by HaHien on 10/17/2016.
 */

public class NewListViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_list_news;

    // User
    public ImageView ivAvatar;

    public TextView tvUser;

    public Button btnFollow;

    // Image
    public ImageView ivPicture;

    public TextView tvPinMap;

    public TextView tvCaption;

    public TextView tvHashtag;

    public ImageView ivLike;

    boolean bFollow = false;
    boolean bLike = false;

    public NewListViewHolder(View itemView) {
        super(itemView);

        ivAvatar = (ImageView)itemView.findViewById(R.id.imgAvatar);
        tvUser = (TextView)itemView.findViewById(R.id.txtUser);
        btnFollow = (Button)itemView.findViewById(R.id.btnFollow);

        ivPicture = (ImageView)itemView.findViewById(R.id.imgPicture);
        tvPinMap = (TextView)itemView.findViewById(R.id.txtPinMap);
        tvCaption = (TextView)itemView.findViewById(R.id.txtCaption);
        tvHashtag = (TextView)itemView.findViewById(R.id.txtHashtag);
        ivLike = (ImageView)itemView.findViewById(R.id.imgLike);

        //ivAvatar.setOnClickListener(this);
        btnFollow.setOnClickListener(this);
        ivLike.setOnClickListener(this);

    }

    public void bind (NewsBean newBean) {
        // User
        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newBean.user.avatar, ivAvatar);
        StringUtil.displayText(newBean.user.username,tvUser);
        if (newBean.user.isFollowing) {
            btnFollow.setBackgroundResource(R.drawable.btnfollowing_bg);
            btnFollow.setText("Following");
        }else {
            btnFollow.setBackgroundResource(R.drawable.btnfollow_bg);
            btnFollow.setText("Follow");
        }
        bFollow = newBean.user.isFollowing;

        StringUtil.displayText(newBean.image.caption, tvCaption);

        int size = newBean.image.hashtag.size();
        String strHashtag ="";
        for (int i = 0; i < size; i++) {
            strHashtag += newBean.image.hashtag.get(i);
            strHashtag += " ";
        }
        StringUtil.displayText(strHashtag, tvHashtag);
        StringUtil.displayText(newBean.image.location, tvPinMap);

        if (newBean.image.isFavourite) {
            ivLike.setBackgroundColor(Color.parseColor("#FFEC2878"));
        }else {
            ivLike.setBackgroundColor(Color.parseColor("#42221f1f"));
        }
        bLike = newBean.image.isFavourite;

        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newBean.image.url, ivPicture);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.imgAvatar:
                DebugLog.i("select avatar");
                break;
            case R.id.imgPicture:
                DebugLog.i("select imgPicture");
                break;
            case R.id.btnFollow:
                if (bFollow) {
                    bFollow = false;
                    btnFollow.setBackgroundResource(R.drawable.btnfollow_bg);
                    btnFollow.setText("Follow");
                }else {
                    bFollow = true;
                    btnFollow.setBackgroundResource(R.drawable.btnfollowing_bg);
                    btnFollow.setText("Following");
                }
                break;
            default:
                break;
        }
    }
}
