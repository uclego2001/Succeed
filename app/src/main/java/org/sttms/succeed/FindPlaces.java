package org.sttms.succeed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FindPlaces extends AppCompatActivity {

    private Button mLogoutButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private String role, fName, lName;
    private TextView mName;
    private SharedPreferences prefs = null;

    public void check() {
        if (prefs.getBoolean("firstrun", true)) {
            Intent i = new Intent(FindPlaces.this, ChooseSubjects.class);
            i.putExtra("Role", role);
            i.putExtra("First", fName);
            i.putExtra("Last", lName);
            prefs.edit().putBoolean("firstrun", false).apply();
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        role = getIntent().getStringExtra("Role");
        fName = getIntent().getStringExtra("First");
        lName = getIntent().getStringExtra("Last");

        prefs = getSharedPreferences("org.sttms.succeed", MODE_PRIVATE);
        check();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_places);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };

        Toast.makeText(this, role, Toast.LENGTH_SHORT).show();

//        mLogoutButton = findViewById(R.id.logout);
//        mLogoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                SharedPreferences sharedPref = FindPlaces.this.getSharedPreferences("status", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putString("status", "loggedout");
//                editor.apply();
//                Log.d("SHAREDPREF", sharedPref.getString("status", "null"));
//                startActivity(new Intent(FindPlaces.this, MainActivity.class));
//                System.exit(0);
//            }
//        });

        mName = findViewById(R.id.username);
        mName.setText("Welcome " + fName + " " + lName);
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}