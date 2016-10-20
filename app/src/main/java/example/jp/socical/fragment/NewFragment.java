package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import example.jp.socical.R;
import example.jp.socical.adapter.NewListAdapter;
import example.jp.socical.bean.NewBean;
import vn.app.base.callback.OnRecyclerViewItemClick;
import vn.app.base.util.FragmentUtil;

public class NewFragment extends NoHeaderFragment implements OnRecyclerViewItemClick{

    NewListAdapter newListAdapter;

    RecyclerView recyclerView;

    List<NewBean> newBeanList;

    @BindView(R.id.fabCamera)
    FloatingActionButton fabCamera;

    public static NewFragment newInstance() {
        NewFragment fragment = new NewFragment();
    return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        recyclerView = (RecyclerView)root.findViewById(R.id.rvNew);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fabCamera = (FloatingActionButton)root.findViewById(R.id.fabCamera);
    }

    @Override
    protected void initData() {

        // Xử lý khi ấn vào Floating Camera
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Di chuyển đến màn hình Detail
                FragmentUtil.pushFragment(getActivity(), UploadFragment.newInstance(), null);
            }
        });

        newBeanList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            newBeanList.add(new NewBean(null,
                    "Hien",
                    true,
                    "No Comnet",
                    "Address",
                    true,
                    null,
                    "Ha Noi"));
        }

        newListAdapter = new NewListAdapter(newBeanList);
        recyclerView.setAdapter(newListAdapter);
        newListAdapter.setOnRecyclerViewItemClick(this);

    }

    private void handleNewData(List<NewBean> newBeanList) {

    }

    @Override
    public void onItemClick(View view, int position) {
        FragmentUtil.pushFragment(getActivity(), ImageDetailFragment.newInstance(), null);
    }
    private void gotoDetail(NewBean newBean) {
        //FragmentUtil.pushFragment(getActivity(), ImageDetailFragment.newInstance(newBean), null);
    }

}
