package com.example.huykhoahuy.finalproject.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.wearable.DataMap.TAG;


public class MapFragment extends Fragment implements OnMapReadyCallback{
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;
    private  ArrayList<LotteryCompany> lotteryCompanies;
    private  ArrayList<LotteryCompany> lotteryCenters;
    View mView;

    // biến toàn cục mới được thêm vào
    ArrayList<Polyline> polylineArrayList = new ArrayList<Polyline>();
    Location currentLocation;
    // kết thúc thêm biến toàn cục

    public MapFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseHostFile parseHostFile1 = new ParseHostFile(getContext(),R.xml.main_host);
        lotteryCompanies = parseHostFile1.lotteryCompanies;

        ParseHostFile parseHostFile2 = new ParseHostFile(getContext(),R.xml.hosts);
        lotteryCenters = parseHostFile2.lotteryCompanies;

    }


    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(mView.getContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(mView.getContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(getActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_map, container, false);
        getLocationPermission();
        return mView;

    }


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mView.getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            for(LotteryCompany company : lotteryCompanies)
            {
                float lat = Float.valueOf(company.getLatitude());
                float lon = Float.valueOf(company.getLongitude());
                LatLng pos = new LatLng(lat,lon);
                mMap.addMarker(new MarkerOptions().position(pos).title(company.getName())).showInfoWindow();
            }

            for(LotteryCompany company : lotteryCenters)
            {
                float lat = Float.valueOf(company.getLatitude());
                float lon = Float.valueOf(company.getLongitude());
                LatLng pos = new LatLng(lat,lon);
                mMap.addMarker(new MarkerOptions().position(pos).title(company.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).showInfoWindow();
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            // thêm mới việc tạo chỉ đường
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (polylineArrayList.size() > 0) {
                        for (int i = 0; i < polylineArrayList.size(); ++i) {
                            polylineArrayList.get(i).remove();
                        }
                        polylineArrayList.clear();
                    }
                    findWays(mMap, new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()) , marker.getPosition());
                    return false;
                }
            });
            // kết thúc thêm mới việc tạo chỉ đường
        }

    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mView.getContext());

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                            // chỉ đường cho lottery company
                            if (target != null) {
                                findWays(mMap, new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()) , new LatLng(Float.valueOf(target.getLatitude()),Float.valueOf(target.getLongitude())));
                                target = null;
                            }
                            // kết thúc chỉ đường cho lottery company
                        }else{
                        }
                    }
                });
            }
        }catch (SecurityException e){
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    //initialize our map
                }
            }
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // Đây là những hàm được thêm vào, search tên hàm để kiểm tra đoạn code cũ đã bị thay đổi ở chỗ nào

    private void findWays(final GoogleMap map, final LatLng latLngFrom, final LatLng latLngTo) {
        String serverKey = "AIzaSyDtuVqMpP-3C15I4Fpm25dgshHXl3UEi48"; // cách lấy server key ở đây: http://www.akexorcist.com/2015/12/google-direction-library-for-android-en.html
        GoogleDirection.withServerKey(serverKey).from(latLngFrom).to(latLngTo).execute(new DirectionCallback() {
            @Override
            public void onDirectionSuccess(Direction direction, String rawBody) {
                if (direction.isOK()) {
                    List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                    ArrayList<PolylineOptions> polylineOptionsArrayList = DirectionConverter.
                            createTransitPolyline(getContext(), stepList, 5, Color.RED, 3, Color.BLUE );
                    for (PolylineOptions polylineOptions: polylineOptionsArrayList)
                        polylineArrayList.add(map.addPolyline(polylineOptions)); //polylineArrayList là biến ỏ ngoài hàm này, dùng để quản lý polyline vẽ trên bản đồ gồm thêm, sửa hoặc xoá polyline đó
                }
            }

            @Override
            public void onDirectionFailure(Throwable t) {
            }
        });
    }

    LotteryCompany target = null;
    public void FindWayToLotteryCompany(LotteryCompany lotteryCompany) {
        target = lotteryCompany;
    }
    // Kết thúc thêm các hàm mới

}
