package com.mycyberlaw.lecturer_manager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mycyberlaw.Config;
import com.mycyberlaw.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.Placeholders;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.util.UUID;

public class UploadNotesActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonChooseFile;

    TextView textViewFileChosen;
    EditText editTextFilename;

    EditText editTextMsgDev;

    Button buttonUpload;

    private int PICK_FILE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Uri filePath;
    String staffID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload Notes");

        requestStoragePermission();

        buttonChooseFile = (Button) findViewById(R.id.buttonChooseFile);

        textViewFileChosen = (TextView) findViewById(R.id.textViewFileChosen);
        editTextFilename = (EditText) findViewById(R.id.editTextFilename);
        textViewFileChosen.setText(null);

        editTextMsgDev = (EditText) findViewById(R.id.editTextMsgDev);

        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        if (textViewFileChosen.getText().toString().isEmpty()) {
            editTextFilename.setEnabled(false);
            editTextMsgDev.setEnabled(false);
            buttonUpload.setEnabled(false);
        }

        buttonChooseFile.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.buttonChooseFile:
                showFileChooser();
                break;
            case R.id.buttonUpload:
                uploadMultipart();
                break;
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

    /*
    * This is the method responsible for image upload
    * We need the full image path and the name for the image in this method
    * */
    public void uploadMultipart() {
        //getting name for the file
        String filename = editTextFilename.getText().toString().trim();
        String msgDev = editTextMsgDev.getText().toString();

        if (filename.isEmpty()) {
            editTextFilename.setError("Required");
        } else {
            new MaterialDialog.Builder(this)
                    .content("See notification for upload progress")
                    .positiveText("OK")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // TODO

                        }
                    })
                    .show();

            if (msgDev.isEmpty()) msgDev = "no";

            //getting the actual path of the image
            String path = getPath(filePath);

            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            staffID = sharedPreferences.getString(Config.STAFFID_SHARED_PREF,"Not Available");

            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();
                final ProgressDialog[] loading = new ProgressDialog[1];

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, Config.UPLOAD_NOTES_URL)
                        .addFileToUpload(path, "application") //Adding file
                        .addParameter("filename", filename) //Adding text parameter to the request
                        .addParameter("staffID", staffID)
                        .addParameter("msgDev", msgDev)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .setDelegate(new UploadStatusDelegate() {
                            @Override
                            public void onProgress(UploadInfo uploadInfo) {
                                loading[0] = ProgressDialog.show(UploadNotesActivity.this, null, "Uploading at " + Placeholders.UPLOAD_RATE + " (" + Placeholders.PROGRESS + ")", true, true);
                            }

                            @Override
                            public void onError(UploadInfo uploadInfo, Exception exception) {
                                loading[0].dismiss();
                                new MaterialDialog.Builder(UploadNotesActivity.this)
                                        .content("Error during upload")
                                        .positiveText("OK")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                // TODO

                                            }
                                        })
                                        .show();
                            }

                            @Override
                            public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                                // your code here
                                // if you have mapped your server response to a POJO, you can easily get it:
                                // YourClass obj = new Gson().fromJson(serverResponse.getBodyAsString(), YourClass.class);
                                loading[0].dismiss();
                                new MaterialDialog.Builder(UploadNotesActivity.this)
                                        .content("Upload completed successfully in " + Placeholders.ELAPSED_TIME)
                                        .positiveText("OK")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                // TODO

                                            }
                                        })
                                        .show();
                            }

                            @Override
                            public void onCancelled(UploadInfo uploadInfo) {
                                // your code here
                            }
                        })
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            textViewFileChosen.setText(filePath.toString());

            editTextFilename.setEnabled(true);
            editTextMsgDev.setEnabled(true);
            buttonUpload.setEnabled(true);
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        String path;

        if (cursor == null) {
            path = uri.getPath();
            Log.d("debug", "path "+path);
        } else {
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            Log.d("debug", "doc id "+document_id);
            cursor.close();

            cursor = getContentResolver().query(
                    uri, // uri
                    null, // projection - column to return
                    null, // selection - row to return
                    new String[]{document_id}, // selectionArgs
                    null, // sortOrder
                    null); // cancellationSignal
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)); //MediaStore.Images.Media.DATA
            cursor.close();
        }

        return path;
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
