package org.sttms.succeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class ChooseSubjects extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<String> selectedItems = new ArrayList<>();
    private Button mButton;
    private TextView mTextView;
    private String role;

    public String[] getData() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getAssets().open("subjects.txt")));
        String[] array = new String[35];
        for(int x = 0; x < array.length; x++){
            array[x] = bufferedReader.readLine();
        }
        return array;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subjects);

        role = getIntent().getStringExtra("Role");

        mListView = findViewById(R.id.checkable_list);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        String[] array = new String[32];
        try {
            array = getData();
        } catch (IOException e){

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.check_boxes, R.id.cb, array);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 String item = ((TextView)view).getText().toString();
                 if(selectedItems.contains(item)){
                     selectedItems.remove(item);
                 } else {
                     selectedItems.add(item);
                 }
            }
        });

        mButton = findViewById(R.id.select_items);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItems.size() == 0){
                    Toast.makeText(ChooseSubjects.this, "You must select at least one subject.", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference currentDB = FirebaseDatabase.getInstance().getReference().child("Users").child(role).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Collections.sort(selectedItems);
                    currentDB.child("Subjects").setValue(selectedItems);
                    if(role.equals("Tutors")) {
                        startActivity(new Intent(ChooseSubjects.this, FindPlaces_Tutor.class));
                    } else {
                        startActivity(new Intent(ChooseSubjects.this, FindPlaces_Tutee.class));
                    }
                }
            }
        });

        mTextView = findViewById(R.id.instructions);
        if(role.equals("Tutors")) {
            mTextView.setText("Please select which subject(s) you would like to tutor.");
        } else {
            mTextView.setText("Please select which subject(s) you would like help for.");
        }
        Log.d("TESTINGG", "done");
    }
}