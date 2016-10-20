package example.jp.socical.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import example.jp.socical.R;
import example.jp.socical.adapter.viewholder.CommentViewHolder;
import example.jp.socical.bean.CommentBean;

/**
 * Created by HaHien on 10/20/2016.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder>{


    List<CommentBean> commentBeanList;

    public CommentAdapter(List<CommentBean> commentBeanList) {
        this.commentBeanList = commentBeanList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CommentViewHolder(layoutInflater.inflate(R.layout.item_list_coment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bind(commentBeanList.get(position));

    }

    @Override
    public int getItemCount() {
        return commentBeanList.size();
    }
}
