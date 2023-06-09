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

public class StudentReg extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-65a7f-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        final EditText fullname = findViewById(R.id.patientName);
        final EditText email = findViewById(R.id.PatientEmail);
        final EditText Idno = findViewById(R.id.patientNo);
        final EditText Phn = findViewById(R.id.patientPhNo);
        final EditText password = findViewById(R.id.PatientPassword);
        final TextView Back = findViewById(R.id.backTo);

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
                    Toast.makeText(StudentReg.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
else if (Phntxt.isEmpty()) {
                    Toast.makeText(StudentReg.this, "Please fill apna phone number", Toast.LENGTH_SHORT).show();
                }


                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if email is registered
                            if (snapshot.hasChild(Phntxt)){
                                Toast.makeText(StudentReg.this, "Phone is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //sending data to firebase realtime database
                                //using email as unique identity
                                databaseReference.child("users").child(regtxt).child("fullname").setValue(fullnametxt);
                                databaseReference.child("users").child(regtxt).child("email").setValue(emailtxt);
                                databaseReference.child("users").child(regtxt).child("password").setValue(passwordtxt);
                                databaseReference.child("users").child(regtxt).child("Phn").setValue(Phntxt);
                                databaseReference.child("users").child(regtxt).child("Idno").setValue(regtxt);

                                Toast.makeText(StudentReg.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
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
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentReg.this, LoginActivity.class);
                startActivity(intent);
            }

        });

    }
}



  /*  private TextView Back;

    private TextInputEditText fullName,idNo,PhnNo,LoginEmail,LoginPassword;
    private Button regBtn;
    private CircleImageView profileImg;
    private Uri resultUri;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference databaseReference;
    private ProgressDialog loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        Back = findViewById(R.id.backTo);
        fullName = findViewById(R.id.patientName);
        idNo = findViewById(R.id.patientNo);
        PhnNo = findViewById(R.id.patientPhNo);
        LoginEmail = findViewById(R.id.PatientEmail);
        LoginPassword = findViewById(R.id.PatientPassword);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientRegistration.this, LoginActivity.class);
                startActivity(intent);
            }

        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
*/

