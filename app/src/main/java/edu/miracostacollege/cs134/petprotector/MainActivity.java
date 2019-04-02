package edu.miracostacollege.cs134.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ImageView petImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect pet image view to layout then use setImage URI


        petImageView = findViewById(R.id.petImageView);
        petImageView.setImageURI(getUriToResource(this, R.drawable.none));


    }

    private static Uri getUriToResource(Context context, int id) throws Resources.NotFoundException
    {
        Resources res = context.getResources();

        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(id) +
                "/" + res.getResourceTypeName(id) +
                "/" + res.getResourceEntryName(id);

        //android.Resource://edu.miracosta.cs134.petprotector/drawable/none
        return Uri.parse(uri);

    }

    public void selectPetImage(View v) {

       
        //make a list (empty) of permissions
        // as user grants them add each permission tot list
        List<String> permsList = new ArrayList<>();
        int permReqCode = 100;
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        //check t see if we have it enabled (Camera perms)
        //if denied add to list of permissions requested
        if(hasCameraPerm == PackageManager.PERMISSION_DENIED)
            permsList.add(Manifest.permission.CAMERA);

        int hasStorageR = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(hasStorageR == PackageManager.PERMISSION_DENIED)
            permsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int hasStorageW = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(hasStorageW == PackageManager.PERMISSION_DENIED)
            permsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // now that we have built the list, lets ask the user

        if(permsList.size() > 0)
        {
            //convert list into an array
            String[] perms = new String[permsList.size()];
            permsList.toArray(perms);

            //make request to user(backwards compatibile)
            ActivityCompat.requestPermissions(this,perms, permReqCode);
        }
    }

}
