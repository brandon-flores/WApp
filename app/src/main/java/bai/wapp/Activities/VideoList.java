package bai.wapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import bai.wapp.Helper.DatabaseHelper;
import bai.wapp.Models.Category;
import bai.wapp.Models.User;
import bai.wapp.Models.Video;
import bai.wapp.R;

/**
 * Created by next on 4/16/2017.
 */

public class VideoList extends AppCompatActivity{


    private static Toolbar toolbar;
    private static ListView listView;
    private static EditText vid_name;
    private static Button btnAdd;
    private static ArrayAdapter<String> mAdapter;
    private static ArrayList<String> listVideos; //ilisi og list sa videos from sql;
    int i = 0;
    User user;
    Category category;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list);
        //initObjects();
        db = DatabaseHelper.getInstance(this);
        String s = getIntent().getStringExtra("from");
        category = db.getCategory(s);
        s = getIntent().getStringExtra("user");
        user = db.getUser(s);
        listVideos=new ArrayList<String>();
        //listVideos.add("Add");
        initViews();
    }

    void initViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView= (ListView)   findViewById(R.id.vid_list);
        btnAdd = (Button) findViewById(R.id.addVid);
        vid_name = (EditText) findViewById(R.id.vname);



        mAdapter = new ArrayAdapter<String>(VideoList.this,
                android.R.layout.simple_list_item_1,
                listVideos);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//        });
        listView.setAdapter(mAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vidname = vid_name.getText().toString();
                //Video video = db.getVideo(vidname);
                Toast.makeText(VideoList.this,
                       user.getName() + " " + category.getName(), Toast.LENGTH_SHORT).show();

                if(db.checkVideo(vidname)){
                    Video video = db.getVideo(vidname);
                    if(db.userHasVideo(video.getId(), user.getId())){
                        Toast.makeText(VideoList.this,
                                "You already added this video!", Toast.LENGTH_SHORT).show();
                    }else{
                        db.addUserVideo(user.getId(),category.getId(),video.getId());
                        listVideos.add(vidname);
                        Log.wtf("ALERT", "nisulod ko diri");
                        // next thing you have to do is check if your adapter has changed
                        mAdapter.notifyDataSetChanged();
                        vid_name.setText("");
                    }
                }else{
                    Video video = new Video(db.getAllVideos().size(), vidname);
                    db.addVideo(video);
                    db.addUserVideo(user.getId(), category.getId(), video.getId());
                    // this line adds the data of your EditText and puts in your array
                    listVideos.add(vidname);

                    // next thing you have to do is check if your adapter has changed
                    mAdapter.notifyDataSetChanged();
                    vid_name.setText("");
                }





            }
        });


    }

//    @Override
//    public void onClick(View v) {
//        videoList.append("\n" + videoname.getText().toString());
//    }

}
