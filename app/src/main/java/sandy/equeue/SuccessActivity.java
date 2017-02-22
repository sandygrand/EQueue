package sandy.equeue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.HashMap;

public class SuccessActivity extends AppCompatActivity {
    private static final String ORDER_URL = "http://192.168.43.139/orderque.php";
     String s1 = "False";
    String s2 = "False";
    String s3 = "False";
    String s4 = "False";
    String s5 = "False";
    CheckBox c1,c2,c3,c4,c5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Toast.makeText(SuccessActivity.this,"You Are SuccessFully Logged In", Toast.LENGTH_LONG).show();
         c1=(CheckBox)findViewById(R.id.checkBox1);
         c2=(CheckBox)findViewById(R.id.checkBox2);
         c3=(CheckBox)findViewById(R.id.checkBox3);
         c4=(CheckBox)findViewById(R.id.checkBox4);
         c5=(CheckBox)findViewById(R.id.checkBox5);
    }
    public void sendOrder(View v)
    {
        if(c1.isChecked())
        {
            s1="True";
        }
        if(c2.isChecked())
        {
            s2="True";
        }
        if(c3.isChecked())
        {
            s3="True";
        }
        if(c4.isChecked())
        {
            s4="True";
        }
        if(c5.isChecked())
        {
            s5="True";
        }
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("mypre", Context.MODE_PRIVATE);
        String username1=(sharedPref.getString("username1",""));
        String token1=(sharedPref.getString("token",""));
        order(username1,token1,s1,s2,s3,s4,s5);
    }
    public void order(String username1,String token1,String s1,String s2,String s3,String s4,String s5)
    {

        class userOrder extends AsyncTask<String,Void,String>
        {
            private ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SuccessActivity.this, "Please Wait",null, true, true);
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
                data.put("username1",params[0]);
                data.put("token1",params[1]);
                data.put("item1",params[2]);
                data.put("item2",params[3]);
                data.put("item3",params[4]);
                data.put("item4",params[5]);
                data.put("item5",params[6]);
                Log.d("OrderService", "Order Request Sent");

                String result = ruc.sendPostRequest(ORDER_URL,data);
                return  result;
            }
        }
        userOrder uo=new userOrder();
        uo.execute(username1,token1,s1,s2,s3,s4,s5);
    }
}
