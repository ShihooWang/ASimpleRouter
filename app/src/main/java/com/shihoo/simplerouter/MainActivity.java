package com.shihoo.simplerouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.shihoo.router_library.ARouter;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.tab1);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.tab1:

                break;
            case R.id.tab2:
                showCoursePacket();
                break;
            case R.id.tab3:
                showMePacket();
                break;
        }
    }

    private void showCoursePacket(){
        ARouter.create(ACourseRouter.class).showCourse();
    }

    private void showMePacket(){
        ARouter.create(AMeRouter.class).showMeView();
    }
}
