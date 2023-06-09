package com.example.hospital_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class TeacherReg extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-65a7f-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);
        //Back=findViewById(R.id.gotoLogin);

        final EditText fullname = findViewById(R.id.docName);
        final EditText email = findViewById(R.id.PatientEmail);
        final EditText Idno = findViewById(R.id.docNo);
        final EditText Phn = findViewById(R.id.docPhNo);
        final EditText password = findViewById(R.id.PatientPassword);
       // final TextView Back = findViewById(R.id.backTo);
        final Button registerBtn = findViewById(R.id.RegButton);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fetch data from edit text
                final String fullnametxt = fullname.getText().toString();
                final String emailtxt = email.getText().toString();
                final String regtxt = Idno.getText().toString();
                final String Phntxt = Phn.getText().toString();
                final String passwordtxt = password.getText().toString();


                //checking no fields are empty
                if (fullnametxt.isEmpty() || emailtxt.isEmpty() || passwordtxt.isEmpty() || regtxt.isEmpty()){
                    Toast.makeText(TeacherReg.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (Phntxt.isEmpty()) {
                    Toast.makeText(TeacherReg.this, "Please fill apna phone number", Toast.LENGTH_SHORT).show();
                }


                else {

                    databaseReference.child("Teacher").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if email is registered
                            if (snapshot.hasChild(Phntxt)){
                                Toast.makeText(TeacherReg.this, "Phone is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //sending data to firebase realtime database
                                //using email as unique identity
                                databaseReference.child("Teacher").child(regtxt).child("fullname").setValue(fullnametxt);
                                databaseReference.child("Teacher").child(regtxt).child("email").setValue(emailtxt);
                                databaseReference.child("Teacher").child(regtxt).child("password").setValue(passwordtxt);
                                databaseReference.child("Teacher").child(regtxt).child("Phone").setValue(Phntxt);
                                databaseReference.child("Teacher").child(regtxt).child("Idno").setValue(regtxt);

                                Toast.makeText(TeacherReg.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


   }
}