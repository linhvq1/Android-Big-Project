package com.example.bigproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.FCategory.Edit_Category;
import com.example.bigproject.FCategory.epensesTabFragment;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;

import java.io.InputStream;
import java.util.List;

public class Detail_MonneyUsed extends FragmentActivity {
//    @Nullable
//    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_edit_item,container,false);
//        return view;
//
//
//    }
    private AssetManager assetManager;
    private ImageButton bt_back,bt_delete;
    private LinearLayout bt_update;
    private ImageView Img_Cat;
    private TextView tv_DetailCat,tv_MonneyDetail,tv_TypeCat,tv_DateTime,tv_Note;
    private user_Item_data mU;
    private List<Table_Transaction_Daily> tranCapture;
    Table_Transaction_Daily tranM;
    private double monneyCurrent = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_item);
        assetManager = getAssets();
        mU =(user_Item_data) getIntent().getExtras().get("object item");

        if(mU != null){
            InitParam(mU);
        }
        tranCapture = DatabasePro.getInstance(this).tranDAO().getTransactionFromIdTran(mU.getIdTransaction());
        tranM = tranCapture.get(0);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDelete(tranM);
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Detail_MonneyUsed.this, add_event.class);
                Bundle bd = new Bundle();
                bd.putParcelable("edit_tran",tranM);
                i.putExtras(bd);
                startActivityForResult(i,12);
            }
        });
    }
    private void clickDelete(Table_Transaction_Daily tranM) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete category")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabasePro.getInstance(Detail_MonneyUsed.this).tranDAO().DeleteTransactionDaily(tranM);
                        Toast.makeText(Detail_MonneyUsed.this,"Delete Successfully",Toast.LENGTH_SHORT).show();

                        updateMoneyBankEDIT(tranM.getId_u_bank());
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
    public void updateMoneyBankEDIT(int idBank){
        List<Table_Bank> list = DatabasePro.getInstance(this).bankDAO().getValuesfromIDBank(idBank);

            Table_Bank bank = list.get(0);
            if (tv_TypeCat.getText().toString().equals("Expenses"))
                bank.setCurrent_monney(bank.getCurrent_monney() + Math.abs(mU.getMoney_used()));
            if (tv_TypeCat.getText().toString().equals("Income"))
                bank.setCurrent_monney(bank.getCurrent_monney() - Math.abs(mU.getMoney_used()));
            Toast.makeText(this, "Update Successfully" + bank.getCurrent_monney(), Toast.LENGTH_LONG).show();

            DatabasePro.getInstance(this).bankDAO().updateCurrentBank(bank);
            Toast.makeText(this, "Update Successfully",Toast.LENGTH_LONG).show();
    }
    public void InitParam(user_Item_data u){
        bt_back =(ImageButton) findViewById(R.id.bt_back);
        bt_delete = (ImageButton) findViewById(R.id.bt_Delete);
        bt_update = (LinearLayout) findViewById(R.id.bt_Edit);
        Img_Cat = (ImageView) findViewById(R.id.imgDetail);
        tv_DetailCat = (TextView) findViewById(R.id.tv_catDetail);
        tv_MonneyDetail =(TextView) findViewById(R.id.tv_monneyDetail);
        tv_DateTime = (TextView) findViewById(R.id.dateTimeDetail);
        tv_Note = (TextView) findViewById(R.id.remarkDetail);
        tv_TypeCat = (TextView)findViewById(R.id.Type_cat);

        try{
            InputStream is = assetManager.open("img/"+u.getResource_img());
            Bitmap b = BitmapFactory.decodeStream(is);
            Img_Cat.setImageBitmap(b);

        }catch (Exception e){
            Log.e("CatAdapter",e.getMessage());
        }
        //Img_Cat.setImageResource(u.getResource_img_id());
        tv_DetailCat.setText(u.getName_categories());
        tv_DateTime.setText(u.getDate_time()+", "+u.getTimeTran());
        monneyCurrent = u.getMoney_used();
        if(u.getTypeCat().equals("Expenses")){
            tv_MonneyDetail.setText("-" + u.getMoney_used());
        }
        if(u.getTypeCat().equals("Income")){
            tv_MonneyDetail.setText("+" + u.getMoney_used());
        }
        //tv_MonneyDetail.setText(u.getMoney_used());
        tv_TypeCat.setText(u.getTypeCat());
        tv_Note.setText(u.getDiscribe());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 12 && resultCode == RESULT_OK){
            String idTran = data.getExtras().getString("idTran");

            List<user_Item_data> uzi = DatabasePro.getInstance(this).tranDAO().ReloadDataDetail(Integer.parseInt(dataLocalLoginManager.getIdLogin()),Integer.parseInt(idTran));
            mU = uzi.get(0);
            InitParam(mU);
            tranCapture = DatabasePro.getInstance(this).tranDAO().getTransactionFromIdTran(mU.getIdTransaction());
            tranM = tranCapture.get(0);
        }
    }
}
