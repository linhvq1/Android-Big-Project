package com.example.bigproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bigproject.BroadCastCloseBts.BroadcastCloseListener;
import com.example.bigproject.BroadCastCloseBts.BroadcastRecieverCollapseSh;
import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class add_event extends FragmentActivity implements BroadcastCloseListener {
private DatePicker datePicker;
private TimePicker timePicker;
private Calendar calendar;
TextView tv_Date,tv_Time;
private EditText ed_detail;
private ImageButton imgb;
private AssetManager assetManager;
private int year,month,day;
private LinearLayout layout_keyboard;
private BottomSheetBehavior bts_Keyboard;
// declare button calculator
private int[] numericButton = {R.id.bt0,R.id.bt1,R.id.bt2,R.id.bt3,R.id.bt4,R.id.bt5,R.id.bt6,R.id.bt7,R.id.bt8,R.id.bt9};
private int[] oparetorButton = {R.id.btplus,R.id.btsub,R.id.btmul,R.id.btdiv};
private TextView txtFomula,txtResult;
private boolean lastNumeric;
private boolean stateError;
private boolean lastDot;
private Table_Category catc;
private ExtendedFloatingActionButton BtFloating_add;
private boolean cay = false;
private boolean signalEdit = false;
private Table_Transaction_Daily tranE;
private double monneyCurrent = 0;
    // broadcast && bottom sheet close
private BroadcastRecieverCollapseSh BrCloseBst;
private String strBroadCast;
private static add_event ins;
private MyBottomSheet bottomSheet;

    @Override
    protected void onStart() {
        super.onStart();
        bottomSheet = new MyBottomSheet(add_event.this, add_event.this);
        bottomSheet.setContentView(R.layout.bts_ch_categories_add_event);
        bottomSheet.show();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
       Log.e("log","onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("log","onPause");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("log","onRestart");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BrCloseBst);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ins = this;
        BrCloseBst = new BroadcastRecieverCollapseSh(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastRecieverCollapseSh.ACTION_CLOSE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(BrCloseBst,intentFilter);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color_add_event));
        }

        assetManager = getAssets();
        catc = new Table_Category(null,null,null);
        ed_detail = (EditText) findViewById(R.id.ed_detail);
        ImageButton bt_back = findViewById(R.id.bt_back_add_event);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //calculator
        this.txtFomula = findViewById(R.id.tv_monney_caculator);
        if(txtFomula.getText().toString().isEmpty()) txtFomula.setText("0");
        else txtFomula.setText("");
        this.txtResult = findViewById(R.id.tv_monney_result);
        setNumericOnclickListener();
        setOperatorOnclickListener();
        //
        imgb = findViewById(R.id.IB_add_event);
        imgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet = new MyBottomSheet(add_event.this, add_event.this);
                bottomSheet.setContentView(R.layout.bts_ch_categories_add_event);
                bottomSheet.show();

            }
        });

        tv_Date = (TextView) findViewById(R.id.tv_Date_picker);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year,month+1,day);
        RelativeLayout rl_date = findViewById(R.id.rl_date_picker);
        rl_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(add_event.this,myDateListener,year,month,day).show();
            }
        });
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        tv_Time = (TextView)findViewById(R.id.tv_time_picker);
        showTime(hour,min);
        RelativeLayout rl_time = findViewById(R.id.rl_time_picker);
        rl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePickerDialog = new TimePickerDialog(add_event.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        showTime(hourOfDay,minute);
                    }
                },hour,min,true);
                mTimePickerDialog.setTitle("Select Time");
                mTimePickerDialog.show();
            }
        });

        BtFloating_add = (ExtendedFloatingActionButton) findViewById(R.id.fab_add_event);
        layout_keyboard = findViewById(R.id.layout_bottomSheet_keyboard);
        bts_Keyboard = BottomSheetBehavior.from(layout_keyboard);
        BtFloating_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(bts_Keyboard.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bts_Keyboard.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bts_Keyboard.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                if(cay){
                    if(TextUtils.isEmpty(dataLocalLoginManager.getCatgory().getImg_cat())){
                        return;}
                    //dataLocalLoginManager.getIdLogin();
                    //checkCategory dataLocalLoginManager.getIdBank()
                    if(!signalEdit){
                        if(dataLocalLoginManager.getIdBank() != 0){
                            Table_Transaction_Daily tran = new Table_Transaction_Daily(Integer.parseInt(dataLocalLoginManager.getIdLogin()),dataLocalLoginManager.getCatgory().getId_cat(),dataLocalLoginManager.getIdBank(),tv_Date.getText().toString(),
                                    tv_Time.getText().toString(),Double.parseDouble(txtResult.getText().toString()),ed_detail.getText().toString());
                            DatabasePro.getInstance(add_event.this).tranDAO().InsertTransactionDaily(tran);
                            Toast.makeText(add_event.this, "Add transaction Successfully",Toast.LENGTH_LONG).show();
                            // update monney bank
                            updateMoneyBankADD(dataLocalLoginManager.getIdBank());
                            //
                            finish();
                        }else{
                            // neu chua ton tai 1 so tien cu the nao thi dua nguoi dung den setting
                            startActivity(new Intent(add_event.this,Setting.class));
                        }
                    }else{
                        if (tranE == null) return;
                        tranE.setDate_tran_daily(tv_Date.getText().toString());
                        tranE.setDetail_tran(ed_detail.getText().toString());
                        tranE.setTime_tran_daily(tv_Time.getText().toString());
                        tranE.setId_cat(dataLocalLoginManager.getCatgory().getId_cat());
                        tranE.setMonney_tran_daily(Double.parseDouble(txtResult.getText().toString()));
                        DatabasePro.getInstance(add_event.this).tranDAO().UpdateTransactionDaily(tranE);
                        Toast.makeText(add_event.this, "Update Successfully",Toast.LENGTH_LONG).show();
                        // update monney bank
                        //updateMoneyBank(tranE.getId_u_bank());
                        updateMoneyBankEDIT(tranE.getId_u_bank());
                        //
                        Intent intentResult = new Intent();
                        intentResult.putExtra("idTran",String.valueOf(tranE.getId_tran()));
                        setResult(RESULT_OK,intentResult);
                        finish();
                    }
                }
            }
        });
        /// test
        //Table_Transaction_Daily tranE =(Table_Transaction_Daily) getIntent().getExtras().get("edit_tran");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null ) {
            tranE =(Table_Transaction_Daily) bundle.get("edit_tran");
            tv_Date.setText(tranE.getDate_tran_daily());
            tv_Time.setText(tranE.getTime_tran_daily());
            ed_detail.setText(tranE.getDetail_tran());

            List<Table_Category> listCatE = new ArrayList<>();
            listCatE = DatabasePro.getInstance(this).catDAO().getListCatFromId(tranE.getId_cat());

            dataLocalLoginManager.setCategory(listCatE.get(0));
            monneyCurrent = tranE.getMonney_tran_daily();
            txtResult.setText(String.valueOf(tranE.getMonney_tran_daily()));
            txtFomula.setText(String.valueOf(tranE.getMonney_tran_daily()));
            BtFloating_add.setIconResource(R.drawable.round_done_outline_white_36);
            cay = true;
            signalEdit = true;
        } else {
            Toast.makeText(this, "error" , Toast.LENGTH_SHORT).show();
        }
        ///

    }
    public void updateMoneyBankADD(int idBank){
        List<Table_Bank> list = DatabasePro.getInstance(this).bankDAO().getValuesfromIDBank(idBank);

            if (list != null && !list.isEmpty()) {
                //list.get(0).getCurrent_monney();
                Table_Bank bank = list.get(0);
                if (dataLocalLoginManager.getCatgory().getType_cat().equals("Expenses"))
                    bank.setCurrent_monney(bank.getCurrent_monney() - Math.abs(Double.parseDouble(txtResult.getText().toString())));
                if (dataLocalLoginManager.getCatgory().getType_cat().equals("Income"))
                    bank.setCurrent_monney(bank.getCurrent_monney() + Math.abs(Double.parseDouble(txtResult.getText().toString())));
                Toast.makeText(add_event.this, "Update Successfully" + bank.getCurrent_monney(), Toast.LENGTH_LONG).show();

                DatabasePro.getInstance(this).bankDAO().updateCurrentBank(bank);
                Toast.makeText(add_event.this, "Update Successfully",Toast.LENGTH_LONG).show();
            }

    }
    public void updateMoneyBankEDIT(int idBank){
        List<Table_Bank> list = DatabasePro.getInstance(this).bankDAO().getValuesfromIDBank(idBank);

        if (monneyCurrent != Double.parseDouble(txtResult.getText().toString())) {
            Table_Bank bank = list.get(0);
            if (dataLocalLoginManager.getCatgory().getType_cat().equals("Expenses"))
                bank.setCurrent_monney(bank.getCurrent_monney() + monneyCurrent - Math.abs(Double.parseDouble(txtResult.getText().toString())));
            if (dataLocalLoginManager.getCatgory().getType_cat().equals("Income"))
                bank.setCurrent_monney(bank.getCurrent_monney() - monneyCurrent + Math.abs(Double.parseDouble(txtResult.getText().toString())));
            Toast.makeText(add_event.this, "Update Successfully" + bank.getCurrent_monney(), Toast.LENGTH_SHORT).show();

            DatabasePro.getInstance(this).bankDAO().updateCurrentBank(bank);
            Toast.makeText(add_event.this, "Update Successfully",Toast.LENGTH_SHORT).show();
        } else {
            return;
        }
    }
    ///////////////
    // button
    private void  setNumericOnclickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if(stateError){
                    txtFomula.setText(button.getText());
                    stateError = false;
                }else{
                    txtFomula.append(button.getText());
                }
                lastNumeric = true;
            }
        };
        for(int i : numericButton){
            findViewById(i).setOnClickListener(listener);
        }
    }
    private void setOperatorOnclickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError) {
                    Button button = (Button) v;

                    if(button.getText().equals("÷")){txtFomula.append("/");}
                    else if(button.getText().equals("×")){txtFomula.append("*");}
                    else txtFomula.append(button.getText());

                    lastNumeric = false;
                    lastDot = false;    // Reset the DOT flag
                }
            }
        };
        for(int i : oparetorButton){
            findViewById(i).setOnClickListener(listener);
        }
        findViewById(R.id.btdot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot) {
                    txtFomula.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });
        findViewById(R.id.btdel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtFomula.setText("");  // Clear the screen
                if(!txtFomula.getText().toString().isEmpty()){
                    String s=txtFomula.getText().toString();
                    s=s.substring(0,s.length()-1);
                    txtFomula.setText(s);
                    BtFloating_add.setIconResource(R.drawable.round_price_change_white_36);
                    cay = false;
                    // Reset all the states and flags
                    lastNumeric = false;
                    stateError = false;
                    lastDot = false;
                    txtResult.setText("0");
                }
            }
        });
        findViewById(R.id.btequal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }
    //Double.toString(result)
