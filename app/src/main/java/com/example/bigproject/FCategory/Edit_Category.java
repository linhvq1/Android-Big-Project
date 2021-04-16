package com.example.bigproject.FCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

import com.example.bigproject.Add_Category_Activity;
import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.GridAdapter;
import com.example.bigproject.R;
import com.example.bigproject.Table_Category;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Edit_Category extends AppCompatActivity {

    private AssetManager assetManager;
    private final static String TAG = "Edit_category";
    ArrayList<String> listPath;
    ArrayList<Bitmap> bitmapArrayList;
    private ImageView imageView;
    GridAdapter customAdapter;
    private String imgDone;
    ImageButton back,done;
    EditText tv_name_cat;
    private Table_Category category;
    RadioButton r_Expenses,r_Income;
    private String typeCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__category);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        assetManager = getAssets();
        bitmapArrayList = new ArrayList<Bitmap>();
        listPath = new ArrayList<String>();

        TextInputLayout text1 = (TextInputLayout) findViewById(R.id.input_layout_edit);

        GridView gridView = (GridView) findViewById(R.id.gridvlogo_edit);
        back = (ImageButton) findViewById(R.id.bt_back_edit) ;
        done = (ImageButton) findViewById(R.id.bt_done_edit);
        tv_name_cat = (EditText) findViewById(R.id.ed_namecat_edit);
        tv_name_cat.requestFocus();
        imageView = (ImageView) findViewById(R.id.img_c_edit);
        category =(Table_Category) getIntent().getExtras().get("data_item");
        // radio Typecat
        r_Expenses = (RadioButton) findViewById(R.id.radio_a_edit);
        r_Income = (RadioButton)findViewById(R.id.radio_b_edit);

        r_Income.setOnCheckedChangeListener(listenerRadio);
        r_Expenses.setOnCheckedChangeListener(listenerRadio);

        Log.i("data_lol",category.getImg_cat());
        // NEU nhu du lieu chuyen sang ma ko null thi tien hanh set du lieu cho form
        if(category != null){
            tv_name_cat.setText(category.getName_cat());
            try{
                InputStream is = assetManager.open("img/"+category.getImg_cat());
                Bitmap b = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(b);
            }catch (Exception e){
                Log.e("CatAdapter",e.getMessage());
            }
            if(category.getType_cat().equals("Expenses")) r_Expenses.setChecked(true);
            else r_Income.setChecked(true);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            lisAllImage();
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }

        try{
            customAdapter = new GridAdapter(Edit_Category.this, bitmapArrayList);
            gridView.setAdapter(customAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = bitmapArrayList.get(position).toString();
                Toast.makeText(view.getContext(),listPath.get(position),Toast.LENGTH_LONG).show();

                Toast.makeText(view.getContext(),pos,Toast.LENGTH_LONG).show();
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
                //Category catadd = new Category(tv_name_cat.getText().toString(),imgDone);
                category.setName_cat(tv_name_cat.getText().toString());

                if(!TextUtils.isEmpty(imgDone)) category.setImg_cat(imgDone);

                category.setType_cat(typeCat);

                DatabasePro.getInstance(v.getContext()).catDAO().updateCat(category);
                Toast.makeText(v.getContext(),"Update category successfully",Toast.LENGTH_SHORT).show();

                Intent intentResult = new Intent();
                setResult(Activity.RESULT_OK,intentResult);
                finish();
            }
        });
    }
    CompoundButton.OnCheckedChangeListener listenerRadio = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Toast.makeText(Edit_Category.this,buttonView.getText(),Toast.LENGTH_SHORT).show();
        }
    };
    private boolean isCatExit(Table_Category cat){
        List<Table_Category> list = DatabasePro.getInstance(this).catDAO().checkCategory(cat.getName_cat());
        return list != null && !list.isEmpty();
    }
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