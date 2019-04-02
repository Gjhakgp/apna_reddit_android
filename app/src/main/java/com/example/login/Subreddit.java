package com.example.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Subreddit extends AppCompatActivity {

    EditText mTextCreateSubreddit;
    Button mButtonCreateSubreddit;
    Button mButtonClickSubreddit;
    TextView mTextusername;
    DatabaseHelper db;
    String username;
    String[] all_subreddit;
    LinearLayout layout;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    Intent intent;
    int i;
    int btn_id=0;
    int new_subreddit_made=0;
    Button[] b1=new Button[20];

    private class MyClickListener implements View.OnClickListener{
        int index;
        public MyClickListener(int index){
            this.index=index;
        }
        @Override
        public void onClick(View v){
            intent=new Intent(Subreddit.this,PostActivity.class);
            intent.putExtra("subreddit_name",all_subreddit[index]);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subreddit);

        db=new DatabaseHelper(this);

        //CODE FOR GETTING THE USERNAME AND SETTING THE NME ON THE WALL
        username=db.getUsername();
        mTextusername=(TextView)findViewById(R.id.profile_username);
        mTextusername.setText(username);

        //CODE FOR CREATING A SUBREDDIT
        mTextCreateSubreddit=(EditText)findViewById(R.id.create_subreddit);
        mButtonCreateSubreddit=(Button)findViewById(R.id.create_subreddit_button);
        layout=(LinearLayout)findViewById(R.id.subreddit_list);
        mButtonCreateSubreddit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final String subreddit_name=mTextCreateSubreddit.getText().toString().trim();
                long res=db.addSubreddit(subreddit_name,username);
                if(res>=0){
                    mTextCreateSubreddit.setText("");
                    //ADD CODE FOR APPENDING THIS SUBREDDIT ON THE WALL
                    Button btn=new Button(Subreddit.this);
                    btn_id++;
                    btn.setId(btn_id);
                    btn.setText("r/"+subreddit_name);
                    layout.addView(btn,params);
                    b1[new_subreddit_made]=(Button)findViewById(btn_id);
                    b1[new_subreddit_made].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //WRITE CODE FOR PASSING SUBREDDIT NAME TO POST ACTIVITY
                            intent=new Intent(Subreddit.this,PostActivity.class);
                            intent.putExtra("subreddit_name",subreddit_name);
                            startActivity(intent);
                        }
                    });
                    new_subreddit_made++;

                }
                else{
                    Toast.makeText(Subreddit.this,"Subreddit Already Exist!",Toast.LENGTH_SHORT).show();
                }
            }

        });

        //CODE FOR RETRIEVING ALL SUBREDDIT FROM DATABASE AND DISPLAYING IT
        all_subreddit=db.getSubreddit();
        Button[] b=new Button[all_subreddit.length];
        if(all_subreddit!=null){
            int total_subreddit=all_subreddit.length;
            for(i=0;i<total_subreddit;i++){
                btn_id++;
                Button btn=new Button(this);
                btn.setId(btn_id);
                btn.setText("r/"+all_subreddit[i]);
                layout.addView(btn,params);
                b[i]=(Button)findViewById(btn_id);
                b[i].setOnClickListener(new MyClickListener(i));
//                b[i].setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //WRITE CODE FOR PASSING SUBREDDIT NAME TO POST ACTIVITY
//                        intent=new Intent(Subreddit.this,PostActivity.class);
//                        intent.putExtra("subreddit_name",all_subreddit[i-1]);
//                        startActivity(intent);
//                    }
//                });
            }
//
        }

    }
}
