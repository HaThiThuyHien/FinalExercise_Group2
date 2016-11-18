package example.jp.socical.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.api.request.NearbyRequest;
import example.jp.socical.api.response.NearbyResponse;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.api.volley.callback.ApiObjectCallBack;

public class NearbyFragment extends HeaderFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    GoogleMap mMap;
    GoogleApiClient googleApiClient;
    Location mCurrentLocation;

    double dbLat;
    double dbLong;

    public static NearbyFragment newInstance() {
        NearbyFragment nearbyFragment = new NearbyFragment();
        return nearbyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected void initView(View root) {
        super.initView(root);

        ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_NEARBY);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapNearby);
        mapFragment.getMapAsync(this);

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

        mMap.getUiSettings().setAllGesturesEnabled(false);
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
        mMap.setMyLocationEnabled(true);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getActivity().getLayoutInflater().inflate(R.layout.custome_info_window, null);

                LatLng latLng = marker.getPosition();

                TextView tvName = (TextView)v.findViewById(R.id.txtName);
                TextView tvCaption = (TextView)v.findViewById(R.id.txtCaption);

                tvName.setText("Latidue:" + latLng.latitude);
                tvCaption.setText("Longtidue" + latLng.longitude);

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

//            LatLng latLng = new LatLng(dbLat, dbLong);
//            mMap.addMarker(new MarkerOptions().position(latLng));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            Toast.makeText(getActivity(),"onConnected", Toast.LENGTH_LONG).show();

            onRequestNearby();
        }

    }

    public void onRequestNearby() {
        NearbyRequest nearbyRequest = new NearbyRequest(dbLat, dbLong);
        nearbyRequest.setRequestCallBack(new ApiObjectCallBack<NearbyResponse>() {
            @Override
            public void onSuccess(NearbyResponse data) {
                Toast.makeText(getActivity(),"success", Toast.LENGTH_LONG).show();
                setMarketNearby(data.data);
            }

            @Override
            public void onFail(int failCode, String message) {
                Toast.makeText(getActivity(),"fail", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setMarketNearby(List<NewsBean> inNewsBean) {
        if (inNewsBean != null) {
            int size = inNewsBean.size();
            String strLat;
            String strLong;
            String strName;

            LatLng latLng;

            for (int i = 0; i < size; i++) {
                NewsBean newsBean = inNewsBean.get(i);
                if (newsBean != null) {
                    strLat = newsBean.image.lat;
                    strLong = newsBean.image._long;
                    strName =  newsBean.user.username;

                    double dbLat = Double.valueOf(strLat).doubleValue();
                    double dbLong = Double.valueOf(strLong).doubleValue();

                    latLng = new LatLng(dbLat, dbLong);
                    mMap.addMarker(new MarkerOptions().position(latLng));
                }
            }

            latLng = new LatLng(dbLat, dbLong);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            //LatLng latLng = new LatLng(dbLat, dbLong);
            //mMap.addMarker(new MarkerOptions().position(latLng));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
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
        Toast.makeText(getActivity(), "InforWindow Click", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        marker.showInfoWindow();
        return false;
    }
}
