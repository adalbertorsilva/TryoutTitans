package titans.com.br.tryouttitans.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.Timer;
import java.util.TimerTask;

import titans.com.br.tryouttitans.R;

@EActivity(R.layout.activity_tela_splash)
public class TelaSplashActivity extends AppCompatActivity {

    @AfterViews
    public void timerTelaSplash(){

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();

                Intent intent = new Intent();
                intent.setClass(TelaSplashActivity.this, TelaPrincipal_.class);
                startActivity(intent);
            }
        }, 2000);
    }
}
