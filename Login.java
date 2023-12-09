package com.example.tastymix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastymix.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private EditText gmailOrUsername;
    private EditText password;

    private ProgressBar loading;
    private TextView RegisterTv;
    private com.google.android.material.button.MaterialButton loginButton;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gmailOrUsername = findViewById(R.id.gmail_or_username);
        password =findViewById(R.id.password);
        loginButton=findViewById(R.id.login_button);
        RegisterTv=findViewById(R.id.signUpTV);
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        Toast.makeText(Login.this, usersRef.toString(), Toast.LENGTH_SHORT).show();

        Log.d("Snapshot", usersRef.toString());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(password.getText())|| TextUtils.isEmpty(gmailOrUsername.getText())){
                    Toast.makeText(Login.this,"Please fill all fileds", Toast.LENGTH_SHORT).show();

                }else{

                    Query query = usersRef.orderByChild("username").equalTo(gmailOrUsername.getText().toString()).limitToFirst(1);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // User with the provided username found
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    UserModel user = userSnapshot.getValue(UserModel.class);
                                    // Do something with the user data
                                }
                            } else {
                                // No user found with the provided username, try querying by email
                                Query emailQuery = usersRef.orderByChild("gmail").equalTo(gmailOrUsername.getText().toString()).limitToFirst(1);

                                emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot emailSnapshot) {
                                        if (emailSnapshot.exists()) {
                                            // User with the provided email found
                                            for (DataSnapshot userSnapshot : emailSnapshot.getChildren()) {

                                                UserModel user = userSnapshot.getValue(UserModel.class); // Assuming 'User' is your model class
                                                // Do something with the user data
                                            }
                                        } else {

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        // Handle errors
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors
                        }
                    }); //listener end
                }
            }
        });
        RegisterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);
            }
        });

    }
}
