/*
    ListViews were not covered in class, so most of this app is already complete for you. I've done
    the work to make the ListView add items, and get the item that was clicked. Your job is to
    finish the app by adding your own styles, and including the two components that let the user
    enter their own items. An example and rubric are posted on Moodle to help guide you

    I've used poor variable names and IDs on purpose
    You can rename them with right-click > Refactor > Rename
 */

package ca.davidpellegrini.testtodo;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> list;

    ArrayList<Boolean> checker;
    ArrayAdapter<String> adapter;

    Button add;
    EditText data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        add = findViewById(R.id.add);
        data = findViewById(R.id.data);
        list = new ArrayList<>();
        checker = new ArrayList<>();

        // screen rotation will make data persistent

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        if (savedInstanceState != null){
            for (int i = 0; i < savedInstanceState.getInt("length_item"); i++){
                list.add(savedInstanceState.getString("item_"+i));
                checker.add(savedInstanceState.getBoolean("checked_"+i));
                listView.setItemChecked(i,savedInstanceState.getBoolean("checked_"+i));
            }
        }


        // onClick for the "add" button
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!data.getText().toString().matches("")){
                    list.add(data.getText().toString());
                    checker.add(false);
                    adapter.notifyDataSetChanged();
                    data.setText("");
                }
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            // onClick for list items
            @Override
            public void onItemClick(AdapterView<?> d, View e, int position, long g) {
                CheckedTextView textView = ((CheckedTextView) e);

                if(textView.isChecked()){
                    checker.set(position, true);
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    textView.setPaintFlags(0);
                    checker.set(position, false);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("length_item",list.size());
        for (int i= 0; i< list.size();i++){
            savedInstanceState.putString("item_"+i, list.get(i));
            savedInstanceState.putBoolean("checked_"+i, checker.get(i));
        }
        super.onSaveInstanceState(savedInstanceState);
    }
}