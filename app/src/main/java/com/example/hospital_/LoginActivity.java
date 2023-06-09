package com.example.hospital_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button button;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-65a7f-default-rtdb.firebaseio.com/");
    private TextView LoginPageQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.loginEmail);
        final EditText password = findViewById(R.id.loginpassword);
        final Button LoginBtn = findViewById(R.id.loginbutton);

        button = findViewById(R.id.loginbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernameTxt = email.getText().toString();
                final String PasswordTxt = password.getText().toString();

                if (usernameTxt.isEmpty() || PasswordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your Username and Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if idNo is existing in the db
                            if (snapshot.hasChild(usernameTxt)){

                                final String getpassword = snapshot.child(usernameTxt).child("password").getValue(String.class);

                                if (getpassword.equals(PasswordTxt)){
                                    Toast.makeText(LoginActivity.this, "Successfully Logged in",Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(LoginActivity.this, StudentMainActivity.class);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(LoginActivity.this,"Wrong Credentials",Toast.LENGTH_SHORT).show();
                                }

                            }
//
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                ////////////////////////////////////////////////////////////////////////////////
                databaseReference.child("Teacher").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //check if idNo is existing in the db
                        if (snapshot.hasChild(usernameTxt)){

                            final String getpassword = snapshot.child(usernameTxt).child("password").getValue(String.class);

                            if (getpassword.equals(PasswordTxt)){
                                Toast.makeText(LoginActivity.this, "Successfully Logged in",Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(LoginActivity.this, TeacherMain.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(LoginActivity.this,"Wrong Credentials",Toast.LENGTH_SHORT).show();
                            }

                        }
//
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                ////////////////////////////////////////////////////////////////////////////////

            }
        });
        LoginPageQ= findViewById(R.id.signup);
        LoginPageQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);
            }
        });
        
}


}