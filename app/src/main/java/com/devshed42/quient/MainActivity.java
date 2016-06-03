package com.devshed42.quient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    LinearLayout actionLinearLayout;
    TextView actionValue;
    Switch statusSwitch;

    RadioButton vibrateBtn;
    RadioButton silentBtn;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    final String PREF_STATUS = "STATUS";
    final String PREF_ACTION = "ACTION";

    final int VIBRATE = 1;
    final int SILENT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        actionLinearLayout = (LinearLayout) findViewById(R.id.action);
        actionValue = (TextView) findViewById(R.id.actionValue);

        statusSwitch = (Switch) findViewById(R.id.statusSwitch);

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.alert_dialog);

        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.alertRadioGroup);
        vibrateBtn = (RadioButton) dialog.findViewById(R.id.vibrate);
        silentBtn = (RadioButton) dialog.findViewById(R.id.silent);

        statusSwitch.setChecked(preferences.getBoolean(PREF_STATUS, false));

        final Intent intent = new Intent(getApplicationContext(), QuientService.class);

        int actionId = preferences.getInt(PREF_ACTION,1);

        switch (actionId) {
            case 0:
                actionValue.setText(R.string.Silent);
                vibrateBtn.setChecked(false);
                silentBtn.setChecked(true);
                break;
            case 1:
                actionValue.setText(R.string.Vibrate);
                vibrateBtn.setChecked(true);
                silentBtn.setChecked(false);
                break;
            default:
                break;
        }

        if (statusSwitch.isChecked()) {
            getApplicationContext().startService(intent);
        }


        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(LOG_TAG, "Status: " + buttonView.isChecked());
                editor.putBoolean(PREF_STATUS, buttonView.isChecked());
                editor.apply();

                if (buttonView.isChecked()) {
                    // Start service
                    Log.d(LOG_TAG, "Starting Service");

                    getApplicationContext().startService(intent);
                } else {
                    Log.d(LOG_TAG, "Stopping Service");
                    getApplicationContext().stopService(intent);
                }

            }
        });


        actionLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Button Clicked");

                Button dialogBtnOk = (Button) dialog.findViewById(R.id.btnOk);
                Button dialogBtnCancel = (Button) dialog.findViewById(R.id.btnCancel);

                dialogBtnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(LOG_TAG, "Checked radio: " + radioGroup.getCheckedRadioButtonId());
                        Log.d(LOG_TAG, "Vibrate ID: " + R.id.vibrate);
                        Log.d(LOG_TAG, "Silent ID: " + R.id.silent);

                        switch (radioGroup.getCheckedRadioButtonId()) {
                            case R.id.silent:
                                Log.d(LOG_TAG, "Adding pref_action: 0");
                                actionValue.setText(R.string.Silent);
                                editor.putInt(PREF_ACTION,SILENT);
                                editor.apply();
                                break;
                            case R.id.vibrate:
                                Log.d(LOG_TAG, "Adding pref_action: 1");
                                actionValue.setText(R.string.Vibrate);
                                editor.putInt(PREF_ACTION,VIBRATE);
                                editor.apply();
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                });

                dialogBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

}
