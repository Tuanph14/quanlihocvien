package com.example.tuan.managestudents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import ui.BaseFragment;

/**
 * Created by tuan on 05/17/2016.
 */
public class Login_Fragment extends BaseFragment {
    private  static final String TAG = "LoginActivity";
    private  static final int REQUEST_SIGNUP = 0;

    EditText edtEmail;
    EditText edtPassword;
    Button   btnLogin;
    TextView tvSignUpLink;
    ImageView ivLogo;


    @Override
    public String getTagName() {
        return "Login_Fragment";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_login,container,false);
        edtEmail = (EditText) root.findViewById(R.id.edtEmail);
        edtPassword = (EditText) root.findViewById(R.id.edtPassword);
        tvSignUpLink = (TextView) root.findViewById(R.id.tvSignUpLink);
        tvSignUpLink.setVisibility(View.GONE);
        ivLogo = (ImageView) root.findViewById(R.id.ivLogo);
        ivLogo.setVisibility(View.GONE);

        btnLogin = (Button) root.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return root;
    }

    public void login(){
        Log.d(TAG,"login");

        if(!validate()){
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    private void onLoginSuccess() {
        btnLogin.setEnabled(true);
        Intent intent = new Intent(getActivity(), PracticeMain.class);
        startActivity(intent);
    }

    private void onLoginFailed() {
        Toast.makeText(getActivity(),"Login failed",Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    public  boolean validate(){
        boolean valid = true;

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if(email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Enter a valid email address");
            valid = false;
        }else {
            edtEmail.setError(null);
        }
        if(password.isEmpty()||password.length()<4||password.length()>10){
            edtPassword.setError("Between 4 and 10 alphanumberic characters");
            valid = false;
        }else {
            edtPassword.setError(null);
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        if(email.equals(sharedPreferences.getString("email",""))&&
                password.equals(sharedPreferences.getString("password",""))){
            valid = true;
        }else{
            valid = false;
        }


        return valid;
    }

}
