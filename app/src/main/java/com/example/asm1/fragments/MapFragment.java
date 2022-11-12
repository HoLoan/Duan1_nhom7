package com.example.asm1.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



import java.util.List;

public class MapFragment extends Fragment {
    private  String address;
    public static MapFragment newInstance(String address){
        MapFragment mapsFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("address",address);
        mapsFragment.setArguments(bundle);
        return mapsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() !=null){
            address = getArguments().getString("address");
        }
    }

    private
    OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            try {
                //chuyển từ address sang lat long
                Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
                List<Address> list = geocoder.getFromLocationName(address,3);
                Address location = list.get(0);

                //lấy lat long cho vào map
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(sydney).title(address));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            }catch (Exception e){
                Log.d(">>>>>>>>>>FRAGMENT_MAP_ERROR", e.getMessage());
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}
