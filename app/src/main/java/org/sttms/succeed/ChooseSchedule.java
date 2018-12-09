package org.sttms.succeed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.sql.Time;
import java.util.HashMap;

public class ChooseSchedule extends AppCompatActivity {

    private CheckedTextView monCheck, tuesCheck, wedCheck, thursCheck, friCheck, satCheck, sunCheck;
    private LinearLayout monLin, tuesLin, wedLin, thursLin, friLin, satLin, sunLin;
    private boolean[] days = new boolean[7];
    private Button monLeft, monRight, tuesLeft, tuesRight, wedLeft, wedRight, thursLeft, thursRight, friLeft, friRight, satLeft, satRight, sunLeft, sunRight;
    private Button mNext;
    private HashMap<String, Time[]> selectedItems = new HashMap<>();

    public int duration(int hourS, int minS, int hourE, int minE){
        return (hourE * 60 + minE) - (hourS * 60 + minS);
    }

    public boolean check(){
        boolean goneIn = false;
        if(monLin.getVisibility() == View.VISIBLE){
            String[] s = monLeft.getText().toString().split(":"), e = monRight.getText().toString().split(":");
            if(s.length != 2 && e.length != 2)
                return false;
            Time start = new Time(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 0), end = new Time(Integer.parseInt(e[0]), Integer.parseInt(e[1]), 0);
            int elapse = duration(start.getHours(), start.getMinutes(), end.getHours(), end.getMinutes());
            if(elapse < 60)
                return false;
            goneIn = true;
            Time[] times = {start, end};
            selectedItems.put("Monday", times);
        }
        if(tuesLin.getVisibility() == View.VISIBLE){
            String[] s = tuesLeft.getText().toString().split(":"), e = tuesRight.getText().toString().split(":");
            if(s.length != 2 && e.length != 2)
                return false;
            Time start = new Time(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 0), end = new Time(Integer.parseInt(e[0]), Integer.parseInt(e[1]), 0);
            int elapse = duration(start.getHours(), start.getMinutes(), end.getHours(), end.getMinutes());
            if(elapse < 60)
                return false;
            goneIn = true;
            Time[] times = {start, end};
            selectedItems.put("Tuesday", times);
        }
        if(wedLin.getVisibility() == View.VISIBLE){
            String[] s = wedLeft.getText().toString().split(":"), e = wedRight.getText().toString().split(":");
            if(s.length != 2 && e.length != 2)
                return false;
            Time start = new Time(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 0), end = new Time(Integer.parseInt(e[0]), Integer.parseInt(e[1]), 0);
            int elapse = duration(start.getHours(), start.getMinutes(), end.getHours(), end.getMinutes());
            if(elapse < 60)
                return false;
            goneIn = true;
            Time[] times = {start, end};
            selectedItems.put("Wednesday", times);
        }
        if(thursLin.getVisibility() == View.VISIBLE){
            String[] s = thursLeft.getText().toString().split(":"), e = thursRight.getText().toString().split(":");
            if(s.length != 2 && e.length != 2)
                return false;
            Time start = new Time(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 0), end = new Time(Integer.parseInt(e[0]), Integer.parseInt(e[1]), 0);
            int elapse = duration(start.getHours(), start.getMinutes(), end.getHours(), end.getMinutes());
            if(elapse < 60)
                return false;
            goneIn = true;
            Time[] times = {start, end};
            selectedItems.put("Thursday", times);
        }
        if(friLin.getVisibility() == View.VISIBLE){
            String[] s = friLeft.getText().toString().split(":"), e = friRight.getText().toString().split(":");
            if(s.length != 2 && e.length != 2)
                return false;
            Time start = new Time(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 0), end = new Time(Integer.parseInt(e[0]), Integer.parseInt(e[1]), 0);
            int elapse = duration(start.getHours(), start.getMinutes(), end.getHours(), end.getMinutes());
            if(elapse < 60)
                return false;
            goneIn = true;
            Time[] times = {start, end};
            selectedItems.put("Friday", times);
        }
        if(satLin.getVisibility() == View.VISIBLE){
            String[] s = satLeft.getText().toString().split(":"), e = satRight.getText().toString().split(":");
            if(s.length != 2 && e.length != 2)
                return false;
            Time start = new Time(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 0), end = new Time(Integer.parseInt(e[0]), Integer.parseInt(e[1]), 0);
            int elapse = duration(start.getHours(), start.getMinutes(), end.getHours(), end.getMinutes());
            if(elapse < 60)
                return false;
            goneIn = true;
            Time[] times = {start, end};
            selectedItems.put("Saturday", times);
        }
        if(sunLin.getVisibility() == View.VISIBLE){
            String[] s = sunLeft.getText().toString().split(":"), e = sunRight.getText().toString().split(":");
            if(s.length != 2 && e.length != 2)
                return false;
            Time start = new Time(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 0), end = new Time(Integer.parseInt(e[0]), Integer.parseInt(e[1]), 0);
            int elapse = duration(start.getHours(), start.getMinutes(), end.getHours(), end.getMinutes());
            if(elapse < 60)
                return false;
            goneIn = true;
            Time[] times = {start, end};
            selectedItems.put("Sunday", times);
        }
        return goneIn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_schedule);

