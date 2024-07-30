package com.inkrypt;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lambdapioneer.argon2kt.Argon2Kt;
import com.lambdapioneer.argon2kt.Argon2KtResult;
import com.lambdapioneer.argon2kt.Argon2Mode;
import com.lambdapioneer.argon2kt.Argon2Version;

public class VerifyFragment extends Fragment {

    private EditText editTextInput;
    private TextView textViewVerifyHash;
    private Button buttonClear, buttonVerify;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify, container, false);

        editTextInput = view.findViewById(R.id.editTextInput);
        textViewVerifyHash = view.findViewById(R.id.textViewVerifyHash);
        buttonClear = view.findViewById(R.id.buttonClear);
        buttonVerify = view.findViewById(R.id.buttonVerify);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextInput.setText("");
            }
        });

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyNumber();
            }
        });

        return view;
    }

    private void verifyNumber() {
        String input = editTextInput.getText().toString();
        if (TextUtils.isDigitsOnly(input) && input.length() == 6) {

            final Argon2Kt argon2Kt = new Argon2Kt();
            String salt = "00000000";

            final Argon2KtResult hashResult = argon2Kt.hash(Argon2Mode.ARGON2_ID, String.valueOf(input).getBytes(), salt.getBytes(),4, 16, 1, 16, Argon2Version.V13);
            final String encodedOutput = hashResult.rawHashAsHexadecimal(true);

            textViewVerifyHash.setText("Argon2 hash: " + encodedOutput.substring(encodedOutput.length() - 6));
        } else {
            textViewVerifyHash.setText("Please enter a 6-digit number");
        }
    }
}
