package example.jp.socical.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.fenlisproject.hashtagedittext.HashTagEditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.api.request.UploadImageRequest;
import example.jp.socical.api.response.UploadImageResponse;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.Base64;
import vn.app.base.util.FragmentUtil;

public class UploadFragment extends HeaderFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    final int CAMERA_PICTURE = 94;
    final int PIC_CROP = 95;

    @BindView(R.id.fbUpload)
    FloatingActionButton fabCamera;

    @BindView(R.id.btnCancel)
    Button btnUploadCancel;

    @BindView(R.id.btnPost)
    Button btnUploadPost;

    MainActivity mainActivity;

    String strimgPicture;
    String strcaption;
    String strlocation;
    String strlat;
    String strlong;
    ArrayList<String> strhashtagView;

    @BindView(R.id.imgPostPicture)
    ImageView ivPicture;

    Bitmap bitmap;

    @BindView(R.id.etCaption)
    EditText etCaption;

    @BindView(R.id.hashtagView)
    HashTagEditText etHashtagView;

    @BindView(R.id.swSendLocation)
    Switch swSendLocation;

    Uri picUri;

    //Location
    protected GoogleApiClient mGoogleApiClient;

    protected Location mCurrentLocation;


    public static UploadFragment newInstance() {
        UploadFragment uploadFragment = new UploadFragment();
        return uploadFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    public String getScreenTitle() {
        return "Post ";
    }

    @Override
    protected void initView(View root) {
        super.initView(root);

        ((MainActivity) getActivity()).setToolbar(HeaderOption.MENU_UPLOAD);

//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();

    }

    @Override
    protected void initData() {
        initValue();
    }

    public void selectImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PICTURE && resultCode == Activity.RESULT_OK) {
            //Bundle extras = data.getExtras();

            picUri = data.getData();
            //bitmap = (Bitmap) extras.get("data");
            //imgPicture.setImageBitmap(bitmap);
            performCrop();
        } else if (requestCode == PIC_CROP){
            Bundle extras = data.getExtras();
            bitmap = extras.getParcelable("data");
            ivPicture.setImageBitmap(bitmap);
        }
    }

    private void performCrop(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");
        cropIntent.putExtra("crop", true);
        cropIntent.putExtra("aspectX", 16);
        cropIntent.putExtra("aspectY", 9);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, PIC_CROP);
    }

    public void uploadImage() {

        getValuesHashTagView();

        strimgPicture = getStringImage(bitmap);
        strcaption = etCaption.getText().toString();

        UploadImageRequest uploadImageRequest = new UploadImageRequest(strimgPicture, strcaption, strlocation, strlat, strlong, strhashtagView);
        uploadImageRequest.setRequestCallBack(new ApiObjectCallBack<UploadImageResponse>() {
            @Override
            public void onSuccess(UploadImageResponse data) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFail(int failCode, String message) {
                //Log.i("Connect Error", message);
            }
        });
        uploadImageRequest.execute();
    }

    public void getValuesHashTagView() {

        int size = etHashtagView.getValues().size();

        strhashtagView = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            strhashtagView.add(etHashtagView.getValues().get(i));
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void initValue() {
        strimgPicture = "";
        strcaption = "";
        strlocation = "";
        strlat = "";
        strlong = "";
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Khi connect thanh cong
        // Lay thong tin vi tri nguoi dung
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        double dbLat;
//        double dbLong;
//
//        dbLat = mCurrentLocation.getLatitude();
//        dbLong = mCurrentLocation.getLongitude();
//
//        strlat = Double.toString(dbLat);
//        strlong = Double.toString(dbLong);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @OnClick(R.id.btnCancel)
    public void clickBtnCancel(){
        FragmentUtil.popBackStack(getActivity());
    }

    @OnClick(R.id.btnPost)
    public void clickBtnPost() {
        uploadImage();
    }

    @OnClick(R.id.fbUpload)
    public void clickFbUpload() {
        selectImage();
    }
}
