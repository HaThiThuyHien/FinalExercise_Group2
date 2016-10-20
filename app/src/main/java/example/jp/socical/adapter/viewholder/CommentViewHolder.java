package example.jp.socical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import example.jp.socical.R;
import example.jp.socical.bean.CommentBean;

/**
 * Created by HaHien on 10/20/2016.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {

    ImageView ivUser;
    TextView tvCmt;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ivUser = (ImageView)itemView.findViewById(R.id.imgUser);
        tvCmt = (TextView)itemView.findViewById(R.id.txtCmt);
    }

    public void bind(CommentBean commentBean) {
    }

}
