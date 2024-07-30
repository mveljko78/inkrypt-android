package com.inkrypt;

import android.os.Bundle;
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

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.Random;

public class InkryptFragment extends Fragment {

    private EditText editTextNumber;
    private TextView textViewHash;
    private Button buttonInkrypt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inkrypt, container, false);

        editTextNumber = view.findViewById(R.id.editTextNumber);
        textViewHash = view.findViewById(R.id.textViewHash);
        buttonInkrypt = view.findViewById(R.id.buttonInkrypt);

        buttonInkrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAndHashNumber();
            }
        });

        return view;
    }

    private void generateAndHashNumber() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        String salt = "00000000";
        editTextNumber.setText(String.valueOf(randomNumber));

        final Argon2Kt argon2Kt = new Argon2Kt();


        final Argon2KtResult hashResult = argon2Kt.hash(Argon2Mode.ARGON2_ID, String.valueOf(randomNumber).getBytes(), salt.getBytes(),4, 16, 1, 16, Argon2Version.V13);
        final String encodedOutput = hashResult.rawHashAsHexadecimal(true);


        textViewHash.setText("Argon2 hash: " + encodedOutput.substring(encodedOutput.length() - 6));
    }
}