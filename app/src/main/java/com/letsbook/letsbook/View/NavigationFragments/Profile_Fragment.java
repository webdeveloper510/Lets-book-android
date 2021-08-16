package com.letsbook.letsbook.View.NavigationFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.Response_ForgotPassword;
import com.letsbook.letsbook.Model.Response_updateProfile;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Profile_Fragment extends Fragment {

    private static final int MY_GALLERY_PERMISSION_CODE = 101;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    private EditText profileUserName, profileEmail, profileCall;
    private Button profileEdit;
    private LinearLayout profileSubmit;
    private CircleImageView profileImage;
    private ImageView openGallery;
    private int i = 1;
    private RadioGroup profileGender;
    private ProgressDialog progress;
    String imagePath = "";
    private Uri image_uri;
    public File file;
    public MultipartBody.Part body;


    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private RadioButton radio_zero, radio_one, radio_two;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_, container, false);

        profileEdit = view.findViewById(R.id.profile_edit);
        profileImage = view.findViewById(R.id.profile_image);
        openGallery = view.findViewById(R.id.open_gallery);
        profileUserName = view.findViewById(R.id.profile_user_name);
        profileEmail = view.findViewById(R.id.profile_email);
        profileCall = view.findViewById(R.id.profile_call);
        profileGender = view.findViewById(R.id.profile_gender);
        radio_zero = view.findViewById(R.id.radio0);
        radio_one = view.findViewById(R.id.radio1);
        radio_two = view.findViewById(R.id.radio2);
        profileSubmit = view.findViewById(R.id.profile_submit);


        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();
        profileSubmit.setVisibility(View.GONE);
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        openGallery.setVisibility(View.GONE);
        profileUserName.setEnabled(false);
        profileEmail.setEnabled(false);
        profileCall.setEnabled(false);
        radio_zero.setEnabled(false);
        radio_one.setEnabled(false);
        radio_two.setEnabled(false);

        hitGetProfileApi();

        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileEdit.setVisibility(View.GONE);
                profileSubmit.setVisibility(View.VISIBLE);
                openGallery.setVisibility(View.VISIBLE);
                profileUserName.setEnabled(true);
                profileCall.setEnabled(true);
                radio_zero.setEnabled(true);
                radio_one.setEnabled(true);
                radio_two.setEnabled(true);
            }
        });

        // Open gallery
        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
                //selectImage();
            }
        });

