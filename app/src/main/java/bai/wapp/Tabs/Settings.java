package bai.wapp.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bai.wapp.R;

/**
 * Created by next on 4/12/2017.
 */

public class Settings extends Fragment implements View.OnClickListener {
   // private DatabaseHelper databaseHelper;
    private static EditText textView;
    private static TextView textView2;
    private static Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_options,
                container, false);

       // initObjects();

        textView = (EditText) view.findViewById(R.id.textView);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);
//        User a = new User(0,"iris","asd","asda");
//        databaseHelper.addUser(a);
//        databaseHelper.addCategory(new Category(a.getId(),"Category a"));
//        Category b = databaseHelper.getCategory(a.getId());
//        Log.wtf("CatFetch",b.getName());
        return view;
    }


   // void initObjects(){
       // databaseHelper = new DatabaseHelper(getActivity());
   // }

    @Override
    public void onClick(View v) {
       // databaseHelper.addUser(new User(0,textView.getText().toString(),"ayi","haha"));
        //Log.wtf("check",databaseHelper.checkUser("iris")+"");
        //textView2.setText(databaseHelper.getUser("iris").getUsername());
    }
}

