package bai.wapp.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bai.wapp.R;
import bai.wapp.Models.User;

/**
 * Created by next on 4/12/2017.
 */

public class Home extends Fragment {

    private static TextView username;
    private static TextView name;
    View view;
    User user;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_home,
                container, false);
        initViews();
        String uname = getArguments().getString("uname");
        String nname = getArguments().getString("name");
        username.setText(uname);
        name.setText(nname);
        return view;
    }

    void initViews(){
        username = (TextView) view.findViewById(R.id.c_uname);
        name = (TextView) view.findViewById(R.id.c_name);
    }
}
