package example.jp.socical.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fenlisproject.hashtagedittext.HashTagEditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.api.request.UploadImageRequest;
import example.jp.socical.api.response.DataUploadResponse;
import example.jp.socical.api.response.UploadImageResponse;
import example.jp.socical.constant.APIConstant;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.Base64;
import vn.app.base.util.BitmapUtil;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.ImagePickerUtil;
import vn.app.base.util.NetworkUtils;
import vn.app.base.util.SharedPrefUtils;
import vn.app.base.util.StringUtil;

public class UploadFragment extends HeaderFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    final int CAMERA_PICTURE = 94;
    final int PIC_CROP = 95;

    @BindView(R.id.fbUpload)
    FloatingActionButton fabCamera;

    @BindView(R.id.btnCancel)
    Button btnUploadCancel;

    @BindView(R.id.btnPost)
    Button btnUploadPost;

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

    File fileCapture;

    String strimgPicture;
    String strcaption;
    String strlocation;
    String strlat;
    String strlong;
    String strhashtagView;

    double dbLat;
    double dbLong;

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
    }

    @Override
    protected void initData() {
        initValue();
    }

    public void selectImage() {

        ImagePickerUtil imagePickerUtil = new ImagePickerUtil();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picUri = Uri.fromFile(imagePickerUtil.createFileUri(getActivity()));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, CAMERA_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PICTURE && resultCode == Activity.RESULT_OK) {
            CropImage.activity(picUri).setAspectRatio(16, 9).start(getContext(), this);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = BitmapUtil.decodeFromFile(resultUri.getPath(), 1200, 800);
                    creatFilefromBitmap(bitmap);
                    ivPicture.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File creatFilefromBitmap(Bitmap bitmap) throws IOException {

        File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/InstagramFaker");
        imageDir.mkdir();
        fileCapture = new File(imageDir, "avatarCropped.jpg");
        OutputStream fOut = new FileOutputStream(fileCapture);
        Bitmap getBitmap = bitmap;
        getBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return fileCapture;
    }

    public void uploadImage() {

        Map<String, String> header = new HashMap<>();
        header.put(APIConstant.TOKEN, SharedPrefUtils.getAccessToken());

        //param value
        strcaption = etCaption.getText().toString();
        //getValuesHashTagView();
        strhashtagView = etHashtagView.getValues().toString();

        Map<String, String> params = new HashMap<>();

        if (!strcaption.isEmpty()) {
            params.put(APIConstant.FILE_CAPTION, strcaption);
        }

        if (!strhashtagView.isEmpty()) {
            params.put(APIConstant.FILE_HASHTAG, strhashtagView);
        }

        if (swSendLocation.isChecked()) {
            params.put(APIConstant.FILE_LOCATION, strlocation);
            params.put(APIConstant.LAT, strlat);
            params.put(APIConstant.LONG, strlong);
        }

        Map<String, File> filePart = new HashMap<>();
        filePart.put(APIConstant.FILE_IMAGE, fileCapture);

        UploadImageRequest uploadImageRequest = new UploadImageRequest(Request.Method.POST, APIConstant.IMAGE_UPLOAD, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                initialNetworkError();
            }
        }, UploadImageResponse.class, header, new Response.Listener<UploadImageResponse>() {
            @Override
            public void onResponse(UploadImageResponse response) {
                hideCoverNetworkLoading();
                if (response != null) {
                    reInit(response.data);
                }
            }
        }, params, filePart);

        NetworkUtils.getInstance(getActivity().getApplicationContext()).addToRequestQueue(uploadImageRequest);
        showCoverNetworkLoading();
    }

    private void reInit(DataUploadResponse data){
        ImageLoader.loadImage(getContext(), R.drawable.loading_list_image_220, data.image.url, ivPicture);
        etCaption.setText(data.image.caption);
        if (data.image.lat.isEmpty() || data.image._long.isEmpty() ) {
            swSendLocation.setEnabled(false);
        } else {
            swSendLocation.setEnabled(true);
        }
        etHashtagView.setText(data.image.hashtag.toString());
    }

    private void initValue() {
        strimgPicture = "";
        strcaption = "";
        strlocation = "";
        strlat = "";
        strlong = "";
    }

    @Override
    public void onStart() {
        super.onStart();
        if (swSendLocation.isChecked()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (swSendLocation.isChecked()) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Khi connect thanh cong
        // Lay thong tin vi tri nguoi dung

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mCurrentLocation != null) {

            dbLat = mCurrentLocation.getLatitude();
            dbLong = mCurrentLocation.getLongitude();

            strlat = Double.toString(dbLat);
            strlong = Double.toString(dbLong);

            getAddress();

        }
    }

    public void getAddress() {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(dbLat, dbLong, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            strlocation = address + ", " + state + ", " + city + ", " + country;

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    @OnCheckedChanged(R.id.swSendLocation)
    public void checkChangedSend(){
        if (swSendLocation.isChecked()){
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();
        }
    }
}
