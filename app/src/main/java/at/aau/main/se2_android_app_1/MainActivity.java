package at.aau.main.se2_android_app_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText edtext_input;
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

        edtext_input = findViewById(R.id.edtext_input);
        txt_serverAnswer = findViewById(R.id.txt_serverAnswer);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_send_onClick(v);
            }
        });

        networkManager = new NetworkManager();

    }


    private void btn_send_onClick(View v){
        txt_serverAnswer.setText(R.string.server_loading_text);

        String matrikelnummer = edtext_input.getText().toString();
        if (matrikelnummer.isEmpty()) {
            txt_serverAnswer.setText(R.string.empty_number_text);
            return;
        }
        disposable = networkManager.calculateResult(matrikelnummer)
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe(res -> {
                                                txt_serverAnswer.setText(res);
                                              }, throwable -> {
                                                txt_serverAnswer.setText(R.string.server_error_text);
                                   });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}