package sandy.equeue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "MyLogService";

    private EditText emailReg,passReg,phnoReg,nameReg,passRegCon;
    private static final String REGISTER_URL = "http://192.168.43.139/registeruser.php";
    public class myPref
    {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("mypre", Context.MODE_PRIVATE);

        String value1=(sharedPref.getString("token",""));

    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };
    void checkFieldsForEmptyValues(){
        myPref mp=new myPref();

        Button finalReg =(Button)findViewById(R.id.finalReg);;

        String s1 = emailReg.getText().toString();
        String s2 = passReg.getText().toString();
        String s3 = phnoReg.getText().toString();
        String s4 = nameReg.getText().toString();
        String s5 = passRegCon.getText().toString();
        String s6= mp.value1;

        if(s1.equals("")|| s2.equals("")||s3.equals("")||s4.equals("")||s5.equals("")||!s2.equals(s5)){
            finalReg.setEnabled(false);
        }
        else {

                finalReg.setEnabled(true);
                finalReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registerUser();
                       // Toast.makeText(getBaseContext(), "You Have Successfully Registered!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                });

        }
    }
    private void registerUser() {
        myPref mp=new myPref();
        String name1 = nameReg.getText().toString().trim().toLowerCase();
        String username1 = emailReg.getText().toString().trim().toLowerCase();
        String password1 = passReg.getText().toString().trim().toLowerCase();
        String phn1 = phnoReg.getText().toString().trim().toLowerCase();
        String token1=mp.value1;
        register(name1,username1,password1,phn1,token1);
    }

    private void register(String name1, String username1, String password1, String phn1, final String token1) {
    //    Toast.makeText(getBaseContext(), "You Are Being Registered!", Toast.LENGTH_SHORT).show();
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
              //  Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name1",params[0]);
                data.put("username1",params[1]);
                data.put("password1",params[2]);
                data.put("phn1",params[3]);
                data.put("token1",params[4]);
                Log.d(TAG, "Working Token : "+token1);

                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name1,username1,password1,phn1,token1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         emailReg=(EditText)findViewById(R.id.emailReg);
         passReg=(EditText)findViewById(R.id.passReg);
         phnoReg=(EditText)findViewById(R.id.phnoReg);
         nameReg=(EditText)findViewById(R.id.nameReg);
         passRegCon=(EditText)findViewById(R.id.passRegCon);

        emailReg.addTextChangedListener(mTextWatcher);
        passRegCon.addTextChangedListener(mTextWatcher);
        passReg.addTextChangedListener(mTextWatcher);
        nameReg.addTextChangedListener(mTextWatcher);
        phnoReg.addTextChangedListener(mTextWatcher);
        checkFieldsForEmptyValues();
    }

}
