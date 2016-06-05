package com.devshed42.quient;

import android.app.Dialog;
import android.content.Intent;
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
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    LinearLayout actionLinearLayout;
    TextView actionValue;
    Switch statusSwitch;

    RadioButton vibrateBtn;
    RadioButton silentBtn;

    final int VIBRATE = 1;
    final int SILENT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionLinearLayout = (LinearLayout) findViewById(R.id.action);
        actionValue = (TextView) findViewById(R.id.actionValue);

        statusSwitch = (Switch) findViewById(R.id.statusSwitch);
        statusSwitch.setChecked(PreferencesManager.getSavedState(this));

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.alert_dialog);

        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.alertRadioGroup);
        vibrateBtn = (RadioButton) dialog.findViewById(R.id.vibrate);
        silentBtn = (RadioButton) dialog.findViewById(R.id.silent);

        final Intent intent = new Intent(getApplicationContext(), QuientService.class);
        int actionId = PreferencesManager.getSavedAction(this);

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
                PreferencesManager.saveState(MainActivity.this, buttonView.isChecked());

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

                Button dialogBtnOk = (Button) dialog.findViewById(R.id.btnOk);
                Button dialogBtnCancel = (Button) dialog.findViewById(R.id.btnCancel);

                dialogBtnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        switch (radioGroup.getCheckedRadioButtonId()) {
                            case R.id.silent:
                                Log.d(LOG_TAG, "Adding pref_action: 0");
                                actionValue.setText(R.string.Silent);
                                PreferencesManager.saveAction(MainActivity.this, SILENT);
                                if (QuientService.isQuientRunning()) {
                                    Log.d(LOG_TAG, "Quient is Running; Restarting Service");
                                    getApplicationContext().stopService(intent);
                                    getApplicationContext().startService(intent);
                                }
                                break;
                            case R.id.vibrate:
                                Log.d(LOG_TAG, "Adding pref_action: 1");
                                actionValue.setText(R.string.Vibrate);
                                PreferencesManager.saveAction(MainActivity.this, VIBRATE);
                                if (QuientService.isQuientRunning()) {
                                    Log.d(LOG_TAG, "Quient is running; Restarting Service");
                                    getApplicationContext().stopService(intent);
                                    getApplicationContext().startService(intent);
                                }
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
