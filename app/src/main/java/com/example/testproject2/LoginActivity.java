package com.example.testproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText  mail,passwrod;
    Button Sign;
    String emailPattern="[a-zA-Z0-9._-]@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mail=findViewById(R.id.et_mail);
        Sign=findViewById(R.id.Sign_in);
        passwrod=findViewById(R.id.et_password);

        progressDialog =new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(LoginActivity.this,MainActivity.class));
                //finish();
                perforLogin();
            }
        });
    }

    private void perforLogin() {
        String email = mail.getText().toString();
        String password = passwrod.getText().toString();

        if (email.matches(emailPattern)) {
            mail.setError("Enter your mail");
        } else if (password.isEmpty() || password.length() < 6) {
            passwrod.setError("Enter your password");
        } else {

            progressDialog.setMessage("wait ...");
            progressDialog.setMessage("Login ");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
           /* mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendUserToNextActivity();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });µ

            */
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //progressDialog.dismiss();
                        //sendUserToNextActivity();
                        Intent intent = new Intent(LoginActivity.this,CenterActivity.class);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    }

                    else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        }





    private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginActivity.this,CenterActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();


    }
}