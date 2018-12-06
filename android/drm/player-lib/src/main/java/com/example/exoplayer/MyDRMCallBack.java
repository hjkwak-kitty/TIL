package com.example.exoplayer;

import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.drm.MediaDrmCallback;

import java.util.UUID;

/**
 * drm-quick-start-android
 * Class: MyDRMCallBack
 * Created by hjkwak on 05/12/2018.
 * <p>
 * Description:
 */
public class MyDRMCallBack implements MediaDrmCallback {

    String keyString;

    public MyDRMCallBack(String keyId, String keyValue) {
        this.keyString = "{\"keys\":[{\"kty\":\"oct\",\"k\":\""+keyValue+"\",\"kid\":\""+keyId+"\"}],\"type\":\"temporary\"}";
    }


    @Override
    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest request) throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest request) throws Exception {
        return keyString.getBytes();
    }
}