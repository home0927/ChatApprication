package com.jmj.chatapprication;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private EditText mInputMessage;
    private Button mSendMessage;
    private LinearLayout mMessageLog;
    private TextView mCpuMessage;
    private TextView mUserMessage;
    private TextView mUserMessage_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale locale = Locale.US;
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        getResources().updateConfiguration(config,null);

        mInputMessage = (EditText)     findViewById(R.id.input_message);
        mSendMessage =  (Button)       findViewById(R.id.send_message);
        mMessageLog =   (LinearLayout) findViewById(R.id.message_log);
        //mCpuMessage =   (TextView)     findViewById(R.id.cpu_message);
        //mUserMessage =  (TextView)     findViewById(R.id.user_message);
        //mSendMessage.setOnClickListener(this);

    }
    /*
    @Override
    public void sendMessage(View v){
        if(v.equals(mSendMessage)){
            Log.d("디버그","Toast를 실행한다.");
            Toast.makeText(this, "onClick()", Toast.LENGTH_SHORT).show();

            String inputTest = mInputMessage.getText().toString();
            mUserMessage.setText(inputTest);
            mCpuMessage.setText("그거 괜찮아요");
            mInputMessage.setText("");
        }

    }
    */
    public void sendMessage(View v){
        String inputText = mInputMessage.getText().toString();
        String lowerInputText = inputText.toLowerCase();
        String answer;


        //메시지 크기에 맞춤
        LinearLayout.LayoutParams userMessageLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //마진설정 (메소드 안에서 final로 선언하면, 클래스 맴버변수로 선언되어 메소드가 종료되어도 살아 있는 것이다)
        //즉 메소드 실행시마다 변수를 재성하는 것이 아니라, 기존 변수를 재활용하는 것이다.
        final int marginSize = getResources().getDimensionPixelSize(R.dimen.message_margin);
        final int messageColor = getResources().getColor(R.color.message_color);

        mUserMessage = new TextView(this);
        mUserMessage.setTextColor(messageColor);
        mUserMessage.setBackgroundResource(R.drawable.user_message);
        mUserMessage.setText(inputText);
        //mUserMessage.setGravity(Gravity.END);

        userMessageLayout.gravity = Gravity.END;
        userMessageLayout.setMargins(0,marginSize,0,marginSize);
        mMessageLog.addView(mUserMessage,0,userMessageLayout);


        if(lowerInputText.contains(getString(R.string.how_are_you))){
            answer = getString(R.string.fine);
        }else if(lowerInputText.contains(getString(R.string.forturn))) {
            double random = Math.random() * 5.1d;
            if (random < 1d) {
                answer = getString(R.string.worst_luck);
            } else if (random < 2d) {
                answer = getString(R.string.bad_luck);
            } else if (random < 3d) {
                answer = getString(R.string.good_luck);
            } else if (random < 4d) {
                answer = getString(R.string.nice_luck);
            } else if (random < 5d) {
                answer = getString(R.string.best_luck);
            } else {
                answer = getString(R.string.amazing_luck);
            }
        }else if(lowerInputText.contains(getString(R.string.time))) {
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            answer = getString(R.string.time_format, hour, minute,second);

        }else{
            answer = getString(R.string.good);
        }
        mCpuMessage = new TextView(this);
        mCpuMessage.setTextColor(messageColor);
        mCpuMessage.setBackgroundResource(R.drawable.cpu_message);
        mCpuMessage.setText(answer);
        //mMessageLog.addView(mCpuMessage,0);
        //mCpuMessage.setGravity(Gravity.START);
        mInputMessage.setText("");

        //mCpuMessage.setText(answer);
        TranslateAnimation userMassageAnimation =
                new TranslateAnimation(mMessageLog.getWidth(),0,0,0);
        userMassageAnimation.setDuration(1000);
                userMassageAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        //메시지 크기에 맞춤
                        LinearLayout.LayoutParams cpuMessageLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        cpuMessageLayout.gravity = Gravity.START;
                        mMessageLog.addView(mCpuMessage,0, cpuMessageLayout);
                        TranslateAnimation cpuAnimation =
                                new TranslateAnimation(-mMessageLog.getWidth(),0,0,0);
                        cpuAnimation.setDuration(1000);
                        mCpuMessage.setAnimation(cpuAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
        mUserMessage.startAnimation(userMassageAnimation);

    }
}
