package com.example.t_teng.e_bus;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveListener {

    GoogleMap map;

    TextView PicLat, PicLng, PicWide, PicHigh;                //For Calibating Image
    LatLngBounds kmuttbounds,mapbounds;
    GroundOverlay mapOverlay,red_line,yellow_line,map_label;
    GroundOverlayOptions mapOverlayOptions, map_labelOverlayOptions;
    GroundOverlayOptions red_lineOptions, yellow_lineOptions;
    AdjustableImageButton ButtonCar1, ButtonCar2;

    LatLng FIBO = new LatLng(13.654651298468655, 100.49434728920461),Slope = new LatLng(13.6535745277100925, 100.49498297274111);
    LatLng Micro = new LatLng(13.65338816859215, 100.49468927085401),Chem = new LatLng(13.652567798440824, 100.49504734575747);
    LatLng Math = new LatLng(13.652869491687582, 100.49437612295152),PHY = new LatLng(13.652663584459466, 100.49469094723463);
    LatLng Lib = new LatLng(13.653128830282432, 100.49396473914383),SIT = new LatLng(13.652708871032964, 100.4936619848013);
    LatLng Construction = new LatLng(13.65174970606427, 100.49425609409809),Athikan = new LatLng(13.651956265697496,100.49502991139889);
    LatLng SoLa = new LatLng(13.651971904274363, 100.49336023628713),CB1 = new LatLng(13.65159006205979, 100.4934886470437);
    LatLng CB2 = new LatLng(13.65144375928168, 100.49402743577957),CB3 = new LatLng(13.649782167621014, 100.49204394221307);
    LatLng CB4 = new LatLng(13.649414983186944, 100.49273259937763),CB5 = new LatLng(13.649686380432438, 100.49358788877726);
    LatLng KFC = new LatLng(13.651026420118669, 100.49180220812559),ChemEng = new LatLng(13.650411951127193, 100.49312822520733);
    LatLng Red_Building = new LatLng(13.64998872897697, 100.49412097781898),LX = new LatLng(13.65001805155226, 100.49528237432241);
    LatLng Dorm_Girl = new LatLng(13.64885948124886, 100.49483176320791),Dorm_Boy = new LatLng(13.649465483298274, 100.49456890672445);

    Marker mFIBO,mSlope,mMicro,mChem,mMath,mPHY,mLib,mSIT,mConstruction,mAthikan,mSoLa,mCB1;
    Marker mCB2,mCB3,mCB4,mCB5,mKFC,mChemEng,mRed_Building,mLX,mDorm_Girl,mDorm_Boy;
    Marker click_map;

    double map_lat = 13.65111348977032, map_lng = 100.49399703936434,
            redline_lat = 13.65167348975617, redline_lng = 100.49378103936965,
            yellowline_lat = 13.651433489762228, yellowline_lng = 100.4934210339381;

    float zoom;
    float map_wide = 685f , map_high = 884f,
            redline_wide = 378f,redline_high = 527f,
            yellowline_wide = 457f, yellowline_high = 504f;

    /**
     *  OnCreate()
     *  It's used when Activity is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        kmuttbounds = new LatLngBounds(new LatLng(13.647080, 100.490774), new LatLng(13.655056, 100.497254));
        mapbounds = new LatLngBounds(new LatLng(13.642637, 100.484570), new LatLng(13.659925, 100.504021));
        setOverlay();
    }

    /**
     *   setOverlay()
     *   Setting Image & line of the maps
     */
    private void setOverlay() {
        //Initial Map
        mapOverlayOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.kmuttmap))
                .position(new LatLng(map_lat, map_lng), map_wide, map_high);

        //Red Line
        red_lineOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.red_line))
                .position(new LatLng(redline_lat, redline_lng), redline_wide, redline_high);

        //Yellow Line
        yellow_lineOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.yellow_line))
                .position(new LatLng(yellowline_lat, yellowline_lng), yellowline_wide, yellowline_high);

        //Map with label
        map_labelOverlayOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.kmuttmap_label2))
                .position(new LatLng(map_lat, map_lng), map_wide, map_high);

    }

    /**
     *   onMapReady()
     *   This function is working when Map is showing on SmartPhone
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Declare_Marker();
        PicLat = (TextView) findViewById(R.id.PicLat);
        PicLng = (TextView) findViewById(R.id.PicLng);
        ButtonCar1 = (AdjustableImageButton) findViewById(R.id.Y_line);
        ButtonCar2 = (AdjustableImageButton) findViewById(R.id.R_line);
        ButtonCar1.setOnClickListener(this);
        ButtonCar2.setOnClickListener(this);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setLatLngBoundsForCameraTarget(mapbounds);    //Scoping Bounds
        map.setMinZoomPreference(16.5f);               //Minimum Zoom
        map.setMaxZoomPreference(18);                  //Maximum Zoom

        mapOverlay = map.addGroundOverlay(mapOverlayOptions);
        map_label = map.addGroundOverlay(map_labelOverlayOptions);
        yellow_line = map.addGroundOverlay(yellow_lineOptions);
        red_line = map.addGroundOverlay(red_lineOptions);
        yellow_line.setVisible(false);
        red_line.setVisible(false);

        map.setOnCameraIdleListener(this);
        map.setOnCameraMoveListener(this);


        boolean success = map.setMapStyle(new MapStyleOptions(getResources() //Set Map Style
                .getString(R.string.style_json)));
        if (!success) {
            Log.e("MapsActivityString", "Style parsing failed.");
        }

    }
    /***********************************************************************************************/

    /**
     *  This function work when Camera stop moving
     *  CameraLat = Get latitude of center of camera after camera stop moving
     *  CameraLong = Get longigude of center of camera after camera stop moving
     *  If CameraLat & CameraLong are outside 'kmuttmap', Camera will move to the center of 'mapbounds'
     */
    @Override
    public void onCameraIdle() {
        double CameraLat = map.getCameraPosition().target.latitude;
        double CameraLong = map.getCameraPosition().target.longitude;
        if (CameraLat <= 13.647080 || CameraLat >= 13.655056) {
            map.animateCamera(CameraUpdateFactory.newLatLng(mapbounds.getCenter()));
        }
        if (CameraLong <= 100.490774 || CameraLong >= 100.497254) {
            map.animateCamera(CameraUpdateFactory.newLatLng(mapbounds.getCenter()));
        }
    }

    /**
     *   Work when Camera is moving.
     *   zoom = Get camera's zoom
     *   Set Visibility of 'map_label'
     */
    @Override
    public void onCameraMove() {
        zoom = map.getCameraPosition().zoom;
        if(zoom >= 17.2f){
            map_label.setVisible(true);
        }
        else{
            map_label.setVisible(false);
        }
    }

    /**
     *   Under this comment OnClick()
     *   To Calibate each images include map, red&yellow line, map_label
     *   Calibate this value (Lattitute, Longitude, wide, high)
     */
    @Override
    public void onClick(View v) {
        if (v == ButtonCar1){
            if(red_line.isVisible()){
                red_line.setVisible(false);
            }
            if(yellow_line.isVisible()){
                yellow_line.setVisible(false);
            }
            else{
                yellow_line.setVisible(true);
            }
        }

        if( v == ButtonCar2) {
            if(yellow_line.isVisible()){
                yellow_line.setVisible(false);
            }
            if(red_line.isVisible()){
                red_line.setVisible(false);
            }
            else{
                red_line.setVisible(true);
            }
        }

    }


    public void Declare_Marker(){
        mFIBO = map.addMarker(new MarkerOptions()
                .position(FIBO)
                .title("FIBO")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mSlope = map.addMarker(new MarkerOptions()
                .position(Slope)
                .title("ตึกปฏิบัติการพื้นฐานทางวิทยาศาสตร์")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMicro = map.addMarker(new MarkerOptions()
                .position(Micro)
                .title("ตึกภาควิชาจุลชีววิทยา")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mChem = map.addMarker(new MarkerOptions()
                .position(Chem)
                .title("ตึกภาควิชาเคมี")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMath = map.addMarker(new MarkerOptions()
                .position(Math)
                .title("ตึกภาควิชาคณิตศาสตร์")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mPHY = map.addMarker(new MarkerOptions()
                .position(PHY)
                .title("ตึกภาควิชาฟิสิกส์")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mLib = map.addMarker(new MarkerOptions()
                .title("หอสมุด")
                .position(Lib)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.lib)));
        mSIT = map.addMarker(new MarkerOptions()
                .title("ตึกคณะ SIT")
                .position(SIT)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mConstruction = map.addMarker(new MarkerOptions()
                .title("เขตก่อสร้าง")
                .position(Construction)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.construction)));
        mAthikan = map.addMarker(new MarkerOptions()
                .position(Athikan)
                .title("ตึกอธิการบดี")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mSoLa = map.addMarker(new MarkerOptions()
                .position(SoLa)
                .title("ตึกศิลปศาสตร์")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mCB1 = map.addMarker(new MarkerOptions()
                .position(CB1)
                .title("CB1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mCB2 = map.addMarker(new MarkerOptions()
                .position(CB2)
                .title("CB2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mCB3 = map.addMarker(new MarkerOptions()
                .position(CB3)
                .title("CB3")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mCB4 = map.addMarker(new MarkerOptions()
                .position(CB4)
                .title("CB4")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mCB5 = map.addMarker(new MarkerOptions()
                .position(CB5)
                .title("CB5")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mKFC = map.addMarker(new MarkerOptions()
                .title("โรงอาหาร")
                .position(KFC)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.canteen)));
        mChemEng = map.addMarker(new MarkerOptions()
                .position(ChemEng)
                .title("ตึกวิศวกรรมเคมี")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mRed_Building = map.addMarker(new MarkerOptions()
                .position(Red_Building)
                .title("ตึกแดง")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mLX = map.addMarker(new MarkerOptions()
                .position(LX)
                .title("โรงเรียนดรุณสิกขาลัย")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mDorm_Girl = map.addMarker(new MarkerOptions()
                .title("หอหญิง")
                .position(Dorm_Girl)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dorm_f)));
        mDorm_Boy = map.addMarker(new MarkerOptions()
                .title("หอชาย")
                .position(Dorm_Boy)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dorm_m)));
    }

}

