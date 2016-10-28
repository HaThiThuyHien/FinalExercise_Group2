package example.jp.socical.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.api.request.UploadImageRequest;
import example.jp.socical.api.response.UploadImageResponse;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.Base64;

public class UploadFragment extends HeaderFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    final int CAMERA_PICTURE = 91;
    final int PIC_CROP = 92;

    @BindView(R.id.fbUpload)
    FloatingActionButton fabCamera;

    MainActivity mainActivity;

    String strimgPicture;
    String strcaption;
    String strlocation;
    String strlat;
    String strlong;
    String strhashtag;

    ImageView imgPicture;
    Button btnPost;

    Bitmap bitmap;

    EditText etCaption;
    EditText etHashtag;
    Switch swSendLocation;

    Uri picUri;


    final Linkify.TransformFilter filter = new Linkify.TransformFilter() {
        @Override
        public String transformUrl(Matcher match, String url) {
            return match.group();
        }
    };

    final Pattern hashtagPattern = Pattern.compile("#([ء-يA-Za-z0-9_-]+)");
    final String hashtagScheme = "content://com.hashtag.jojo/";

    final Pattern urlPattern = Patterns.WEB_URL;

    //Location
    protected GoogleApiClient mGoogleApiClient;

    protected Location mCurrentLocation;


    public static UploadFragment newInstance() {
        UploadFragment uploadFragment = new UploadFragment();
        return uploadFragment;
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
        imgPicture = (ImageView) root.findViewById(R.id.imgPostPicture);
        fabCamera = (FloatingActionButton) root.findViewById(R.id.fbUpload);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnPost = (Button) root.findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();

        swSendLocation = (Switch)root.findViewById(R.id.swSendLocation);

        etHashtag = (EditText)root.findViewById(R.id.etHashtag);
        etHashtag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println(count);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Linkify.addLinks(s, hashtagPattern, hashtagScheme, null,filter);
                Linkify.addLinks(s, urlPattern, null, null, filter);
            }
        });

        etCaption = (EditText) root.findViewById(R.id.etCaption);

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
            imgPicture.setImageBitmap(bitmap);
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

        strimgPicture = getStringImage(bitmap);
        strcaption = etCaption.getText().toString();

        UploadImageRequest uploadImageRequest = new UploadImageRequest(strimgPicture, strcaption, strlocation, strlat, strlong, strhashtag);
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
        strhashtag = "";
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
}
