package com.letsbook.letsbook.View.Inner_Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Adapters.TimeAdapter;
import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.ResponseBooking;
import com.letsbook.letsbook.Model.ResponseTimeSlot;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;
import com.letsbook.letsbook.View.Activitys.Home;
import com.letsbook.letsbook.View.Activitys.Success;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookAnAppointment extends Fragment {

    private LinearLayout bookThisAppointment, addAnotherService;
    private ImageView backImageButton, appointmentRemove;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private ProgressBar bookAppointmentTimeProgressBar;
    private RecyclerView bookAppointmentTimeRecyclerView;
    private TextView appointmentTitle, appointmentPrice, appointmentBio, appointmentDuration;
    private EditText note;
    private String weekday;
    public String updatedDate;
    String categoryName, getDescription, getPrice, getDuration, itemId, selectedDate, todaySDate;
    CalendarView calendarView;
    private ProgressDialog progress;


    public BookAnAppointment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_an_appointment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            categoryName = bundle.getString("categoryName", categoryName);
            getDescription = bundle.getString("getDescription", getDescription);
            getPrice = bundle.getString("getPrice", getPrice);
            getDuration = bundle.getString("getDuration", getDuration);
            itemId = bundle.getString("itemId", itemId);
        }


        bookAppointmentTimeRecyclerView = view.findViewById(R.id.book_an_appointment_time);
        bookAppointmentTimeProgressBar = view.findViewById(R.id.book_an_appointment_progressbar);
        bookThisAppointment = view.findViewById(R.id.book_this_appointment);
        backImageButton = view.findViewById(R.id.booking_appointment_back);
        addAnotherService = view.findViewById(R.id.add_another_service);
        calendarView = view.findViewById(R.id.calender_view);
        note = view.findViewById(R.id.note);

        appointmentTitle = view.findViewById(R.id.appointment_title);
        appointmentPrice = view.findViewById(R.id.appointment_price);
        appointmentBio = view.findViewById(R.id.appointment_bio);
        appointmentDuration = view.findViewById(R.id.appointment_duration);
        appointmentRemove = view.findViewById(R.id.appointment_remove);

        appointmentTitle.setText(categoryName);
        appointmentPrice.setText(getPrice);
        appointmentBio.setText(getDescription);
        appointmentDuration.setText(getDuration);


        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                // get selected day of the week
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                weekday = new DateFormatSymbols().getWeekdays()[dayOfWeek];
                Log.d("timeapi", weekday + "  selected day of the week ");
                //get selected day month year
                String dayOfMonth_ = String.valueOf(dayOfMonth);
                String month_ = String.valueOf(month + 1);
                String year_ = String.valueOf(year);
                selectedDate = year_ + "-" + month_ + "-" + dayOfMonth_;
                Log.e("onSelectedDayChange", selectedDate);
                // call time api on every date click
                getTime();
            }

        });


        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalData.selectedTime = null;

                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(BookAnAppointment.this);
                fragmentTransaction.commit();
                fragmentManager.popBackStack();
            }
        });

        addAnotherService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                fragmentTransaction = ((Home) v.getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AddAnotherService());
                fragmentTransaction.commit();
            }
        });

        bookThisAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                booking();

            }
        });

        appointmentRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalData.selectedTime = null;
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(BookAnAppointment.this);
                fragmentTransaction.commit();
                fragmentManager.popBackStack();
            }
        });


        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        bookAppointmentTimeRecyclerView.setLayoutManager(itemLayoutManager);
        getTime();

        return view;
    }


    private void getTime() {

        // Get current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        Log.d("timeapi", currentTime + "  current time");

        // Get current date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d");
        String currentDate = simpleDateFormat.format(new Date());
        Log.d("timeapi", currentDate + "  current date");

        // Get current day of the week
        SimpleDateFormat currentDay = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = currentDay.format(new Date());
        Log.d("timeapi", dayOfTheWeek + "  current day");

        GlobalData.date= currentDate;
        GlobalData.day= dayOfTheWeek;
        String id = GlobalData.id;
        String time = currentTime;
        String date = selectedDate;
        String day = weekday;
        Log.d("timeapi", date +"  selected date");

        if (day==null){
            day=GlobalData.day;
        }

        if (date==null){
            date= GlobalData.date;
        }

        if (date.equals(currentDate)) {
            GlobalData.currentAndFutureDate = "0";
            Log.d("timeapi", GlobalData.currentAndFutureDate + "    date=current date");
        } else {
            GlobalData.currentAndFutureDate = "1";
            Log.d("timeapi", GlobalData.currentAndFutureDate + "  it's not today's date");
        }

        String today = GlobalData.currentAndFutureDate;

        Call<ResponseTimeSlot> call = ApiClient.getInstance().getApi().timeSlot(id, today,day, time);
        Log.d("responseOfTime", "id = "+id + " 0 is today 1 is tomarrow = " + today +  "day = " + day +" time = " + time);
        call.enqueue(new Callback<ResponseTimeSlot>() {
            @Override
            public void onResponse(Call<ResponseTimeSlot> call, Response<ResponseTimeSlot> response) {
                bookAppointmentTimeProgressBar.setVisibility(View.GONE);
                ResponseTimeSlot timeSlot = response.body();
                if (timeSlot.getCode() == 200) {

                    List<String> emptyList = new ArrayList<>();

                    for (int nIndex = 0; nIndex <= timeSlot.getData().getTimeSlot().size() - 1; nIndex++) {
                        emptyList.add(timeSlot.getData().getTimeSlot().get(nIndex));
                        Log.e("nIndex", String.valueOf(nIndex));
                    }

                    Log.e("listofstring", String.valueOf(emptyList));


                    TimeAdapter mDataAdapter = new TimeAdapter(emptyList);
                    bookAppointmentTimeRecyclerView.setAdapter(mDataAdapter);
                } else {
                    Toast.makeText(getActivity(), "No time slots are available !!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseTimeSlot> call, Throwable t) {
                bookAppointmentTimeProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Time slots is not available for today!!", Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    private void booking() {

        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        String currentTime = sdf.format(new Date());


        String id = GlobalData.id;
        String date = selectedDate;
        Log.e("SimpleDateFormat", date + "  currentDate");
        String time = GlobalData.selectedTime;
        Log.e("SimpleDateFormat", time + "  currentTime");
        String notes = note.getText().toString().trim();
        String user_id = user.getId();
        String service_id = GlobalData.service_id;
        String item_id = itemId;
        String amount = getPrice;

        if (date == null) {
            date= GlobalData.date;
            Log.e("SimpleDateFormat", date + "  currentTime");
        }

        if (time == null) {
            Toast.makeText(getActivity(), "Please select any time slot !!", Toast.LENGTH_SHORT).show();
        } else {
            progress = new ProgressDialog(getActivity());
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            Call<ResponseBooking> call = ApiClient.getInstance().getApi().conformBooking(id, date, time, notes, user_id, service_id, item_id, amount);
            call.enqueue(new Callback<ResponseBooking>() {
                @Override
                public void onResponse(Call<ResponseBooking> call, Response<ResponseBooking> response) {
                    progress.dismiss();
                    ResponseBooking bookingResponse = response.body();
                    try {
                        if (bookingResponse.getCode() == 200) {
                            GlobalData.selectedTime = null;
                            Toast.makeText(getActivity(), "Booked successfully !!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), Success.class);
                            startActivity(intent);
                            getActivity().finish();

                        } else {
                            Toast.makeText(getActivity(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBooking> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}