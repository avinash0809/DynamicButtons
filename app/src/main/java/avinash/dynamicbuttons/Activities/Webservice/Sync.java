package avinash.dynamicbuttons.Activities.Webservice;

import android.os.CountDownTimer;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.RunnableFuture;

/**
 * Created by avinash on 5/6/18.
 */

public class Sync implements Serializable {

    private static volatile Sync ourInstance;
    private ArrayList<JSONObject> eventArray = new ArrayList<JSONObject>();
    private CountDownTimer timer;

    private Sync() {
        //Prevent form the reflection api.
        if (ourInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Sync getInstance() {
        if (ourInstance == null) { //if there is no instance available... create new one
            synchronized (Sync.class) {
                if (ourInstance == null) ourInstance = new Sync();
            }
        }

        return ourInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected Sync readResolve() {
        return getInstance();
    }

    public void event(JSONObject event) {
        eventArray.add(event);
        if (eventArray.size() >= 3) {
            if (timer != null) {
                timer.cancel();
            }
            syncToWebService();
        } else {
            timer = new CountDownTimer(3000, 3000) {
                @Override
                public void onTick(long l) {
                    // If any updates required use this method
                }

                public void onFinish() {
                    syncToWebService();
                }
            }.start();
        }
    }

    private void syncToWebService() {
        // Syncing to the server
    }

}
