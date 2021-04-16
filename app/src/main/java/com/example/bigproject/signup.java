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

import java.util.ArrayList;
import java.util.List;

public class signup extends AppCompatActivity {
    private EditText ed_Username, ed_Email,ed_Password,ed_ConfirmPass;
    private Button btn_SignUp;
    View viewHeaderDecoration,viewBottomDecoration;
    Animation righToLeft,leftToRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        righToLeft = AnimationUtils.loadAnimation(this,R.anim.ani_signup_form_2);
        leftToRight = AnimationUtils.loadAnimation(this,R.anim.ani_signup_form);

        viewBottomDecoration = (View) findViewById(R.id.header_view);
        viewHeaderDecoration = (View) findViewById(R.id.bottom_view);

        viewHeaderDecoration.setAnimation(leftToRight);
        viewBottomDecoration.setAnimation(righToLeft);
        inItSignUpForm();
        handleSignUp();
    }
    public void inItSignUpForm(){
        ed_Username = (EditText)findViewById(R.id.editName);
        ed_Email = (EditText) findViewById(R.id.editEmail);
        ed_Password = (EditText)findViewById(R.id.editPass);
        ed_ConfirmPass = (EditText)findViewById(R.id.editPassConfirm);
        btn_SignUp = (Button)findViewById(R.id.buttonAcount);
    }
    public void handleSignUp(){
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(ed_Username.getText().toString())){ ed_Username.setError("this field is not null"); return;}
                if(TextUtils.isEmpty(ed_Email.getText().toString())){ ed_Email.setError("this field is not null"); return;}
                if(TextUtils.isEmpty(ed_Password.getText().toString())){ ed_Password.setError("this field is not null"); return;}
                if(TextUtils.isEmpty(ed_ConfirmPass.getText().toString())){ ed_ConfirmPass.setError("this field is not null"); return;}
                if(!ed_Password.getText().toString().equals(ed_ConfirmPass.getText().toString())){ ed_ConfirmPass.setError("Confirm pass is wrong"); return;}
                // insert vo database
                Table_User useradd = new Table_User(ed_Username.getText().toString(),ed_Email.getText().toString(),ed_Password.getText().toString());
                if(isUserExist(useradd)){
                    Toast.makeText(v.getContext(),"this account is Exist",Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabasePro.getInstance(signup.this).userDAO().InsertUser(useradd);
                Toast.makeText(v.getContext(),"add user successfully",Toast.LENGTH_SHORT).show();
                //insertCategory();
                finish();
            }
        });
    }

    private boolean isUserExist(Table_User user){
        List<Table_User> list = DatabasePro.getInstance(this).userDAO().checkEmailUser(user.getEmail());
        return list != null && !list.isEmpty();
    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}