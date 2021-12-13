package com.example.bubaleapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bubaleapp.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class EditProfile extends Fragment {

    private EditText first;
    private EditText last;
    private EditText address;
    private EditText address2;
    private EditText zip;
    private Button btnsave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        first = view.findViewById(R.id.etFirstedit);
        last = view.findViewById(R.id.etLastedit);
        address = view.findViewById(R.id.etaddedit);
        address2 = view.findViewById(R.id.etadd2edit);
        zip = view.findViewById(R.id.etzipedit);
        btnsave = view.findViewById(R.id.btnsave);

        first.setText(ParseUser.getCurrentUser().getString("first"));
        last.setText(ParseUser.getCurrentUser().getString("last"));
        address.setText(ParseUser.getCurrentUser().getString("address"));
        address2.setText(ParseUser.getCurrentUser().getString("address2"));
        zip.setText(ParseUser.getCurrentUser().getString("zip"));

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.put("first", first.getText().toString());
                currentUser.put("last", last.getText().toString());
                currentUser.put("address", address.getText().toString());
                currentUser.put("address2", address2.getText().toString());
                currentUser.put("zip", zip.getText().toString());

                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Profile Updated!!", Toast.LENGTH_SHORT).show();
                            Fragment fragment = new homeFragment();
                            ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.maincont, fragment).commit();
                        }
                    }
                });
            }
        });
    }
}