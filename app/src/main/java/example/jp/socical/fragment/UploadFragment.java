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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
import example.jp.socical.api.response.UploadImageResponse;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.Base64;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.NetworkUtils;
import vn.app.base.util.SharedPrefUtils;

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
    //ArrayList<String> strhashtagView;
    String strhashtagView;

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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileCapture = new File(getActivity().getExternalCacheDir(),
                String.valueOf(System.currentTimeMillis() + ".jpg"));
        picUri = Uri.fromFile(fileCapture);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, CAMERA_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PICTURE && resultCode == Activity.RESULT_OK) {
            //Bundle extras = data.getExtras();

            //picUri = data.getData();

            //bitmap = (Bitmap) extras.get("data");
            //ivPicture.setImageBitmap(bitmap);

            //picUri = getImageUri(getActivity().getApplicationContext(), bitmap);
            //performCrop();
        } else if (requestCode == PIC_CROP) {
            Bundle extras = data.getExtras();
            //bitmap = extras.getParcelable("data");
            //ivPicture.setImageBitmap(bitmap);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void performCrop() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");
        cropIntent.putExtra("crop", true);
        cropIntent.putExtra("aspectX", 16);
        cropIntent.putExtra("aspectY", 9);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, PIC_CROP);
    }

    public void uploadImage() {

        //getValuesHashTagView();

        //strimgPicture = getStringImage(bitmap);
        //strcaption = etCaption.getText().toString();

        String url = "https://polar-plains-86888.herokuapp.com/api/image/upload";

        Map<String, String> header = new HashMap<>();
        header.put("token", SharedPrefUtils.getAccessToken());

        //param value
        strcaption = etCaption.getText().toString();
        //getValuesHashTagView();
        strhashtagView = etHashtagView.getValues().toString();


        Map<String, String> params = new HashMap<>();

        if (!strcaption.isEmpty()) {
            params.put("caption", strcaption);
        }

        if (!strhashtagView.isEmpty()) {
            params.put("hashtag", strhashtagView);
        }

        if (swSendLocation.isChecked()) {
            params.put("location", strlocation);
            params.put("lat", strlat);
            params.put("long", strlong);
        }

        Map<String, File> filePart = new HashMap<>();
        filePart.put("image", fileCapture);

        UploadImageRequest uploadImageRequest_test = new UploadImageRequest(Request.Method.POST, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Fail", error.getMessage());
            }
        }, UploadImageResponse.class, header, new Response.Listener<UploadImageResponse>() {
            @Override
            public void onResponse(UploadImageResponse response) {
                //Log.i("Success", response.toString());
                if (response != null) {
                    ImageLoader.loadImage(getActivity(), response.data.image.url, ivPicture);
                }
            }
        }, params, filePart);

        NetworkUtils.getInstance(getActivity().getApplicationContext()).addToRequestQueue(uploadImageRequest_test);
    }

    private String getRealPathFromUri(Uri tempUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(tempUri, proj, null, null, null);
            if (cursor == null) return null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void getValuesHashTagView() {

//        int size = etHashtagView.getValues().size();
//
//        strhashtagView = new ArrayList<>();
//
//        for (int i = 0; i < size; i++) {
//            strhashtagView.add(etHashtagView.getValues().get(i));
//        }
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
