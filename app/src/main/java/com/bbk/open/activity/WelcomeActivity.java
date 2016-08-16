package com.bbk.open.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bbk.open.globlesearch.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class WelcomeActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        cameraTask();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EasyPermissions.SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen
            // Let's show Toast for example
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        skip();

    }

    private void skip() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, requestCode + " denied", Toast.LENGTH_LONG).show();
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.rationale_ask_again),
                R.string.setting, R.string.cancel, null, perms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(2)
    public void cameraTask() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "运行需要权限", 2, Manifest.permission.READ_CONTACTS);
        } else {
            skip();
        }
    }


}