        for(boolean b : days){
            b = false;
        }

        monCheck = findViewById(R.id.monday);
        tuesCheck = findViewById(R.id.tuesday);
        wedCheck = findViewById(R.id.wedday);
        thursCheck = findViewById(R.id.thursday);
        friCheck = findViewById(R.id.friday);
        satCheck = findViewById(R.id.satday);
        sunCheck = findViewById(R.id.sunday);

        monLin = findViewById(R.id.monlinear);
        tuesLin = findViewById(R.id.tueslinear);
        wedLin = findViewById(R.id.wedlinear);
        thursLin = findViewById(R.id.thurslinear);
        friLin = findViewById(R.id.frilinear);
        satLin = findViewById(R.id.satlinear);
        sunLin = findViewById(R.id.sunlinear);

        monLeft = findViewById(R.id.monleft);
        tuesLeft = findViewById(R.id.tuesleft);
        wedLeft = findViewById(R.id.wedleft);
        thursLeft = findViewById(R.id.thursleft);
        friLeft = findViewById(R.id.frileft);
        satLeft = findViewById(R.id.satleft);
        sunLeft = findViewById(R.id.sunleft);
        monRight = findViewById(R.id.monright);
        tuesRight = findViewById(R.id.tuesright);
        wedRight = findViewById(R.id.wedright);
        thursRight = findViewById(R.id.thursright);
        friRight = findViewById(R.id.friright);
        satRight = findViewById(R.id.satright);
        sunRight = findViewById(R.id.sunright);

        monLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                monLeft.setText(hourOfDay + ":0" + minute);
                            } else {

                                monLeft.setText(hourOfDay + ":" + minute);
                            }
                        }
                    }, 12, 0, false).show();
                }
            }
        });

        monRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                monRight.setText(hourOfDay + ":0" + minute);
                            } else {
                                monRight.setText(hourOfDay + ":" + minute);
                            }
                        }
                    }, 12, 0, false).show();
                }
            }
        });

        tuesLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tuesLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                tuesLeft.setText(hourOfDay + ":0" + minute);
                            } else {
                                tuesLeft.setText(hourOfDay + ":" + minute);
                            }
                        }
                    }, 12, 0, false).show();
                }
            }
        });

        tuesRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tuesLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                tuesRight.setText(hourOfDay + ":0" + minute);
                            } else {
                                tuesRight.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        wedLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wedLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                wedLeft.setText(hourOfDay + ":0" + minute);
                            } else {
                                wedLeft.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        wedRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wedLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                wedRight.setText(hourOfDay + ":0" + minute);
                            } else {
                                wedRight.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        thursLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thursLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                thursLeft.setText(hourOfDay + ":0" + minute);
                            } else {
                                thursLeft.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        thursRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thursLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                thursRight.setText(hourOfDay + ":0" + minute);
                            } else {
                                thursRight.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        friLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                friLeft.setText(hourOfDay + ":0" + minute);
                            } else {
                                friLeft.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        friRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                friRight.setText(hourOfDay + ":0" + minute);
                            } else {
                                friRight.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        satLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(satLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                satLeft.setText(hourOfDay + ":0" + minute);
                            } else {
                                satLeft.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        satRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(satLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                satRight.setText(hourOfDay + ":0" + minute);
                            } else {
                                satRight.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        sunLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sunLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                sunLeft.setText(hourOfDay + ":0" + minute);
                            } else {
                                sunLeft.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        sunRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sunLin.getVisibility() == View.VISIBLE){
                    Log.d("TESTING", "WORKING");
                    new TimePickerDialog(ChooseSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if(minute < 10) {
                                sunRight.setText(hourOfDay + ":0" + minute);
                            } else {
                                sunRight.setText(hourOfDay + ":" + minute);
                            }                        }
                    }, 12, 0, false).show();
                }
            }
        });

        mNext = findViewById(R.id.next_button);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()) {
                    DatabaseReference currentDB = FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getStringExtra("Role") + "s").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    currentDB.child("Schedule").setValue(new Gson().toJson(selectedItems));
                    Intent intent = new Intent(ChooseSchedule.this, FindPlaces.class);
                    intent.putExtra("Role", getIntent().getStringExtra("Role"));
                    intent.putExtra("First", getIntent().getStringExtra("First"));
                    intent.putExtra("Last", getIntent().getStringExtra("Last"));
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(ChooseSchedule.this).setTitle("Try Again")
                            .setMessage("Please make sure your timings are at least 1 hour long and they are all filled in.")
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        tuesCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[2] = !days[2];
                if(tuesCheck.isChecked()){
                    tuesCheck.setChecked(false);
                    tuesLin.setVisibility(View.INVISIBLE);
                } else {
                    tuesCheck.setChecked(true);
                    tuesLin.setVisibility(View.VISIBLE);
                }
            }
        });

        wedCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[3] = !days[3];
                if(wedCheck.isChecked()){
                    wedCheck.setChecked(false);
                    wedLin.setVisibility(View.INVISIBLE);
                } else {
                    wedCheck.setChecked(true);
                    wedLin.setVisibility(View.VISIBLE);
                }
            }
        });

        thursCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[4] = !days[4];
                if(thursCheck.isChecked()){
                    thursCheck.setChecked(false);
                    thursLin.setVisibility(View.INVISIBLE);
                } else {
                    thursCheck.setChecked(true);
                    thursLin.setVisibility(View.VISIBLE);
                }
            }
        });

        friCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[5] = !days[5];
                if(friCheck.isChecked()){
                    friCheck.setChecked(false);
                    friLin.setVisibility(View.INVISIBLE);
                } else {
                    friCheck.setChecked(true);
                    friLin.setVisibility(View.VISIBLE);
                }
            }
        });

        satCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[6] = !days[6];
                if(satCheck.isChecked()){
                    satCheck.setChecked(false);
                    satLin.setVisibility(View.INVISIBLE);
                } else {
                    satCheck.setChecked(true);
                    satLin.setVisibility(View.VISIBLE);
                }
            }
        });

        sunCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[0] = !days[0];
                if(sunCheck.isChecked()){
                    sunCheck.setChecked(false);
                    sunLin.setVisibility(View.INVISIBLE);
                } else {
                    sunCheck.setChecked(true);
                    sunLin.setVisibility(View.VISIBLE);
                }
            }
        });

        monCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[1] = !days[1];
                if(monCheck.isChecked()){
                    monCheck.setChecked(false);
                    monLin.setVisibility(View.INVISIBLE);
                } else {
                    monCheck.setChecked(true);
                    monLin.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}