package example.jp.socical.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.R;

public class RegisterFragment extends NoHeaderFragment {

    @BindView(R.id.btnSkip)
    Button btnSkip;

    public static RegisterFragment newInstance() {
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        btnSkip = (Button)root.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gọi đến giao diện Home
            }
        });
    }

    @Override
    protected void initData() {

    }

}
