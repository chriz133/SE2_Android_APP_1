package at.aau.main.se2_android_app_1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private Button btn_send;
    private TextView txt_serverAnswer;
    private NetworkManager networkManager;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_serverAnswer = (TextView) findViewById(R.id.txt_serverAnswer);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_send_onClick(v);
            }
        });

        networkManager = new NetworkManager();

    }


    private void btn_send_onClick(View v){
        txt_serverAnswer.setText("WAITING FOR RESPONSE...");

        String matrikelnummer = "12203544";
        disposable = networkManager.calculateResult(matrikelnummer)
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe(res -> {
                                                txt_serverAnswer.setText(res);
                                              }, throwable -> {
                                                txt_serverAnswer.setText("Fehler bei der Kommunikation mit dem Server");
                                                Log.e("ERROR", "Fehler bei der Kommunikation mit dem Server", throwable);
                                   });
    }
}