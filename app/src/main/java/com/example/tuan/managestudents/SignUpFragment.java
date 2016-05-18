package com.example.tuan.managestudents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ui.BaseFragment;

/**
 * Created by tuan on 05/17/2016.
 */
public class SignUpFragment extends BaseFragment {

    private static final String TAG = "SignupActivity";

    EditText edtEmail;
    EditText edtName;
    EditText edtPassword;
    Button btnSignUp;
    TextView tvLoginLink;
    ImageView ivLogo;


    @Override
    public String getTagName() {
        return "SignUpFragment";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_signup,container,false);

        edtEmail = (EditText) root.findViewById(R.id.edtEmail);
        edtName = (EditText) root.findViewById(R.id.edtName);
        edtPassword = (EditText) root.findViewById(R.id.edtPassword);
        btnSignUp = (Button) root.findViewById(R.id.btnSignUp);
        tvLoginLink = (TextView) root.findViewById(R.id.tvLoginLink);
        tvLoginLink.setVisibility(View.GONE);

        ivLogo = (ImageView) root.findViewById(R.id.ivLogo_Sign);
        ivLogo.setVisibility(View.GONE);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singup();
            }
        });

        return root;
    }


    private void singup() {
        Log.d(TAG,"SignUpActivity");
        if(!Validate()){
            onSignUpFailed();
            return;
        }

        btnSignUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    private void onSignupSuccess() {
        btnSignUp.setEnabled(true);

        //Luu thong tin dang ky

        SharedPreferences sharedPreferences = getActivity().
                getSharedPreferences("sharedPref", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userName",edtName.getText().toString());
            editor.putString("email",edtEmail.getText().toString());
            editor.putString("password",edtPassword.getText().toString());

            editor.apply();

            //di vao man hinh main
            Intent intent = new Intent(getActivity(), PracticeMain.class);
            startActivity(intent);
        }

    private void onSignUpFailed() {
        Toast.makeText(getActivity(),"Login Faile",Toast.LENGTH_LONG).show();
        btnSignUp.setEnabled(true);
    }

    private boolean Validate() {
        boolean valid = true;

        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            edtName.setError("at least 3 character");
            valid = false;
        } else {
            edtName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            edtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtPassword.setError("between 4 and 10 alphanumberic character");
            valid = false;
        } else {
            edtPassword.setError(null);
        }
        return valid;
    }
}
