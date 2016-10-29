package example.jp.socical.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import vn.app.base.util.FragmentUtil;

public class TutorialFragment extends NoHeaderFragment {

    @BindView(R.id.btnSkip)
    Button btnSkip;

    public static TutorialFragment newInstance() {
        TutorialFragment tutorialFragment = new TutorialFragment();
        return tutorialFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tutorial;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);

        ((MainActivity) getActivity()).setToolbar(0);

        btnSkip = (Button)root.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gọi đến màn hình Home
                FragmentUtil.pushFragment(getActivity(), HomeFragment.newInstance(), null);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
