package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.Response_updateProfile;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePassword extends Fragment {

    private ImageView changePasswordBack;
    private EditText currentPassword, newPassword, conformPassword;
    private LinearLayout changePasswordSubmit;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    public ChangePassword() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        currentPassword = view.findViewById(R.id.current_password);
        newPassword = view.findViewById(R.id.new_password);
        conformPassword = view.findViewById(R.id.conformPassword);
        changePasswordSubmit = view.findViewById(R.id.Change_password_submit);
        changePasswordBack = view.findViewById(R.id.change_password_back);

        changePasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ChangePassword .this);
                fragmentTransaction.commit();
                fragmentManager.popBackStack();*/
                updatePassword();
            }
        });

        changePasswordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ChangePassword.this);
                fragmentTransaction.commit();
                fragmentManager.popBackStack();
            }
        });


        return view;
    }

    void updatePassword() {

        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();
        String old_password = currentPassword.getText().toString().trim();
        String new_poasword = newPassword.getText().toString().trim();
        String conform_password = conformPassword.getText().toString().trim();
        String user_id = user.getId();

        if (old_password.isEmpty()){
            Toast.makeText(getActivity(), "Please enter current password", Toast.LENGTH_SHORT).show();
        }else if (new_poasword.isEmpty()){
            Toast.makeText(getActivity(), "Please enter new password", Toast.LENGTH_SHORT).show();
        }else if (new_poasword.length()<6){
            Toast.makeText(getActivity(), "Please enter minimum 6 characters !", Toast.LENGTH_SHORT).show();
        }else if (conform_password.isEmpty()){
            Toast.makeText(getActivity(), "Please conform your password", Toast.LENGTH_SHORT).show();
        }else if (conform_password.equals(new_poasword)) {
            Call<Response_updateProfile> call = ApiClient.getInstance().getApi().hitUpdatePasswordApi(old_password, new_poasword, user_id);

            call.enqueue(new Callback<Response_updateProfile>() {
                @Override
                public void onResponse(Call<Response_updateProfile> call, Response<Response_updateProfile> response) {
                    Response_updateProfile response_updateProfile = response.body();

                    if (response_updateProfile.getCode()==200){
                        Log.e("update_response", response.message());
                        Toast.makeText(getActivity(), "Password updated successfully !", Toast.LENGTH_SHORT).show();
                        currentPassword.setText("");
                        newPassword.setText("");
                        conformPassword.setText("");
                    }else {
                        Toast.makeText(getActivity(), "Please correct your current password", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Response_updateProfile> call, Throwable t) {
                    Log.e("update_response", t.getMessage());
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Password doesn't match", Toast.LENGTH_SHORT).show();
        }
    }
}