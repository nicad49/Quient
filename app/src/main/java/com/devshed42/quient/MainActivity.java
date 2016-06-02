package com.devshed42.quient;

import android.app.Dialog;
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

    LinearLayout actionLinearLayout;
    TextView actionValue;
    Switch statusSwitch;

    RadioButton vibrateBtn;
    RadioButton silentBtn;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    final String PREF_STATUS = "STATUS";
    final String PREF_ACTION = "ACTION";


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

        int actionId = preferences.getInt(PREF_ACTION,1);

        switch (actionId) {
            case 1:
                actionValue.setText(R.string.Vibrate);
                vibrateBtn.setChecked(true);
                silentBtn.setChecked(false);
                break;
            case 2:
                actionValue.setText(R.string.Silent);
                vibrateBtn.setChecked(false);
                silentBtn.setChecked(true);
                break;
            default:
                break;
        }


        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("MainActivity", "Status: " + buttonView.isChecked());
                editor.putBoolean(PREF_STATUS, buttonView.isChecked());
                editor.apply();

            }
        });


        actionLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Button Clicked");

                Button dialogBtnOk = (Button) dialog.findViewById(R.id.btnOk);
                Button dialogBtnCancel = (Button) dialog.findViewById(R.id.btnCancel);

                dialogBtnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("MainActivity", "Checked radio: " + radioGroup.getCheckedRadioButtonId());
                        Log.d("MainActivity", "Vibrate ID: " + R.id.vibrate);
                        Log.d("MainActivity", "Silent ID: " + R.id.silent);

                        switch (radioGroup.getCheckedRadioButtonId()) {
                            case R.id.vibrate:
                                Log.d("MainActivity", "Adding pref_action: 1");
                                actionValue.setText(R.string.Vibrate);
                                editor.putInt(PREF_ACTION,1);
                                editor.apply();
                                break;
                            case R.id.silent:
                                Log.d("MainActivity", "Adding pref_action: 2");
                                actionValue.setText(R.string.Silent);
                                editor.putInt(PREF_ACTION,2);
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
                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
