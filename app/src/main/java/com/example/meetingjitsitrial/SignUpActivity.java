package com.example.meetingjitsitrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    EditText registername,registeremail,registerpassword,registerconfirmPassword;
    Button registerBtn;
    TextView loginRedirect;
    FirebaseAuth auth;
    FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        registername = findViewById(R.id.registerName);
        registeremail = findViewById(R.id.registerEmail);
        registerpassword = findViewById(R.id.registerPassword);
        registerconfirmPassword = findViewById(R.id.registerConfirmPassword);
        registerBtn = findViewById(R.id.registerBtn);
        loginRedirect = findViewById(R.id.loginRedirect);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email,password,confirmPassword;
                name = registername.getText().toString();
                email = registeremail.getText().toString();
                password = registerpassword.getText().toString();
                confirmPassword = registerconfirmPassword.getText().toString();

                final Users users = new Users();
                users.setName(name);
                users.setEmail(email);
                users.setPassword(password);

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
                    if(confirmPassword.equals(password)){
                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    database.collection("Users")
                                            .document().set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                                    finish();
                                                }
                                            });
//                                Toast.makeText(SignUpActivity.this, "Account is created", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Your Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(name.isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Username is empty", Toast.LENGTH_SHORT).show();
                    } else if (email.isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Confirm password is empty", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}