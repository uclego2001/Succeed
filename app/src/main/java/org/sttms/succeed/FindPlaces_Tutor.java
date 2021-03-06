package org.sttms.succeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FindPlaces_Tutor extends AppCompatActivity {

    private Button mLogoutButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private String role, name;
    private TextView mName;
    private SharedPreferences prefs = null;
    private DatabaseReference mReferenceTutors, mReferenceTutees;
    private Iterable<DataSnapshot> data;

    public void check() {
        if (prefs.getBoolean("firstrun", true)) {
            startActivity(new Intent(FindPlaces_Tutor.this, ChooseSubjects.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = getSharedPreferences("org.sttms.succeed", MODE_PRIVATE);
        check();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_places_tutor);

        role = "Tutor";

        mName = findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(mAuth.getCurrentUser() != null) {
                    name = mAuth.getCurrentUser().getDisplayName();
                    mName.setText("Welcome " + name);
                }
            }
        };

        mReferenceTutees = FirebaseDatabase.getInstance().getReference("Users").child("Tutees");
        mReferenceTutees.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mAuth.getCurrentUser() != null) {
                    data = dataSnapshot.getChildren();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mReferenceTutors = FirebaseDatabase.getInstance().getReference("Users").child("Tutors");
        mReferenceTutors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mAuth.getCurrentUser() != null) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        if (d.getKey().equals(mAuth.getCurrentUser().getUid())) {
                            if(d.child("Subjects").getValue() == null){
                                Intent intent = new Intent(FindPlaces_Tutor.this, ChooseSubjects.class);
                                intent.putExtra("Role", "Tutors");
                                startActivity(intent);
                                finish();
                            }
                            break;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        mLogoutButton = findViewById(R.id.logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(FindPlaces_Tutor.this, MainActivity.class));
                finish();
            }
        });
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