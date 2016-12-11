package example.jp.socical.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.api.request.RegisterRequest;
import example.jp.socical.api.response.RegisterResponse;
import example.jp.socical.commonclass.StringEncryption;
import example.jp.socical.constant.APIConstant;
import example.jp.socical.constant.CommonConstant;
import example.jp.socical.constant.FragmentActionConstant;
import example.jp.socical.manager.UserManager;
import vn.app.base.util.BitmapUtil;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.KeyboardUtil;
import vn.app.base.util.NetworkUtils;
import vn.app.base.util.SharedPrefUtils;
import vn.app.base.util.StringUtil;

public class RegisterFragment extends NoHeaderFragment {

    public static final String REGISTER_PHOTO = "REGISTER_PHOTO";

    String userName;
    String email;
    String pass;
    String confirmPass;

    File avatarFile;
    Uri uriFile;

    @BindView(R.id.imgAvatar)
    CircleImageView ivAvatar;

    @BindView(R.id.userName)
    EditText etUserName;

    @BindView(R.id.email)
    EditText etEmail;

    @BindView(R.id.password)
    EditText etPass;

    @BindView(R.id.confirmPass)
    EditText etConfirmPass;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

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
        ((MainActivity) getActivity()).setToolbar(0);
        etConfirmPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    register();
                    return true;
                }
                return false;
            }
        });
        etUserName.requestFocus();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.imgAvatar)
    public void onSelectPicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        uriFile = Uri.fromFile(creatFileUri(getActivity()));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile);
        startActivityForResult(intent, FragmentActionConstant.REQUEST_CAMERA);
    }

    public File creatFileUri(Context context) {
        File[] externalFile = ContextCompat.getExternalFilesDirs(context, null);
        if (externalFile == null) {
            externalFile = new File[]{context.getExternalFilesDir(null)};
        }
        final File root = new File(externalFile[0] + File.separator + "InstagramFaker" + File.separator);
        root.mkdir();
        final String fname = REGISTER_PHOTO;
        final File sdImageMainDirectory = new File(root, fname);
        if (sdImageMainDirectory.exists()) {
            sdImageMainDirectory.delete();
        }
        return sdImageMainDirectory;
    }

    @OnClick(R.id.btnSignUp)
    public void clickBtnSignUp(){
        register();
    }

    private void register(){
        userName = etUserName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        confirmPass = etConfirmPass.getText().toString().trim();

        if (!StringUtil.checkStringValid(userName)){
            DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "User name is blank");
            return;
        }

        if (!StringUtil.checkStringValid(email)) {
            DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Email is blank");
            return;
        }

        if (!StringUtil.checkStringValid(password)) {
            DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Password is blank");
            return;
        }

        if (!password.equals(confirmPass)) {
            DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Password and ConfirmPassword are not the same");
            return;
        }

        try {
            pass = StringEncryption.SHA1(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, File> filePart = new HashMap<>();
        if (avatarFile != null) {
            filePart.put(APIConstant.AVATAR, avatarFile);
        }

        Map<String, String> params = new HashMap<>();
        params.put(APIConstant.USER_NAME, userName);
        params.put(APIConstant.EMAIL, email);
        params.put(APIConstant.PASS, pass);

        RegisterRequest registerRequest = new RegisterRequest(Request.Method.POST, APIConstant.RIGISTER, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                initialNetworkError();
            }
        }, RegisterResponse.class, null, new Response.Listener<RegisterResponse>() {
            @Override
            public void onResponse(RegisterResponse response) {
                if (response.status == 1) {
                    hideCoverNetworkLoading();
                    SharedPrefUtils.saveAccessToken(response.dataRegister.token);
                    UserManager.saveCurrentUser(response.dataRegister);
                    FragmentUtil.pushFragment(getActivity(), TutorialFragment.newInstance(), null, "TutorialFragment");
                }
            }
        }, params, filePart);

        NetworkUtils.getInstance(getActivity().getApplicationContext()).addToRequestQueue(registerRequest);
        KeyboardUtil.hideKeyboard(getActivity());
        showCoverNetworkLoading();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FragmentActionConstant.REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            CropImage.activity(uriFile).setAspectRatio(1,1).start(getContext(), this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();

                try {
                    Bitmap bitmap = BitmapUtil.decodeFromFile(resultUri.getPath(), 800, 800);
                    creatFilefromBitmap(bitmap);
                    ivAvatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File creatFilefromBitmap(Bitmap bitmap) throws IOException {

        File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/InstagramFaker");
        imageDir.mkdir();
        avatarFile = new File(imageDir, "avatarCropped.jpg");
        OutputStream fOut = new FileOutputStream(avatarFile);
        Bitmap getBitmap = bitmap;
        getBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return avatarFile;
    }
}
