package com.example.bigproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Add_Category_Activity extends AppCompatActivity {

    private AssetManager assetManager;
    private final static String TAG = "ADD_cat";
    ArrayList<String> listPath;
    ArrayList<Bitmap> bitmapArrayList;
    private ImageView imageView;
    GridAdapter customAdapter;
    private String imgDone;
    ImageButton back,done;
    EditText tv_name_cat;
    RadioButton r_Expenses,r_Income;
    private String typeCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__category_);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        assetManager = getAssets();
        bitmapArrayList = new ArrayList<Bitmap>();
        listPath = new ArrayList<String>();

        TextInputLayout text1 = (TextInputLayout) findViewById(R.id.input_layout);

        GridView gridView = (GridView) findViewById(R.id.gridvlogo);
        back = (ImageButton) findViewById(R.id.bt_back) ;
        done = (ImageButton) findViewById(R.id.bt_done);
        tv_name_cat = (EditText) findViewById(R.id.ed_namecat);
        tv_name_cat.requestFocus();
        r_Expenses = (RadioButton) findViewById(R.id.radio_a);
        r_Income = (RadioButton)findViewById(R.id.radio_b);

        r_Income.setOnCheckedChangeListener(listenerRadio);
        r_Expenses.setOnCheckedChangeListener(listenerRadio);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView = (ImageView) findViewById(R.id.img_c);
        try {
            lisAllImage();
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }

        try{
            customAdapter = new GridAdapter(Add_Category_Activity.this, bitmapArrayList);
            gridView.setAdapter(customAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String pos = bitmapArrayList.get(position).toString();
                //Toast.makeText(view.getContext(),listPath.get(position),Toast.LENGTH_LONG).show();

                //Toast.makeText(view.getContext(),pos,Toast.LENGTH_LONG).show();
                imageView.setImageBitmap(bitmapArrayList.get(position));
                imgDone = listPath.get(position);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(tv_name_cat.getText().toString())){
                    tv_name_cat.setError("This item name cannot be empty");
                    return;
                }
                if(r_Expenses.isChecked()) typeCat = r_Expenses.getText().toString();
                if(r_Income.isChecked()) typeCat = r_Income.getText().toString();

                if(TextUtils.isEmpty(imgDone)){
                    return;
                }

                Table_Category catadd = new Table_Category(tv_name_cat.getText().toString(),imgDone,typeCat);

                if(isCatExit(catadd)){
                    Toast.makeText(v.getContext(),"Name is Trumcate",Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabasePro.getInstance(v.getContext()).catDAO().InsertCat(catadd);
                Toast.makeText(v.getContext(),"add category successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private boolean isCatExit(Table_Category cat){
        List<Table_Category> list = DatabasePro.getInstance(this).catDAO().checkCategory(cat.getName_cat());
        return list != null && !list.isEmpty();
    }
    CompoundButton.OnCheckedChangeListener listenerRadio = new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(Add_Category_Activity.this,buttonView.getText(),Toast.LENGTH_SHORT).show();
    }
    };
    public void lisAllImage(){
        Bitmap b = null;

        try{
            String[] imgPath = assetManager.list("img");
            for (int i = 0;i<imgPath.length;i++){
                InputStream is = assetManager.open("img/"+imgPath[i]);
                listPath.add(imgPath[i]);
                b = BitmapFactory.decodeStream(is);
                bitmapArrayList.add(b);
            }
        }catch (IOException e){
            Log.e(TAG,e.getMessage());
        }
    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}