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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TutorLogIn extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
//    private SharedPreferences sharedPreferences;
//    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_log_in);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                sharedPreferences = TutorLogIn.this.getSharedPreferences("status", Context.MODE_PRIVATE);
//                status = sharedPreferences.getString("status", "null");
//                Log.d("SHAREDPREF", status);
//                if (status.equals("loggedin")) {
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    if (user != null) {
//                        Intent intent = new Intent(TutorLogIn.this, FindPlaces.class);
//                        intent.putExtra("Role", "Tutor");
//                        intent.putExtra("First", getIntent().getStringExtra("First"));
//                        intent.putExtra("Last", getIntent().getStringExtra("Last"));
//                        startActivity(intent);
//                        finish();
//                    }
//                }
            }
        };

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorLogIn.this, Registration.class);
                intent.putExtra("Role", "Tutors");
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString();
                final String pass = passwordEditText.getText().toString();
                if (!(email.equals("") || pass.equals(""))) {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(TutorLogIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(TutorLogIn.this, "Error logging in", Toast.LENGTH_SHORT).show();
                            } else {
//                              SharedPreferences sharedPref = getSharedPreferences("status", Context.MODE_PRIVATE);
//                              SharedPreferences.Editor editor = sharedPref.edit();
//                              editor.putString("status", "loggedin");
//                              editor.apply();
//                              Log.d("SHAREDPREF1", status);
                                Intent intent = new Intent(TutorLogIn.this, FindPlaces.class);
                                intent.putExtra("Role", "Tutor");
                                intent.putExtra("First", task.getResult().getUser().getDisplayName());
                                intent.putExtra("Last", "");
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TutorLogIn.this, MainActivity.class));
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
