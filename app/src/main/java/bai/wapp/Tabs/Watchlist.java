package bai.wapp.Tabs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import bai.wapp.Helper.DatabaseHelper;
import bai.wapp.Models.Category;
import bai.wapp.Models.User;
import bai.wapp.R;
import bai.wapp.Activities.VideoList;

/**
 * Created by next on 4/12/2017.
 */

public class Watchlist extends Fragment implements View.OnClickListener {
    Button addCategory;
    LinearLayout layout;
    ViewGroup container_frame;
    DatabaseHelper db;
    User user;
    View view;
    private String categoryName = "";
    int i = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        String uname = getArguments().getString("uname");
        String nname = getArguments().getString("name");

        container_frame = container;
        db = DatabaseHelper.getInstance(getActivity());
        user = db.getUser(uname);
        view = inflater.inflate(R.layout.tab_watchlist,container,false);
        layout = (LinearLayout) view.findViewById(R.id.gridlayout);
        addCategory = (Button) view.findViewById(R.id.addCategory);
        addCategory.setOnClickListener(this);
        //addListenerOnButton();
        return view;
    }

    public void addListenerOnButton() {

        addCategory = (Button) view.findViewById(R.id.addCategory);

        addCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Button image = new Button(getActivity());
                image.setBackgroundResource(R.drawable.add_category);
                image.setText("category" + i);

                image.setId(i++);
                layout.addView(image);

                Toast.makeText(getActivity(),
                        "ImageButton is clicked!" + image.getId(), Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addCategory:

                showDialog(this);
                Toast.makeText(getActivity(),
                        "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
                break;
            default:
                //go to video list
                Button b = (Button)v;
                String buttonText = b.getText().toString();
                Intent intent = new Intent(getActivity(), VideoList.class);
                intent.putExtra("from", buttonText);
                intent.putExtra("user", user.getUsername());
                startActivity(intent);
                Toast.makeText(getActivity(),
                        "ImageButton is clicked! " + buttonText, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showDialog(final Watchlist w){
        final Button image = new Button(getActivity());
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.prompt);
        dialog.setCancelable(true);


        Button button = (Button) dialog.findViewById(R.id.promptOk);
        Button button2 = (Button) dialog.findViewById(R.id.promptCancel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText editText = (EditText) dialog.findViewById(R.id.categoryTitleInput);
                String categoryTitle = editText.getText().toString();
                //if(db.userHasCategory(categoryTitle, "" + user.getId())){
                //Category category = db.getCategory(categoryTitle);

                if(db.checkCategory(categoryTitle)){
                    Category category = db.getCategory(categoryTitle);
                    if(db.userHasCategory(category.getId(), user.getId())){
                        Toast.makeText(getActivity(),
                                "Category already exists!" + categoryTitle, Toast.LENGTH_SHORT).show();
                    }else{
                        db.addUserCategory(user.getId(),category.getId());
                        image.setBackgroundResource(R.drawable.add_category);
                        image.setTransformationMethod(null);
                        image.setText(categoryTitle);
                        image.setId(i++);
                        layout.addView(image);
                        image.setOnClickListener(w);
                        Toast.makeText(getActivity(),
                                "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }else{
                    Category category = new Category(db.getAllCategories().size(), categoryTitle);
                    // Category category1 = new Category()
                    db.addCategory(category);
                    db.addUserCategory(user.getId(),category.getId());
                    image.setBackgroundResource(R.drawable.add_category);
                    image.setTransformationMethod(null);
                    image.setText(categoryTitle);
                    image.setId(i++);
                    layout.addView(image);
                    image.setOnClickListener(w);
                    Toast.makeText(getActivity(),
                            "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }









            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