//        Select image from the gallery
        profileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });


        return view;
    }

    private void requestPermissions() {
        // below line is use to request
        // permission in the current activity.
        Dexter.withContext(getActivity())
                // below line is use to request the number of
                // permissions which are required in our app.
                .withPermissions(Manifest.permission.
                        READ_EXTERNAL_STORAGE, CAMERA, WRITE_EXTERNAL_STORAGE)
                // after adding permissions we are
                // calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            selectImage();

                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently,
                            // we will show user a dialog message.
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        // this method is called when user grants some
                        // permission and denies some of them.
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            // this method is use to handle error
            // in runtime permissions
            @Override
            public void onError(DexterError error) {
                // we are displaying a toast message for error message.
                Toast.makeText(getActivity().getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        })
                // below line is use to run the permissions
                // on same thread and to check the permissions
                .onSameThread().check();
    }

    // below is the shoe setting dialog
    // method which is use to display a
    // dialogue message.
    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

        // below line is the title
        // for our alert dialog.
        builder.setTitle("Need Permissions");

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // this method is called on click on positive
                // button and on clicking shit button we
                // are redirecting our user from our app to the
                // settings page of our app.
                dialog.cancel();
                // below is the intent from which we
                // are redirecting our user.
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // this method is called when
                // user click on negative button.
                dialog.cancel();
            }
            // below line is used
        });
        // to display our dialog
        builder.show();
    }

    private void hitGetProfileApi() {

        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();

        String id = user.getId();
        String profile_image = "";
        String name = profileUserName.getText().toString().trim();
        String email = profileEmail.getText().toString().trim();
        String phone = "";
        String gender = "";
        Log.e("phone", phone + " before getting");

        Call<Response_ForgotPassword> call = ApiClient.getInstance().getApi().hitGetProfileApi(id, profile_image, name, email, phone, gender);
        call.enqueue(new Callback<Response_ForgotPassword>() {
            @Override
            public void onResponse(Call<Response_ForgotPassword> call, Response<Response_ForgotPassword> response) {

                progress.dismiss();
                if (response.code() == 200) {

                    if (response.body().getData().getProfile_image() != null) {

                        Glide.with(getActivity()).load(response.body().getData().getProfile_image())
                                .placeholder(R.drawable.ic_user).into(profileImage);

                        Log.e("profileImage", response.body().getData().getProfile_image());
                    } else {
                        Glide.with(getActivity()).load(R.drawable.ic_user).into(profileImage);
//                        Picasso.get().load(R.drawable.ic_user).into(profileImage);
                        Log.e("profileImage", "else part");
                    }

                    profileUserName.setText(response.body().getData().getName());
                    profileEmail.setText(response.body().getData().getEmail());
                    profileCall.setText(response.body().getData().getPhone());
                    Log.e("phone", response.body().getData().getPhone() + " after getting ");

                    if (response.body().getData().getGender() == null) {

                    } else {
                        if (response.body().getData().getGender().isEmpty()) {
                            Toast.makeText(getActivity(), "Gender is empty", Toast.LENGTH_SHORT).show();
                        } else {
                            if (response.body().getData().getGender().equals("0")) {
                                radio_zero.setChecked(true);
                            } else if (response.body().getData().getGender().equals("1")) {
                                radio_one.setChecked(true);
                            } else if (response.body().getData().getGender().equals("2")) {
                                radio_two.setChecked(true);
                            }
                        }
                    }

                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_ForgotPassword> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getActivity(), "Please check your network connection !!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void selectImage() {
        try {
            PackageManager pm = getActivity().getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            String fileName = System.currentTimeMillis() + ".jpg";
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, fileName);
                            image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
                            startActivityForResult(intent, MY_CAMERA_PERMISSION_CODE);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, MY_GALLERY_PERMISSION_CODE);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else {
                requestPermissions();

            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MY_CAMERA_PERMISSION_CODE:
                if (resultCode == Activity.RESULT_OK) {

                    imagePath = getPath(image_uri);
                    Glide.with(this).load(imagePath).placeholder(R.drawable.ic_user).into(profileImage);

                }
                break;

            case MY_GALLERY_PERMISSION_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bm = null;
                    try {
                        bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imagePath = cursor.getString(columnIndex);
                        cursor.close();

                        Log.e("TAG", "filefile22 " + imagePath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Glide.with(this).load(bm).placeholder(R.drawable.ic_user).into(profileImage);
                }
                break;
        }
    }


    private String getPath(Uri selectedImaeUri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = getActivity().managedQuery(selectedImaeUri, projection, null, null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            return cursor.getString(columnIndex);
        }

        return selectedImaeUri.getPath();
    }


    private void UpdateProfile() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();

        String radioValue = null;
        if (radio_zero.isChecked()) {
            radioValue = "0";
        } else if (radio_one.isChecked()) {
            radioValue = "1";
        } else if (radio_two.isChecked()) {
            radioValue = "2";
        }

        String profile = imagePath;
        Log.e("response", profile + " before update");
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), user.getId());
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), radioValue);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), profileUserName.getText().toString().trim());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), profileEmail.getText().toString().trim());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), profileCall.getText().toString().trim());


        try {
            file = new File(profile);
            Bitmap bitmap = BitmapFactory.decodeFile(profile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);   //compress to 70% of original image quality
            byte[] byte_arr = stream.toByteArray();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("profile", file.getName(), requestFile);
        }catch (Exception e){

        }


        if (name.toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
        } else if (email.toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_SHORT).show();
        } else if (phone.toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
        } else if (phone.toString().length() <= 9) {
            Toast.makeText(getActivity(), "Please enter minimum 10 character", Toast.LENGTH_SHORT).show();
        } else if (!email.toString().matches(emailPattern)) {

            progress = new ProgressDialog(getActivity());
            progress.setCancelable(false);// disable dismiss by tapping outside of the dialog
            progress.setMessage("Loading...");
            progress.show();

            Call<Response_updateProfile> call = ApiClient.getInstance().getApi().hitUpdateProfileApi(user_id, body, name, email, phone, gender);

            call.enqueue(new Callback<Response_updateProfile>() {
                @Override
                public void onResponse(Call<Response_updateProfile> call, Response<Response_updateProfile> response) {

                    progress.dismiss();
                    Response_updateProfile response_updateProfile = response.body();
                    if (response_updateProfile.getCode() == 200) {

                        Log.e("response", phone + " after update");
                        Log.e("response", profile + " after update");
                        profileEdit.setVisibility(View.VISIBLE);
                        profileSubmit.setVisibility(View.GONE);
                        openGallery.setVisibility(View.GONE);
                        profileUserName.setEnabled(false);
                        profileCall.setEnabled(false);
                        radio_zero.setEnabled(false);
                        radio_one.setEnabled(false);
                        radio_two.setEnabled(false);
                        Toast.makeText(getActivity(), response.body().getData() + "", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), response_updateProfile.getData() + "", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response_updateProfile> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                    Log.e("response", t.getMessage());
                }
            });

        } else {
            Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
        }


    }
}