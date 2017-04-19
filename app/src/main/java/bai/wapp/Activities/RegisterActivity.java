package bai.wapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bai.wapp.Helper.DatabaseHelper;
import bai.wapp.R;
import bai.wapp.Models.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper db;
    private static EditText name;
    private static EditText username;
    private static EditText password;
    private static EditText cpassword;
    private static Button btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initObjects();
        initViews();
    }

    void initViews(){
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.cpassword);
        btnregister = (Button)   findViewById(R.id.btnRegister);
        btnregister.setOnClickListener(this);
    }
    void initObjects(){
        db = DatabaseHelper.getInstance(this);
    }


    @Override
    public void onClick(View v) {
        String uname = username.getText().toString();
        String n = name.getText().toString();
        String pass  = password.getText().toString();
        String cpass  = cpassword.getText().toString();
        if(db.checkUser(uname)){
            Log.wtf("REGISTER REQUEST", "INVALID! ALREADY EXISTS");
        }else{
            if(pass.equals(cpass)){
                User user = new User(db.getAllUser().size(),uname,n,pass);
                Log.wtf("USER ID",user.getId()+"");
                db.addUser(user);
            }else{
                Log.wtf("REGISTER REQUEST","INVALID! PASSWORD DON'T MATCH");
            }
        }
    }
}
