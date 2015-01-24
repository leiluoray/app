package com.arcgis.android.sample.maps.helloworld;

import android.app.ActionBar;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.Symbol;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    View content;
    final  int Name_ID = 1;
    final static int Category_ID = 2;
    final static int hasLake_ID = 3;
    final static int Picture_ID = 4;

    final static int TITLE_ID = 5;

    Drawable drawable2 = getResources().getDrawable(R.drawable.ksu_icon);




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// MapView
        final MapView mMapView =new MapView(this);
        ArcGISTiledMapServiceLayer tileLayer= new ArcGISTiledMapServiceLayer(
                "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
        mMapView.enableWrapAround(true);
        Envelope env = new Envelope(-10781654.841 , 4399876.679, -10678467.132 , 4342726.564);
        mMapView.setExtent(env);
        mMapView.setScale(1128.497176);
        mMapView.addLayer(tileLayer);
        setContentView(mMapView);

//  GraphicsLayer
        final GraphicsLayer gl = new GraphicsLayer();
        mMapView.addLayer(gl);

// 武夷山
        Point pt1 = new Point();
        pt1.setX(-10731913.074 );    pt1.setY(4373947.460);
        Map<String,Object> attribute1 = new HashMap<String, Object>();
        attribute1.put("Title", "武夷山");
        attribute1.put("Category", "水");
        attribute1.put("hasLake", "Yes");
        attribute1.put("PictureID", "1");
        final Graphic pic1 = new Graphic(pt1, (Symbol) new PictureMarkerSymbol(getResources().getDrawable(R.drawable.ksu_icon)),attribute1);
        gl.addGraphic(pic1);
        int ID1 = gl.addGraphic(pic1);

// 泰山
        Point pt2 = new Point();
        pt2.setX(-10731963.074   );    pt2.setY(4373997.460);
        Map<String,Object> attribute2 = new HashMap<String, Object>();
        attribute2.put("Title", "泰山");
        attribute2.put("Category", "山");
        attribute2.put("hasLake", "Yes");
        attribute2.put("PictureID", "2");
        final Graphic pic2 = new Graphic(pt2, (Symbol) new PictureMarkerSymbol(getResources().getDrawable(R.drawable.ksu_icon)),attribute2);
        gl.addGraphic(pic2);
        int ID2 = gl.addGraphic(pic2);



        final String TAG = "MyActivity";
        final Callout callout = mMapView.getCallout();
        callout.setStyle(R.layout.calloutstyle);
        content = createContent();
        mMapView.setOnSingleTapListener(new OnSingleTapListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void onSingleTap(float x, float y) {
                callout.hide();
                int[] graphicIDs = gl.getGraphicIDs(x, y, 25);
                if(graphicIDs != null && graphicIDs.length > 0){
                    Graphic gr = gl.getGraphic(graphicIDs[0]);
                    updateContent(
                            (String) gr.getAttributeValue("Title"),
                            (String) gr.getAttributeValue("Category"),
                            (String) gr.getAttributeValue("hasLake"),
                            (String) gr.getAttributeValue("PictureID"));
                    Point location = (Point) gr.getGeometry();
                    callout.setOffset(0,-15);
                    callout.show(location,content);

                }

                Log.v(TAG, "OnSingleTapLinstener is running !");
            }
        });






    }


    public View createContent() {
        // create linear layout for the entire view
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        // create TextView for the title
        TextView titleView = new TextView(this);
        titleView.setId(TITLE_ID);
        titleView.setTextColor(Color.GRAY);
        layout.addView(titleView);

        TextView categoryView = new TextView(this);
        categoryView.setId(Category_ID);
        categoryView.setTextColor(Color.GRAY);
        layout.addView(categoryView);


        TextView hasLakeView = new TextView(this);
        hasLakeView.setId(hasLake_ID);
        hasLakeView.setTextColor(Color.GRAY);
        layout.addView(hasLakeView);

        ImageView pictureView = new ImageView(this);
        pictureView.setImageDrawable(drawable2);
        pictureView.setId(Picture_ID);
        layout.addView(pictureView);



        return layout;
    }

    public void updateContent( String title, String category, String haslake, String pictureID) {
        if (content == null)
            return;

        TextView txt1 = (TextView) content.findViewById(TITLE_ID);
        txt1.setText(title);

        TextView txt2 = (TextView) content.findViewById(Category_ID);
        txt2.setText(category);

        TextView txt3 = (TextView) content.findViewById
                (hasLake_ID);
        txt3.setText(haslake);

        ImageView image = (ImageView) content.findViewById
                (Picture_ID);
//        try{
//            int ID = (int)(pictureID);
//        }
        image.setImageDrawable(drawable1);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
