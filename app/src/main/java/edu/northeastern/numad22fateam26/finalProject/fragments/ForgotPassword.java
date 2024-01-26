package edu.northeastern.numad22fateam26.finalProject.fragments;

import static edu.northeastern.numad22fateam26.finalProject.fragments.CreateAccountFragment.EMAIL_REGEX;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.numad22fateam26.R;
import edu.northeastern.numad22fateam26.finalProject.ReplacerActivity;


public class ForgotPassword extends Fragment {

    private ImageView loginTv;
    private Button recoverBtn;
    private TextInputLayout emailEt;

    private FirebaseAuth auth;

    private RelativeLayout progressBar;

    public ForgotPassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        clickListener();

    }

    private void init(View view){

        loginTv = view.findViewById(R.id.forget_password_back_btn);
        emailEt = view.findViewById(R.id.emailET);
        recoverBtn = view.findViewById(R.id.recoverBtn);
        progressBar = view.findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

    }

    private void clickListener(){

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReplacerActivity) getActivity()).setFragment(new LoginFragment());
            }
        });

        recoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEt.getEditText().getText().toString().trim();

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)){
                    emailEt.setError("Input valid email");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(), "Password reset email send successfully",
                                            Toast.LENGTH_SHORT).show();
                                 //   emailEt.setText("");
                                }else {
                                    String errMsg = task.getException().getMessage();
                                    Toast.makeText(getContext(), "Error: "+errMsg, Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);

                            }
                        });


            }
        });

    }

}