//System.out.println(format.format(price));
    private void onEqual() {
        if(lastNumeric && !stateError){
            DecimalFormat format = new DecimalFormat("0.#");
            String txt = txtFomula.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try{
                double result = expression.evaluate();
                txtFomula.setText(format.format(result));
                txtResult.setText(format.format(result));
                BtFloating_add.setIconResource(R.drawable.round_done_outline_white_36);
                cay = true;
                lastDot = true;
            }catch (ArithmeticException ex){
                txtFomula.setText("error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }
    /////////////////
    // time picker
    public void setTime(View view){
        int hour = timePicker.getHour();
        int min = timePicker.getMinute();
        showTime(hour,min);
    }

    private void showTime(int hour, int min) {
        tv_Time.setText(new StringBuilder().append(hour).append(":").append(min));
    }

    ////////////////////////////
    // date picker
    @SuppressWarnings("deprecation")
    public void setDate(View view){
        showDialog(999);
        Toast.makeText(getApplicationContext(),"ca",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 999){
            return new DatePickerDialog(this,myDateListener,year,month,day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            showDate(year,month+1,dayOfMonth);
        }
    };

    private void showDate(int year, int i, int dayOfMonth) {
        String[] arrmonth = {"null","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String vmonth="";
        for(int index = 0;index<arrmonth.length;index++){
            if(i == index){
                vmonth = arrmonth[index] ;
                break;
            }
        }
        tv_Date.setText(new StringBuilder().append(vmonth).append(" ").append(dayOfMonth).append(",").append(year));
    }

    @Override
    public void doSomething(String txt) {
        strBroadCast = txt;
        if(!strBroadCast.equals("")) {
            Log.i("broad", strBroadCast);
            //MyBottomSheet bottomSheet = new MyBottomSheet(add_event.this, add_event.this);
            bottomSheet.setContentView(R.layout.bts_ch_categories_add_event);
            if (bottomSheet.isShowing()){
                bottomSheet.cancel();
            }
        }
    }

    /////////////////////////////////////////////
    // bottomsheet
 class MyBottomSheet extends BottomSheetDialog{
        private final FragmentActivity fa;
        public MyBottomSheet(Context context, FragmentActivity fa){
            super(context);
            this.fa = fa;
        }


    @Override
    public void cancel() {
        super.cancel();
        Log.e("log","cancel");
        if(null != dataLocalLoginManager.getCatgory()){
            try{
                InputStream is = assetManager.open("img/"+dataLocalLoginManager.getCatgory().getImg_cat());
                Bitmap b = BitmapFactory.decodeStream(is);
                imgb.setImageBitmap(b);

            }catch (Exception e){
                Log.e("CatAdapter",e.getMessage());
            }
        }
    }

    @Override
        protected void onStart() {
            super.onStart();
//            getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        @Override
        public void setContentView(int layoutResId) {
            super.setContentView(layoutResId);
            findViewById(R.id.root).getLayoutParams().height = 1000;
            TabLayout tabLayout = findViewById(R.id.tab_layout_add_categories);
            ViewPager2 mviewpager = findViewById(R.id.vp_add_event_categories);
            viewPager_Adapter_add_event_categories vp = new viewPager_Adapter_add_event_categories(fa);
            mviewpager.setAdapter(vp);
            new TabLayoutMediator(tabLayout, mviewpager, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    switch (position){
                        case 0:
                            tab.setText("Expenses");
                            break;
                        case 1:
                            tab.setText("Income");
                            break;
                    }
                }
            }).attach();
            ImageButton lol = (ImageButton) findViewById(R.id.chuyen);
            lol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(add_event.this, MainActivity.class);
                    Bundle bd = new Bundle();
                    bd.putString("edit_cat","1");
                    i.putExtras(bd);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
            });
        }
    }
    public static add_event getInstance(){
        return ins;
    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}


