package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextName;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    EditText mTextPhone;
    EditText mTextEmail;
    EditText mTextCity;
    EditText mTextState;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db=new DatabaseHelper(this);
        mTextUsername=(EditText)findViewById(R.id.edittext_username);
        mTextPassword=(EditText)findViewById(R.id.edittext_password);
        mTextCnfPassword=(EditText)findViewById(R.id.edittext_cnf_password);
        mButtonRegister=(Button)findViewById(R.id.button_register);
        mTextViewLogin=(TextView)findViewById(R.id.textview_login);
        mTextName=(EditText) findViewById(R.id.edittext_name);
        mTextCity=(EditText) findViewById(R.id.edittext_city);
        mTextState=(EditText) findViewById(R.id.edittext_state);
        mTextPhone=(EditText) findViewById(R.id.edittext_phone);
        mTextEmail=(EditText) findViewById(R.id.edittext_email);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(login_intent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String name=mTextName.getText().toString().trim();
                String username=mTextUsername.getText().toString().trim();
                String password=mTextPassword.getText().toString().trim();
                String cnf_pass=mTextCnfPassword.getText().toString().trim();
                String phone=mTextPhone.getText().toString().trim();
                String email=mTextEmail.getText().toString().trim();
                String city=mTextCity.getText().toString().trim();
                String state=mTextState.getText().toString().trim();
                if(password.equals(cnf_pass)==true){
                    long res=db.addUser(name,username,password,phone,email,city,state);
                    if(res>=0){
                        Intent login_intent=new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(login_intent);
                    }
                    else{
                        //PROMPT USERNAME ALREADY EXIST
                        Toast.makeText(RegisterActivity.this,"Username already exist",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //PROMPT PASSWORD DID NOT MATCH
                    Toast.makeText(RegisterActivity.this,"Passwords did not match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
