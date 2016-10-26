package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.CommentAdapter;
import example.jp.socical.bean.CommentBean;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.constant.HeaderOption;

public class ImageDetailFragment extends HeaderFragment {

    RecyclerView recyclerView;

    NewsBean newBean;

    public static ImageDetailFragment newInstance() {
        ImageDetailFragment imageDetailFragment = new ImageDetailFragment();
        return imageDetailFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_detail;
    }

    @Override
    protected void getArgument(Bundle bundle) {
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        recyclerView = (RecyclerView)root.findViewById(R.id.rvImgDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<CommentBean> commentBeanList = new ArrayList<>();

        CommentBean commentBean = new CommentBean(null, "Coment Coment Coment ComentComent ComentComent ComentComent ComentComent ComentComent Coment");

        for (int i = 0; i < 10; i++) {
            commentBeanList.add(commentBean);
        }

        CommentAdapter commentAdapter = new CommentAdapter(commentBeanList);
        recyclerView.setAdapter(commentAdapter);

        ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_DETAIL_USER);
    }

    @Override
    protected void initData() {

    }
}
