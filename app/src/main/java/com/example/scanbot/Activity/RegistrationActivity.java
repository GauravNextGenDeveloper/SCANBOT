package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.scanbot.Database.ScanBotDatabase;
import com.example.scanbot.Database.model.Register;
import com.example.scanbot.R;
import com.google.android.material.textfield.TextInputEditText;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    private ScanBotDatabase scanBotDatabase;
    private Register register;

    TextInputEditText nametv,mobiletv,emailtv,passwordtv,cnfpwdtv;
    Button btnsub;

    String name,mobile,email,password,cnfpassword;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initialize();



    }

    private void initialize() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("loading.....");

        nametv=findViewById(R.id.nametv);
        mobiletv=findViewById(R.id.mobiletv);
        emailtv=findViewById(R.id.emailtv);
        passwordtv=findViewById(R.id.passwordtv);
        cnfpwdtv=findViewById(R.id.cnfpwdtv);

        btnsub=findViewById(R.id.btnsub);



        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nametv.getText().toString();
                mobile = mobiletv.getText().toString();
                email = emailtv.getText().toString();
                password = passwordtv.getText().toString();
                cnfpassword = cnfpwdtv.getText().toString();


                if (name.equalsIgnoreCase(""))
                {
                    message("Please Enter Name");
                }else if (mobile.equalsIgnoreCase(""))
                {
                    message("Please Enter Mobile");
                }else if (mobile.length()<10)
                {
                    message("Please Enter Valid Mobile");

                }else if (email.equalsIgnoreCase(""))
                {
                    message("Please Enter Valid email");
                }else if (password.equalsIgnoreCase(""))
                {
                    message("Please Enter Password");
                }else if (cnfpassword.equalsIgnoreCase(""))
                {
                    message("Please Enter Confirm Password");

                }else if (!cnfpassword.equalsIgnoreCase(password))
                {
                    message("Confirm Password Not Matched with Password");
                }else
                {
                    scanBotDatabase = ScanBotDatabase.getInstance(RegistrationActivity.this);
                    register = new Register();
                    register.setName(name);
                    register.setMobile(mobile);
                    register.setEmail(email);
                    register.setPassword(password);

                    new InsertTask(RegistrationActivity.this, register).execute();
                }
            }
        });


        String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
            }
        });

    }


    private class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private Register register;
        private WeakReference<RegistrationActivity> activityReference;
        // only retain a weak reference to the activity
        InsertTask(RegistrationActivity context, Register register) {
            activityReference = new WeakReference<>(context);
            this.register = register;
        }


        @Override
        protected void onPreExecute() {

            ShowProgress();
            super.onPreExecute();

        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            long j = activityReference.get().scanBotDatabase.getUsers().insertRegister(register);
            register.setUser_id(j);
            Log.e("ID ", "doInBackground: " + j);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {

            HideProgress();
            if (bool) {

                Intent i =new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    private void message(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }


    private void ShowProgress()
    {
        if (!progressDialog.isShowing())
        {
            progressDialog.show();
        }
    }

    private void HideProgress()
    {
        if (progressDialog.isShowing())
        {
            progressDialog.cancel();
        }
    }

}
