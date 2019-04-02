package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPost extends AppCompatActivity {

    String post_id;
    String current_subreddit_name;
    String username;
    TextView mTextusername;
    Intent intent;
    DatabaseHelper db;
    TextView mSubredditname;
    TextView mTextDisplayPost;
    TextView mTextDisplayComment;
    EditText mTextCreateComment;
    Button mButtonnComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);
        db=new DatabaseHelper(this);
        intent=getIntent();
        post_id=intent.getStringExtra("post_id");
        current_subreddit_name=intent.getStringExtra("subreddit");

        //CODE FOR DISPALYING USERNAME
        username=db.getUsername();
        mTextusername=(TextView)findViewById(R.id.profile_username);
        mTextusername.setText(username);
        //CODE FOR DISPLAYING SUBREDDIT_NAME
        mSubredditname=(TextView)findViewById(R.id.set_subreddit_name);
        mSubredditname.setText("r/"+current_subreddit_name);


        //CODE FOR DISPLAYIG POST CONTENT
        String Post_content=db.getPost(post_id);
        mTextDisplayPost=(TextView)findViewById(R.id.display_post_showpost);
        mTextDisplayPost.setText(Post_content);



        //CODE FOR DISPLAYIG ALL COMMENT
        mTextDisplayComment=(TextView)findViewById(R.id.display_comment);
        String[] comments=db.getComment(post_id);
        if(comments!=null){
            StringBuilder builder =new StringBuilder();
            for(String cmnt:comments){
                builder.append(cmnt).append("\n");
            }
            mTextDisplayComment.setText(builder);
        }

        //code for setting reply button
        mTextCreateComment=(EditText)findViewById(R.id.create_comment);
        mButtonnComment=(Button)findViewById(R.id.comment_button);
        mButtonnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment=mTextCreateComment.getText().toString().trim();
                long res=db.addComment(username,comment,post_id);
                if(res>=0){
                    mTextCreateComment.setText("");
                    Toast.makeText(ShowPost.this,"commented successfully!",Toast.LENGTH_SHORT).show();
                    //ADD CODE FOR APPENDING THIS COMMENT ON THE WALL
                    mTextDisplayComment.append(comment);
                    mTextDisplayComment.append("\n");

                }
                else{
                    Toast.makeText(ShowPost.this,"could not comment!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
