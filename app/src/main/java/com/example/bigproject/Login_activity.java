package com.example.bigproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.broadCastCheckInternet.CheckInternetBroadcastReciever;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.ArrayList;
import java.util.List;

public class Login_activity extends AppCompatActivity {
    GoogleSignInButton signInButton;
    //SignInButton signInButton;
    private final int SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private EditText edEmail,edPassword;
    private Button btnLogin;
    private TextView tvSwitchSignUpForm;
    private boolean checkFomatLogin;
    CheckInternetBroadcastReciever checkInternet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        checkInternet = new CheckInternetBroadcastReciever();
        
        handleCategory();
        inItLoginForm();
        loginOk();
        SignUpWithGoogleAcc();
        handleLogin();

    }
    public void handleCategory(){
        List<Table_Category> catTab = DatabasePro.getInstance(this).catDAO().getListCategory();
        if(catTab.isEmpty() || catTab == null){
            insertCategory();
        }
    }

    public void inItLoginForm(){
        edEmail = (EditText)findViewById(R.id.ed_email);
        edPassword = (EditText)findViewById(R.id.ed_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvSwitchSignUpForm =(TextView) findViewById(R.id.tv_signup);
    }
    public void loginOk(){
        if(!dataLocalLoginManager.getLoginSuccess()){
            //Toast.makeText(Login_activity.this,"chua dang nhap nay",Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(Login_activity.this,MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
        }
    }
    public void handleLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edEmail.getText().toString())){ edEmail.setError("this field is not null"); return;}
                if(TextUtils.isEmpty(edPassword.getText().toString())){ edPassword.setError("this field is not null"); return;}
                // kiem tra roi dua vo main
                // set session
                if(isUserExist(edEmail.getText().toString(),edPassword.getText().toString())){
                    dataLocalLoginManager.initLoginData(getApplicationContext());
                    dataLocalLoginManager.setLoginState(true);
                    dataLocalLoginManager.setIdLogin(getIdUser(edEmail.getText().toString()));
                    if(getIdCurrentBank() !=0) {
                        dataLocalLoginManager.setIdBank(getIdCurrentBank());
                    }
                    //Toast.makeText(Login_activity.this,"id current user"+dataLocalLoginManager.getIdLogin(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login_activity.this,"login successfully",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login_activity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                }else {
                    Toast.makeText(Login_activity.this,"Email or password is wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSwitchSignUpForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_activity.this, signup.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    private int getIdCurrentBank(){
        if( TextUtils.isEmpty(dataLocalLoginManager.getIdLogin())) return 0;
        List<Table_Bank> list = DatabasePro.getInstance(Login_activity.this).bankDAO().getIDCurrentState(Integer.parseInt(dataLocalLoginManager.getIdLogin()));
        if(list != null && !list.isEmpty())
            return list.get(0).getId_u_bank();
        else return 0;
    }
    private String getIdUser(String email){
        List<Table_User> list = DatabasePro.getInstance(this).userDAO().getIdUserFromEmail(email);
        if( list != null && !list.isEmpty())
            return String.valueOf(list.get(0).getId_u());
        else return "";
    }
    private boolean isUserExist(String email, String pass){
        List<Table_User> list = DatabasePro.getInstance(this).userDAO().checkLoginUser(email,pass);
        return list != null && !list.isEmpty();
    }
    public void SignUpWithGoogleAcc(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){

            Toast.makeText(this,"Already login",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login_activity.this,MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
        }else{
            Toast.makeText(this," login fail",Toast.LENGTH_SHORT).show();
        }


        signInButton = (GoogleSignInButton) findViewById(R.id.SignGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataLocalLoginManager.getInternetState() == 100){
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,SIGN_IN);
                }else{
                    Toast.makeText(Login_activity.this,"Please check your connection!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            dataLocalLoginManager.setLoginState(true);

            Table_User u = new Table_User(account.getDisplayName(),account.getEmail(),null);
            if(isEmailUserExist(u)){
                dataLocalLoginManager.setIdLogin(getIdUser(u.getEmail()));
                if(getIdCurrentBank() !=0) {
                    dataLocalLoginManager.setIdBank(getIdCurrentBank());
                }
            startActivity(new Intent(Login_activity.this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
            }else{
                DatabasePro.getInstance(Login_activity.this).userDAO().InsertUser(u);
                dataLocalLoginManager.setIdLogin(getIdUser(u.getEmail()));

                if(getIdCurrentBank() !=0) {
                    dataLocalLoginManager.setIdBank(getIdCurrentBank());
                }
                //insertCategory();
                startActivity(new Intent(Login_activity.this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("LOL", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this,"Login fail",Toast.LENGTH_SHORT).show();
        }
    }
    private void insertCategory(){
        List<Table_Category> inCat = new ArrayList<>();
        inCat.add(new Table_Category("food","baseline_restaurant_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("tea","baseline_coffee_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("taxi","baseline_local_taxi_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("fuel","baseline_two_wheeler_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("calling fee","baseline_settings_phone_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("books","baseline_book_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("party","baseline_local_bar_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("ship fee","baseline_local_shipping_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("new phone","baseline_phone_iphone_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("new home","baseline_store_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("pet food","baseline_pets_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("shopping","baseline_shopping_cart_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("sports","baseline_sports_tennis_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("films","baseline_movie_creation_white_36dp.png","Expenses"));
        inCat.add(new Table_Category("new tivi","baseline_tv_white_36dp.png","Expenses"));

        inCat.add(new Table_Category("salary","baseline_work_white_36dp.png","Income"));
        inCat.add(new Table_Category("celebration","baseline_celebration_white_36dp.png","Income"));
        inCat.add(new Table_Category("gift","baseline_volunteer_activism_white_36dp.png","Income"));
        inCat.add(new Table_Category("salary","baseline_work_white_36dp.png","Income"));
        inCat.add(new Table_Category("ices cream","baseline_icecream_white_36dp.png","Income"));
        inCat.add(new Table_Category("donate","baseline_videogame_asset_white_36dp.png","Income"));
        inCat.add(new Table_Category("account balance","baseline_leaderboard_white_36dp.png","Income"));

        for (Table_Category tcat :inCat) {
            DatabasePro.getInstance(this).catDAO().InsertCat(tcat);
        }
    }
    private boolean isEmailUserExist(Table_User user){
        List<Table_User> list = DatabasePro.getInstance(this).userDAO().checkEmailUser(user.getEmail());
        return list != null && !list.isEmpty();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // dang ki br
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkInternet,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // hu dang ki br
        unregisterReceiver(checkInternet);
    }
}