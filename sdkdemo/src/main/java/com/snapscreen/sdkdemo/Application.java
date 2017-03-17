package com.snapscreen.sdkdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.snapscreen.sdk.S2Log;
import com.snapscreen.sdk.SnapscreenKit;
import com.snapscreen.sdk.SnapscreenKitListener;

import de.greenrobot.event.EventBus;

/**
 * Created by martin on 08/03/2017.
 */

public class Application extends android.app.Application implements SnapscreenKitListener {
    @Override
    public void onCreate() {
        super.onCreate();
        S2Log.DEBUG_ENABLED = true;

        // TODO: Add your clientID and clientSecret here
        SnapscreenKit.init(this, null, null, true, this);

        SnapscreenKit.getInstance().setCountryCode("AT");
    }

    @Override
    public void snapscreenKitDidReceiveInvalidClientIdAndSecret() {
        // Handle error when invalid client credentials are passed
    }
}
