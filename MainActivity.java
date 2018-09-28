package com.batm.semanai;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

//import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.btn_identify) Button _identifyButton;
    @Bind(R.id.button_callcamera) ImageButton _callCamera;

    public static final int REQUEST_MULTIPLE_PERMISSIONS = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 10;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;
    Uri fileUri = null;
    ImageView photoImage = null;
    ImageUploadHandler imgupload;
    Bitmap bmp, bmpApp;
    Uri photoUri = null;
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        photoImage = (ImageView) findViewById(R.id.photo_image);
        ImageButton callCameraButton = (ImageButton) findViewById(R.id.button_callcamera);

        if (checkPermissionsAndRequest()) { }

        callCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = Uri.fromFile(getOutputPhotoFile());
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ);
            }
        });

        _identifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { register(); }
        });

    // Used to load the 'native-lib' library on application startup.
    //static {
        //System.loadLibrary("native-lib"); }
    }

    // Permission Request
    private boolean checkPermissionsAndRequest() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int permissionStorage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissions = new ArrayList<>();
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {listPermissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                                                     listPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);}
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {listPermissions.add(android.Manifest.permission.CAMERA);}
        if (!listPermissions.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissions.toArray(new String[listPermissions.size()]),REQUEST_MULTIPLE_PERMISSIONS);
            return false;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "camera and storage permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                      // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            showDialogOK("SMS and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE: checkPermissionsAndRequest();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Camera methods
    private void showPhoto(Uri photoUri) {
        File imageFile = new File(photoUri.getPath().toString()); //photoUri.getPath().toString()
        InputStream iStream = null;
        InputStream iStream2 = null;
        Bitmap bmp_temp = null;
        if (imageFile.isFile()) {
            try { iStream = getContentResolver().openInputStream(photoUri); }
            catch (Exception ex) { Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();}
            bmp_temp = RotateBitmap(resizeImage(iStream,1), 90);
            bmp = scaleBitmap(bmp_temp,0.8,0.8);
            iStream2 = new ByteArrayInputStream(getArrayOutputFromImage(bmp_temp).toByteArray());
            bmpApp = RotateBitmap(resizeImage(iStream2,4), 0);
            //Toast.makeText(getBaseContext(), "Imagen subida exitosamente", Toast.LENGTH_LONG).show();
            photoImage.setImageBitmap(bmpApp);
        }
    }

    public Bitmap resizeImage(InputStream is, int sampleSize) {
        BitmapFactory.Options options;
        try {
            options = new BitmapFactory.Options();
            options.inSampleSize = sampleSize; // 1/3 of origin image size from width and height
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            return bitmap;
        } catch (Exception ex) { ex.printStackTrace(); }
        return null;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap scaleBitmap(Bitmap bitmapOrg, double scaleWidth, double scaleHeight) {
        // createa matrix for the manipulation
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = (int)(scaleWidth * width);
        int newHeight = (int)(scaleHeight * height);
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale((float)scaleWidth, (float)scaleHeight);
        // rotate the Bitmap
        matrix.postRotate(0);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 1, 1, newWidth, newHeight, matrix, true);
        return resizedBitmap;
    }

    private File getOutputPhotoFile() {
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName());
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e(TAG, "Failed to create storage directory.");
                return null;
            }
        }
        CharSequence charSeq = new String("yyyMMddHHmmss");
        String timeStamp = new DateFormat().format(charSeq, new Date()).toString();
        return new File(directory.getPath() + File.separator + "IMG_"
                + timeStamp + ".jpg");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    // A known bug here! The image should have saved in fileUri
                    //Toast.makeText(this, "Image loaded successfully", Toast.LENGTH_SHORT).show();
                    photoUri = fileUri;
                    showPhoto(photoUri);
                } else {
                    photoUri = data.getData();
                    //Toast.makeText(this, "Image loaded successfully in: " + data.getData(), Toast.LENGTH_SHORT).show();
                    showPhoto(photoUri);
                }
                // showPhoto(photoUri);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Callout for image capture failed!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public ByteArrayOutputStream getArrayOutputFromImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos;
    }

    public void register() {

        Log.d(TAG, "Register");

        _identifyButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Registry...");
        progressDialog.show();

        imgupload = new ImageUploadHandler();
        imgupload.setOnVariables(fileUri, bmp, null);
        imgupload.uploadImage(MainActivity.this);

        new Handler().postDelayed(new Runnable() {
            // TODO: Implement your own register logic here.
            public void run() {
                // On complete call either onRegisterSuccess or onRegisterFailed depending on success
                onRegisterSuccess();
                //onRegisterFailed();
                progressDialog.dismiss();
            }
        }, 3000);
    }

    public void onRegisterSuccess() {
        _identifyButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(),"Image Uploaded Succesfully!",Toast.LENGTH_SHORT).show();
        //finish();
        //Intent intent = new Intent(this, GraphActivity.class);
        //startActivity(intent);
    }

    public void onRegisterFailed() {
        Toast.makeText(getBaseContext(), "Unable to register", Toast.LENGTH_LONG).show();
        _identifyButton.setEnabled(true);
    }

}
