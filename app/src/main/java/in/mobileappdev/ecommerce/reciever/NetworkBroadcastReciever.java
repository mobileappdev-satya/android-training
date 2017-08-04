package in.mobileappdev.ecommerce.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Techjini on 8/4/2017.
 */

public class NetworkBroadcastReciever  extends BroadcastReceiver{
    private static final String TAG = "NetworkBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "OnRecieve");
        if(intent == null){
            return;
        }

        if(intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)){
            int state = intent.getIntExtra("state", -1);
            if( state != 0){
                Toast.makeText(context,"Headset Plugged in", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context,"Headset UN Plugged", Toast.LENGTH_LONG).show();
            }

        }
    }

}
