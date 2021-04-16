package com.example.bigproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private ImageButton sidebar;
    GoogleSignInClient mGoogleSignInClient;
    TextView tv_userName,tv_Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //kiem tra du lieu cua tai khoan google da dang nhap
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);



        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setUpViewPager();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        sidebar = (ImageButton)findViewById(R.id.sidebar);
        sidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drawerLayout.isDrawerOpen(Gravity.LEFT)) drawerLayout.openDrawer(Gravity.START);
                else drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_categories:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_chart:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.side_bar);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        tv_userName = headerView.findViewById(R.id.userName);
        tv_Email = headerView.findViewById(R.id.emailV);
        if(!dataLocalLoginManager.getIdLogin().isEmpty()){
        List<Table_User> users = DatabasePro.getInstance(this).userDAO().getUserFromId(dataLocalLoginManager.getIdLogin());
        if (users != null && !users.isEmpty()){
            tv_userName.setText(users.get(0).getUser_name());
            tv_Email.setText(users.get(0).getEmail());
        }}

        Bundle bundle = getIntent().getExtras();
        ///
        if (bundle != null ) {
            String srl = bundle.getString("edit_cat");
            viewPager.setCurrentItem(1);
        }
    }

    private void setUpViewPager(){
        viewPager_Datadapter viewPager_datadapter = new viewPager_Datadapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPager_datadapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_categories).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_chart).setChecked(true);
                        break;
                    default:
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_account :
                startActivity(new Intent(MainActivity.this , account_view.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.nav_setting :
                startActivity(new Intent(MainActivity.this , Setting.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.nav_logout :
                revokeAccess();
                signOut();
                break;
        }
        return true;
    }

    private void signOut() {
        dataLocalLoginManager.setLoginState(false);
        dataLocalLoginManager.setIdLogin("");
        dataLocalLoginManager.setIdBank(0);
        Table_Category cat = new Table_Category(null,null,null);
        dataLocalLoginManager.setCategory(cat);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this , Login_activity.class));
                        Toast.makeText(MainActivity.this, "SignOut successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "delete account successfully",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}