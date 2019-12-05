package gw.example.drone;

import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.navdata.*;



public class MainActivity extends Activity implements AcceleroListener{

    IARDrone drone = new ARDrone();

    @Override
    public void receivedRawData(AcceleroRawData d) {

    }

    @Override
    public void receivedPhysData(AcceleroPhysData d) {

    }

    enum dStatus{fliegend,stehend};
    dStatus dstatus;
    Button b;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dstatus = dStatus.stehend;

        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dstatus == dStatus.stehend) {

                    dstatus = dStatus.fliegend;
                    b.setText("Landen");
                }
                else{

                    dstatus = dStatus.stehend;
                    b.setText("Starten");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
