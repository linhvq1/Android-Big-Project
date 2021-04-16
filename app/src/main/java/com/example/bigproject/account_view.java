package com.example.bigproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;

import java.util.ArrayList;
import java.util.List;

public class account_view extends AppCompatActivity {
    private EditText ed_Email, ed_Password, ed_ConfirmPass;
    private Button btn_SignUp;
    View viewHeaderDecoration, viewBottomDecoration;
    Animation righToLeft, leftToRight;
    Table_User useradd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_view);

        righToLeft = AnimationUtils.loadAnimation(this, R.anim.ani_signup_form_2);
        leftToRight = AnimationUtils.loadAnimation(this, R.anim.ani_signup_form);

        viewBottomDecoration = (View) findViewById(R.id.header_viewV);
        viewHeaderDecoration = (View) findViewById(R.id.bottom_viewV);

        viewHeaderDecoration.setAnimation(leftToRight);
        viewBottomDecoration.setAnimation(righToLeft);
        inItSignUpForm();
        handleSignUp();
    }

    public void inItSignUpForm() {
        ed_Email = (EditText) findViewById(R.id.editEmailV);
        ed_Password = (EditText) findViewById(R.id.editPassV);
        ed_ConfirmPass = (EditText) findViewById(R.id.editPassConfirmV);
        btn_SignUp = (Button) findViewById(R.id.buttonAcountV);

        if(!dataLocalLoginManager.getIdLogin().isEmpty()){
            List<Table_User> users = DatabasePro.getInstance(account_view.this).userDAO().getUserFromId(dataLocalLoginManager.getIdLogin());
            if (users != null && !users.isEmpty()){
                ed_Email.setText(users.get(0).getEmail());
                ed_Password.setText(users.get(0).getPassword());
                ed_ConfirmPass.setText(users.get(0).getPassword());
            }}
    }

    public void handleSignUp() {
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ed_Email.getText().toString())) {
                    ed_Email.setError("this field is not null");
                    return;
                }
                if (TextUtils.isEmpty(ed_Password.getText().toString())) {
                    ed_Password.setError("this field is not null");
                    return;
                }
                if (TextUtils.isEmpty(ed_ConfirmPass.getText().toString())) {
                    ed_ConfirmPass.setError("this field is not null");
                    return;
                }
                if (!ed_Password.getText().toString().equals(ed_ConfirmPass.getText().toString())) {
                    ed_ConfirmPass.setError("Confirm pass is wrong");
                    return;
                }
                if(!dataLocalLoginManager.getIdLogin().isEmpty()){
                    List<Table_User> users = DatabasePro.getInstance(account_view.this).userDAO().getUserFromId(dataLocalLoginManager.getIdLogin());
                    if (users == null || users.isEmpty()){
                        return;
                    }else{
                        useradd = users.get(0);
                        useradd.setEmail(ed_Email.getText().toString());
                        useradd.setPassword(ed_Password.getText().toString());
                    }
                }
                // insert vo database
                //Table_User useradd =
                if (!isUserExist(useradd)) {
                    Toast.makeText(v.getContext(), "this email is not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabasePro.getInstance(account_view.this).userDAO().updateUser(useradd);
                Toast.makeText(v.getContext(), "update user successfully", Toast.LENGTH_SHORT).show();
                //insertCategory();
                finish();
            }
        });
    }

    private boolean isUserExist(Table_User user) {
        List<Table_User> list = DatabasePro.getInstance(this).userDAO().checkEmailUser(user.getEmail());
        return list != null && !list.isEmpty();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}