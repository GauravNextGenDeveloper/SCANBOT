package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.scanbot.Database.ScanBotDatabase;
import com.example.scanbot.Database.model.Register;
import com.example.scanbot.R;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ScanBotDatabase scanBotDatabase;
    private List<Register> registerList;
    TextInputEditText mobilenoTv,PwdTv;
    Button btnsub;
    String mobile,pwd;
    private ProgressDialog progressDialog;
    SharedPreferences.Editor editor;
    private String MY_PREFS_NAME = "SCANBOOT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerList = new ArrayList<>();
        scanBotDatabase = ScanBotDatabase.getInstance(LoginActivity.this);

        editor =  getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();


        initialize();
    }

    private void initialize() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("loading.....");


        mobilenoTv=findViewById(R.id.mobilenoEt);
        PwdTv=findViewById(R.id.passwordEt);
        btnsub=findViewById(R.id.btnsub);

        new RetrieveTask(this).execute();

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobile=mobilenoTv.getText().toString();
                pwd=PwdTv.getText().toString();

                if (registerList.size()!=0)
                {
                    for(int i=0;i<registerList.size();i++)
                    {
                        if (registerList.get(i).getMobile().equalsIgnoreCase(mobile))
                        {
                            Register register = registerList.get(i);
                            if (register.getPassword().equalsIgnoreCase(pwd))
                            {

                                editor.putString("name", register.getName());
                                editor.putString("email", register.getEmail());
                                editor.putString("mobile", register.getMobile());
                                editor.putLong("id", register.getUser_id());
                                editor.apply();
                                editor.commit();

                                Intent in=new Intent(LoginActivity.this,DashBordActivity.class);
                                in.putExtra("userid",register.getUser_id());
                                startActivity(in);
                                finish();
                            }
                        }
                    }
                }
            }
        });


    }


    private class RetrieveTask extends AsyncTask<Void, Void, List<Register>> {


        private WeakReference<LoginActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(LoginActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            ShowProgress();
            super.onPreExecute();
        }

        @Override
        protected List<Register> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().scanBotDatabase.getUsers().getUser();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Register> registers) {
            HideProgress();

            if (registers != null && registers.size() > 0) {

                registerList.addAll(registers);
            }
        }
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
