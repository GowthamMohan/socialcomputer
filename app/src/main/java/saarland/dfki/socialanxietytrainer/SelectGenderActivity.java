package saarland.dfki.socialanxietytrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectGenderActivity extends AppCompatActivity {

    private RadioButton rb_female;
    private RadioButton rb_male;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
        rb_male = (RadioButton) findViewById(R.id.rb_female);
        radioGroup = (RadioGroup) findViewById(R.id.select_gender_radioGroup);
    }

    public void saveGender(View v) {
        if(radioGroup.getCheckedRadioButtonId()== R.id.rb_female){
            Preferences.Companion.setGender(this,getString(R.string.gender_name_female));
            Intent intent = new Intent(SelectGenderActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else  if (radioGroup.getCheckedRadioButtonId() == R.id.rb_male){
            Preferences.Companion.setGender(this,getString(R.string.gender_name_male));
            Intent intent = new Intent(SelectGenderActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
