package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHelper(this);
        mTextUsername=(EditText)findViewById(R.id.edittext_username);
        mTextPassword=(EditText)findViewById(R.id.edittext_password);
        mButtonLogin=(Button)findViewById(R.id.button_login);
        mTextViewRegister=(TextView)findViewById(R.id.textview_register);

        ((TextView) mTextViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent;
                register_intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(register_intent);

            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String username=mTextUsername.getText().toString().trim();
                String password=mTextPassword.getText().toString().trim();
                Boolean res=db.checkUser(username,password);
                if(res==true){
//                   String username1=db.getUsername();
//                    Toast.makeText(MainActivity.this,username1,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,Subreddit.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"Incorrect username or password",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
