package example.jp.socical.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.api.request.NearbyRequest;
import example.jp.socical.api.response.NearbyResponse;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.constant.CommonConstant;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.FragmentUtil;

public class NearbyFragment extends HeaderFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    GoogleMap mMap;
    GoogleApiClient googleApiClient;
    Location mCurrentLocation;

    SupportMapFragment fragment;

    double dbLat;
    double dbLong;

    public static NearbyFragment newInstance() {
        NearbyFragment nearbyFragment = new NearbyFragment();
        return nearbyFragment;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_NEARBY);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapNearby);
        mapFragment.getMapAsync(this);

        //SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        //FragmentTransaction fragmentTransaction =
        //        mapFragment.getChildFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.mapNearby, mapFragment);
        //fragmentTransaction.commit();

//        FragmentManager fm = getChildFragmentManager();
//        fragment = (SupportMapFragment)fm.findFragmentById(R.id.mapNearby);
//        if (fragment == null) {
//            fragment = SupportMapFragment.newInstance();
//            fm.beginTransaction().replace(R.id.mapNearby, fragment).addToBackStack("NearbyFragment").commit();
//        } else {
//            fm.beginTransaction().replace(R.id.mapNearby, fragment).addToBackStack("NearbyFragment").commit();
//        }

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getActivity().getLayoutInflater().inflate(R.layout.custome_info_window, null);
                NewsBean newsBean = (NewsBean)marker.getTag();
                TextView tvName = (TextView)v.findViewById(R.id.txtName);
                TextView tvCaption = (TextView)v.findViewById(R.id.txtCaption);
                tvName.setText(newsBean.user.username);
                tvCaption.setText(newsBean.image.caption);

                return v;
            }
        });

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (mCurrentLocation != null) {
            dbLat = mCurrentLocation.getLatitude();
            dbLong = mCurrentLocation.getLongitude();
            onRequestNearby();
        }
    }

    public void onRequestNearby() {
        NearbyRequest nearbyRequest = new NearbyRequest(dbLat, dbLong);
        nearbyRequest.setRequestCallBack(new ApiObjectCallBack<NearbyResponse>() {
            @Override
            public void onSuccess(NearbyResponse data) {
                hideCoverNetworkLoading();
                setMarketNearby(data.data);
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });

        nearbyRequest.execute();
        showCoverNetworkLoading();
    }

    public void setMarketNearby(List<NewsBean> inNewsBean) {
        if (inNewsBean != null) {
            int size = inNewsBean.size();
            String strLat;
            String strLong;
            LatLng latLng;

            for (int i = 0; i < size; i++) {
                NewsBean newsBean = inNewsBean.get(i);
                if (newsBean != null) {
                    strLat = newsBean.image.lat;
                    strLong = newsBean.image._long;

                    double dbLat = Double.valueOf(strLat).doubleValue();
                    double dbLong = Double.valueOf(strLong).doubleValue();

                    latLng = new LatLng(dbLat, dbLong);
                    mMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(getPinMapIcon())))
                    .setTag(newsBean);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }

            latLng = new LatLng(dbLat, dbLong);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }

    private Bitmap getPinMapIcon() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map_pin);
        Bitmap pinMap = Bitmap.createScaledBitmap(bitmap, 90, 160, true);
        return pinMap;
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker != null) {
            NewsBean newsBean = (NewsBean) marker.getTag();
            if (newsBean != null) {
                FragmentUtil.pushFragment(getActivity(), ImageDetailFragment.newInstance(newsBean), null, "ImageDetailFragment");
                return;
            }
        }
        DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Can not make trasition to ImageDetail srceen");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }
}
