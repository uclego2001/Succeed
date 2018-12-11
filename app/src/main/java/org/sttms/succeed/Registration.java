package org.sttms.succeed;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText firstEditText, lastEditText, emailEditText, passwordEditText, countryEditText, zipEditText;
    private Button register;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(Registration.this, ChooseSubjects.class);
                    intent.putExtra("Role", name);
                    intent.putExtra("First", getIntent().getStringExtra("First"));
                    intent.putExtra("Last", getIntent().getStringExtra("Last"));
                    startActivity(intent);
                    finish();
                }
            }
        };

        name = getIntent().getExtras().getString("Role");
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        firstEditText = findViewById(R.id.first_name);
        lastEditText = findViewById(R.id.last_name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        zipEditText = findViewById(R.id.zip_code);
        countryEditText = findViewById(R.id.country);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString();
                final String pass = passwordEditText.getText().toString();
                final String first = firstEditText.getText().toString();
                final String last = lastEditText.getText().toString();
                final String zip = zipEditText.getText().toString();
                final String country = countryEditText.getText().toString();

                if (email.equals("") || pass.equals("") || first.equals("") || last.equals("") || zip.equals("") || country.equals("")) {
                    Toast.makeText(Registration.this, "All Fields Must be Filled In", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Registration.this, "Error signing up", Toast.LENGTH_SHORT).show();
                            } else {
                                String userId = mAuth.getCurrentUser().getUid();
                                UserProfileChangeRequest.Builder changeRequest = new UserProfileChangeRequest.Builder();
                                changeRequest.setDisplayName(first + " " + last);
                                mAuth.getCurrentUser().updateProfile(changeRequest.build());
                                DatabaseReference currentDB = FirebaseDatabase.getInstance().getReference().child("Users").child(name).child(userId);
                                DatabaseReference fName = currentDB.child("First");
                                DatabaseReference lName = currentDB.child("Last");
                                DatabaseReference zipcode = currentDB.child("Zip Code");
                                DatabaseReference countryN = currentDB.child("Country");
                                currentDB.setValue(true);
                                fName.setValue(first);
                                lName.setValue(last);
                                zipcode.setValue(zip);
                                countryN.setValue(country);
                            }
                        }
                    });
                }
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
