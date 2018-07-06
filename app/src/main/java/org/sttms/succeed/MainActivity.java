package org.sttms.succeed;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button tutorButton, tuteeButton;
    private DatabaseReference mReferenceTutors, mReferenceTutees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mReferenceTutees = FirebaseDatabase.getInstance().getReference("Users").child("Tutees");
        mReferenceTutees.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        if (d.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            Intent intent = new Intent(MainActivity.this, TutorLogIn.class);
                            intent.putExtra("Role", "Tutee");
                            intent.putExtra("First", (String) d.child("First").getValue());
                            intent.putExtra("Last", (String) d.child("Last").getValue());
                            startActivity(intent);
                            finish();
                        }
                    }
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
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        if (d.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            Intent intent = new Intent(MainActivity.this, TutorLogIn.class);
                            intent.putExtra("Role", "Tutor");
                            intent.putExtra("First", (String) d.child("First").getValue());
                            intent.putExtra("Last", (String) d.child("Last").getValue());
                            startActivity(intent);
                            finish();
                        }

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        setContentView(R.layout.activity_main);

        tutorButton = findViewById(R.id.tutor_button);
        tuteeButton = findViewById(R.id.tutee_button);

        tutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TutorLogIn.class));
                finish();
            }
        });

        tuteeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TuteeLogIn.class));
                finish();
            }
        });
    }
}
