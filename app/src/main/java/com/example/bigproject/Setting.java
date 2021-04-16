package com.example.bigproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Setting extends AppCompatActivity {
    private Switch sw_Budget,sw_Balance;
    TextView tv_monneyBank,tv_dateMonneyBank,tv_dayOfMonth;
    RelativeLayout rl_edit_monney,rl_edit_date;
    ImageButton bt_back;
    String date_start,date_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color_setting_form));
        }
        inItSettingForm();
        handleSettingForm();
        Toast.makeText(Setting.this,"id bank naycreate : "+String.valueOf(dataLocalLoginManager.getIdBank()),Toast.LENGTH_LONG).show();
        Toast.makeText(Setting.this,"id current user"+dataLocalLoginManager.getIdLogin(),Toast.LENGTH_SHORT).show();
    }
    public void inItSettingForm(){
        sw_Budget = (Switch)findViewById(R.id.sw_budget);
        sw_Balance = (Switch)findViewById(R.id.sw_balance);

        tv_monneyBank = (TextView)findViewById(R.id.monney_bank);
        tv_dateMonneyBank = (TextView)findViewById(R.id.current_date_setting);
        tv_dayOfMonth = (TextView)findViewById(R.id.monney_bank_date) ;
        rl_edit_monney = (RelativeLayout)findViewById(R.id.edit_monneybank);
        rl_edit_date = (RelativeLayout)findViewById(R.id.edit_dateStart_monneybank);
        bt_back = (ImageButton)findViewById(R.id.bt_back_setting);
    }
    public void handleSettingForm(){

        List<Table_Bank> listBank =  DatabasePro.getInstance(Setting.this).bankDAO().getValuesfromIDBank(dataLocalLoginManager.getIdBank());
        if(listBank != null && !listBank.isEmpty()){
            tv_monneyBank.setText(String.valueOf(listBank.get(0).getCurrent_monney()));
        }else{
            tv_monneyBank.setText("0");
        }

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sw_Budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sw_Budget.isChecked()){
                    sw_Balance.setChecked(true);
                    sw_Budget.setChecked(false);
                    rl_edit_monney.setEnabled(false);
                }else{
                    rl_edit_monney.setEnabled(true);
                sw_Balance.setChecked(false);
                sw_Budget.setChecked(true);}
            }
        });

        sw_Balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sw_Balance.isChecked()){
                    sw_Budget.setChecked(true);
                    sw_Balance.setChecked(false);
                    rl_edit_monney.setEnabled(true);
                }else{
                sw_Budget.setChecked(false);
                sw_Balance.setChecked(true);
                rl_edit_monney.setEnabled(false);}
            }
        });

        rl_edit_monney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sw_Budget.isChecked()) return;

            }
        });
        if(TextUtils.isEmpty(date_start) && TextUtils.isEmpty(date_end)){
            Calendar c= Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH,1);
            Date datec = c.getTime();
            String[] dc = datec.toString().split(" ");
            c.add(Calendar.DATE,29);
            String[] dn = c.getTime().toString().split(" ");
            date_start = dc[1] + " " + "1" + "," + dc[5];
            date_end = dn[1] + " " + dn[2] + "," + dn[5];
        }
        rl_edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] dayOfMonth = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};
                AlertDialog.Builder b = new AlertDialog.Builder(Setting.this);
                b.setTitle("Select date start");
                b.setSingleChoiceItems(dayOfMonth, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_dayOfMonth.setText(dayOfMonth[which]);
                        if(dayOfMonth[which].equals("1")){
                            tv_dateMonneyBank.setText("this month");
                            Calendar c= Calendar.getInstance();
                            c.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dayOfMonth[which]));
                            Date datec = c.getTime();
                            String[] dc = datec.toString().split(" ");
                            c.add(Calendar.DATE,29);
                            String[] dn = c.getTime().toString().split(" ");
                            date_start = dc[1] + " " + dayOfMonth[which] + "," + dc[5];

                            date_end = dn[1] + " " + dn[2] + "," + dn[5];

                        }else{

                            if(Integer.parseInt(dayOfMonth[which]) < 11){
                                Calendar c= Calendar.getInstance();
                                c.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dayOfMonth[which]));
                                Date datec = c.getTime();
                                String[] dc = datec.toString().split(" ");
                                c.add(Calendar.DATE,29);
                                String[] dn = c.getTime().toString().split(" ");
                                tv_dateMonneyBank.setText(new StringBuilder().append(dc[1]).append(" ").append(dayOfMonth[which]).append(",").append(dc[5]).append(" -")
                                .append(dn[1]).append(" ").append(dn[2]).append(",").append(dn[5]));
                                date_start = dc[1] + " " + dayOfMonth[which] + "," + dc[5];

                                date_end = dn[1] + " " + dn[2] + "," + dn[5];

                            }else{
                                Calendar c= Calendar.getInstance();
                                c.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dayOfMonth[which]));
                                c.add(Calendar.MONTH,-1);
                                Date datec = c.getTime();
                                String[] dc = datec.toString().split(" ");

                                c.add(Calendar.DATE,30);
                                String[] dn = c.getTime().toString().split(" ");
                                tv_dateMonneyBank.setText(new StringBuilder().append(dc[1]).append(" ").append(dayOfMonth[which]).append(",").append(dc[5]).append(" -")
                                        .append(dn[1]).append(" ").append(dn[2]).append(",").append(dn[5]));
                                date_start = dc[1] + " " + dayOfMonth[which] + "," + dc[5];
                                date_end = dn[1] + " " + dn[2] + "," + dn[5];

                            }

                        }
                        dialog.cancel();
                    }
                });
                b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                b.show();

            }
        });
        rl_edit_monney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(Setting.this);
                input.setText(tv_monneyBank.getText().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                AlertDialog.Builder b = new AlertDialog.Builder(Setting.this);

                b.setTitle("amount");
                b.setView(input);
                b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(TextUtils.isEmpty(input.getText().toString())){
                            dialog.cancel();
                        }else{
                        tv_monneyBank.setText(input.getText().toString());
                            Toast.makeText(Setting.this,dataLocalLoginManager.getIdLogin(),Toast.LENGTH_LONG).show();
                            Toast.makeText(Setting.this,date_start,Toast.LENGTH_LONG).show();
                            Toast.makeText(Setting.this,date_end,Toast.LENGTH_LONG).show();
                            Toast.makeText(Setting.this,tv_monneyBank.getText().toString(),Toast.LENGTH_LONG).show();

                            if(!checkDateUpdate(date_start,date_end,Integer.parseInt(dataLocalLoginManager.getIdLogin()))){
                                Table_Bank bank = new Table_Bank(Integer.parseInt(dataLocalLoginManager.getIdLogin()),Double.parseDouble(tv_monneyBank.getText().toString()),Double.parseDouble(tv_monneyBank.getText().toString()),date_start,date_end,1);
                        DatabasePro.getInstance(Setting.this).bankDAO().updateCurrentState(Integer.parseInt(dataLocalLoginManager.getIdLogin()));
                        DatabasePro.getInstance(Setting.this).bankDAO().InsertBank(bank);
                        dataLocalLoginManager.setIdBank(getIdCurrentBank());
                        Log.e("meo1","ok");
                        Toast.makeText(Setting.this,"id user: "+dataLocalLoginManager.getIdLogin(),Toast.LENGTH_LONG).show();
                        Toast.makeText(Setting.this,"id bank nayhandle: "+String.valueOf(dataLocalLoginManager.getIdBank()),Toast.LENGTH_LONG).show();
                        dialog.cancel();
                            }else{
                                Log.e("meo2","ok");
                                DatabasePro.getInstance(Setting.this).bankDAO().updateCurrentState(Integer.parseInt(dataLocalLoginManager.getIdLogin()));

                                DatabasePro.getInstance(Setting.this).bankDAO().updateMonney(Double.parseDouble(tv_monneyBank.getText().toString()),date_start,date_end,Integer.parseInt(dataLocalLoginManager.getIdLogin()));
                                dataLocalLoginManager.setIdBank(getIdCurrentBank());
                                dialog.cancel();
                            }
                        }
                    }
                });
                b.show();

            }
        });
    }
    public boolean checkDateUpdate(String date_start, String date_end,int idU){
        List<Table_Bank> list = DatabasePro.getInstance(Setting.this).bankDAO().checkBankDate(date_start,date_end,idU);
        return list != null && !list.isEmpty();
    }
    private int getIdCurrentBank(){
        List<Table_Bank> list = DatabasePro.getInstance(Setting.this).bankDAO().getIDCurrentState(Integer.parseInt(dataLocalLoginManager.getIdLogin()));
        return list.get(0).getId_u_bank();
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

}