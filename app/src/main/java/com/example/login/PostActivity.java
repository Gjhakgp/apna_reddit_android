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

public class PostActivity extends AppCompatActivity {

    TextView mSetSubredditname;
    TextView mTextusername;
    EditText mTextPost;
    Button mButtonSubmit;
//    TextView mTextDisplayPost;
    LinearLayout layout;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
//    TextView mTextDisplayComment;
//    Button mNextButton;
//    EditText mTextCreateComment;
//    Button mButtonnComment;
//    Button mButtonSubreddits;
//    LinearLayout mReplySection;
    DatabaseHelper db;
    String username;

    String current_post_id;
    String current_subreddit_name;
    String[][]  post;
    int post_index=0;
    int btn_id=0;
    Intent intent;
    public class MyClickListener implements View.OnClickListener{
        int index;
        public MyClickListener(int index){
            this.index=index;
        }
        @Override
        public void onClick(View v){
            intent=new Intent(PostActivity.this,ShowPost.class);
            intent.putExtra("post_id",post[index][0]);
            intent.putExtra("subreddit",current_subreddit_name);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        db=new DatabaseHelper(this);

        //CODE FOR GETTING THE USERNAME AND SETTING THE NME ON THE WALL
        username=db.getUsername();
        mTextusername=(TextView)findViewById(R.id.profile_username);
        mTextusername.setText(username);
        final Intent intent;
        intent=getIntent();
        current_subreddit_name=intent.getStringExtra("subreddit_name");

        //CODE FOR SETTING SUBREDDIT NAME ON TOP OF THE WALL
        mSetSubredditname=(TextView) findViewById(R.id.set_subreddit_name);
        mSetSubredditname.setText("r/"+current_subreddit_name);


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


//        //CODE FOR CREATING A COMMENT ON A POST
//        mTextDisplayComment=(TextView)findViewById(R.id.display_comment);
////        mTextCreateComment=(EditText)findViewById(R.id.create_comment);
//        mButtonnComment=(Button)findViewById(R.id.comment_button);
//        mButtonnComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String comment=mTextCreateComment.getText().toString().trim();
////                long res=db.addComment(username,comment,current_post_id);
//                if(res>=0){
////                    mTextCreateComment.setText("");
//                    Toast.makeText(PostActivity.this,"commented successfully!",Toast.LENGTH_SHORT).show();
//                    //ADD CODE FOR APPENDING THIS COMMENT ON THE WALL
//                    mTextDisplayComment.append(comment);
//                    mTextDisplayComment.append("\n");
//
//                }
//                else{
//                    Toast.makeText(PostActivity.this,"could not comment!",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        //CODE FOR GETTING ALL POST OF A SUBREDDIT
//        mTextDisplayPost=(TextView)findViewById(R.id.display_post);
//        mReplySection=(LinearLayout)findViewById(R.id.reply_section);
        layout=(LinearLayout)findViewById(R.id.display_post);
        post=db.getAllPost(current_subreddit_name);
        if(post!=null){
            int num_of_post=post.length;
            if(num_of_post==0){
                //ADD CODE FOR REMOVING REPLY BUTTON
//                mReplySection.setVisibility(View.GONE);

            }
            else{
//                mReplySection.setVisibility(View.VISIBLE);
//                mTextDisplayPost.setText(post[post_index][1]);
                int i;
                Button[] b=new Button[num_of_post];
                for(i=0;i<num_of_post;i++){
                    btn_id++;
                    Button btn=new Button(PostActivity.this);
                    btn.setId(btn_id);
                    btn.setText(post[i][1]);
                    layout.addView(btn,params);
                    b[i]=(Button)findViewById(btn_id);
                    b[i].setOnClickListener(new MyClickListener(i));
                }
//                current_post_id=post[post_index][0];
//                String[] comments=db.getComment(current_post_id);
//                if(comments!=null){
//                    StringBuilder builder =new StringBuilder();
//                    for(String cmnt:comments){
//                        builder.append(cmnt).append("\n");
//                    }
//                    mTextDisplayComment.setText(builder);
//                }
            }
        }
        else{
//            mReplySection.setVisibility(View.GONE);
        }

//        CODE FOR GETTING NEXT POST AFTER PRESSING NEXT
//        mNextButton=(Button)findViewById(R.id.next_button);
//        mNextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(post_index>=num_of_post-1){
//                    Toast.makeText(PostActivity.this,"No more post",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    post_index=post_index+1;
////                    mTextDisplayPost.setText(post[post_index][1]);
//                    current_post_id=post[post_index][0];
//                    String[] comments=db.getComment(current_post_id);
//                    if(comments!=null) {
//                        StringBuilder builder = new StringBuilder();
//                        for (String cmnt : comments) {
//                            builder.append(cmnt).append("\n");
//                        }
//                        mTextDisplayComment.setText(builder);
//                    }
//
//                }
//            }
//        });

        //CODE FOR REDIRECTING TO SUBREDDIT PAGE
//        mButtonSubreddits=(Button)findViewById(R.id.go_to_subreddit);
//        mButtonSubreddits.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1=new Intent(PostActivity.this,Subreddit.class);
//                startActivity(intent1);
//            }
//        });

    }


}
