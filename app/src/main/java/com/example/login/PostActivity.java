package com.example.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PostActivity extends AppCompatActivity {

    TextView mTextusername;
    EditText mTextPost;
    Button mButtonSubmit;
    TextView mTextDisplayPost;
    TextView mTextDisplayComment;
    Button mNextButton;
    EditText mTextCreateSubreddit;
    Button mButtonCreateSubreddit;
    EditText mTextCreateComment;
    Button mButtonnComment;

    LinearLayout mReplySection;
    DatabaseHelper db;
    String username;

    String current_post_id;
    String current_subreddit_name="PYTHON";
    String[][]  post;
    int post_index=0;
    int num_of_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        db=new DatabaseHelper(this);

        //CODE FOR GETTING THE USERNAME AND SETTING THE NME ON THE WALL
        username=db.getUsername();
        mTextusername=(TextView)findViewById(R.id.profile_username);
        mTextusername.setText(username);



        //CODE FOR CREATING A SUBREDDIT
        mTextCreateSubreddit=(EditText)findViewById(R.id.create_subreddit);
        mButtonCreateSubreddit=(Button)findViewById(R.id.create_subreddit_button);
        mButtonCreateSubreddit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String subreddit_name=mTextCreateSubreddit.getText().toString().trim();
                long res=db.addSubreddit(subreddit_name,username);
                if(res>=0){
                    mTextCreateSubreddit.setText("");
                    //ADD CODE FOR APPENDING THIS SUBREDDIT ON THE WALL
                }
                else{
                    Toast.makeText(PostActivity.this,"Subreddit Already Exist!",Toast.LENGTH_SHORT).show();
                }
            }

        });


        //CODE FOR CREATING A POST WITHIN A SUBREDDIT
        mTextPost =(EditText)findViewById(R.id.post_content);
        mButtonSubmit=(Button) findViewById(R.id.post_submit);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String post=mTextPost.getText().toString().trim();
               long res= db.addPost(post,username,current_subreddit_name);
               if(res>=0){
                   mTextPost.setText("");
               }
               else{
                   String res_str=String.valueOf(res);
                   Toast.makeText(PostActivity.this,"could not post it!",Toast.LENGTH_SHORT).show();
               }
            }
        });


        //CODE FOR CREATING A COMMENT ON A POST
        mTextDisplayComment=(TextView)findViewById(R.id.display_comment);
        mTextCreateComment=(EditText)findViewById(R.id.create_comment);
        mButtonnComment=(Button)findViewById(R.id.comment_button);
        mButtonnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment=mTextCreateComment.getText().toString().trim();
                long res=db.addComment(username,comment,current_post_id);
                if(res>=0){
                    mTextCreateComment.setText("");
                    Toast.makeText(PostActivity.this,"commented successfully!",Toast.LENGTH_SHORT).show();
                    //ADD CODE FOR APPENDING THIS COMMENT ON THE WALL
                    mTextDisplayComment.append(comment);
                    mTextDisplayComment.append("\n");

                }
                else{
                    Toast.makeText(PostActivity.this,"could not comment!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //CODE FOR GETTING ALL POST OF A SUBREDDIT
        mTextDisplayPost=(TextView)findViewById(R.id.display_post);
        mReplySection=(LinearLayout)findViewById(R.id.reply_section);
        post=db.getAllPost(current_subreddit_name);
        if(post!=null){
            num_of_post=post[0].length;
            if(num_of_post==0){
                //ADD CODE FOR REMOVING REPLY BUTTON
                mReplySection.setVisibility(View.GONE);

            }
            else{
                mReplySection.setVisibility(View.VISIBLE);
                mTextDisplayPost.setText(post[post_index][1]);
                current_post_id=post[post_index][0];
                String[] comments=db.getComment(current_post_id);
                if(comments!=null){
                    StringBuilder builder =new StringBuilder();
                    for(String cmnt:comments){
                        builder.append(cmnt).append("\n");
                    }
                    mTextDisplayComment.setText(builder);
                }
            }
        }
        else{
            mReplySection.setVisibility(View.GONE);
        }

//        CODE FOR GETTING NEXT POST AFTER PRESSING NEXT
        mNextButton=(Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(post_index>=num_of_post-1){
                    Toast.makeText(PostActivity.this,"No more post",Toast.LENGTH_SHORT).show();
                }
                else {
                    post_index=post_index+1;
                    mTextDisplayPost.setText(post[post_index][1]);
                    current_post_id=post[post_index][0];
                    String[] comments=db.getComment(current_post_id);
                    if(comments!=null) {
                        StringBuilder builder = new StringBuilder();
                        for (String cmnt : comments) {
                            builder.append(cmnt).append("\n");
                        }
                        mTextDisplayComment.setText(builder);
                    }

                }
            }
        });

    }


}
