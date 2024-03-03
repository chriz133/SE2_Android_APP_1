package at.aau.main.application;

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


import java.util.Locale;

import at.aau.main.network.NetworkManager;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private Button btn_send;
    private Button btn_calculate;
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
        btn_send.setOnClickListener(this::btn_send_onClick);
        btn_calculate = findViewById(R.id.btn_calculate);
        btn_calculate.setOnClickListener(this::btn_calculate_onClick);

        networkManager = new NetworkManager();

    }


    private void btn_send_onClick(View v){
        txt_serverAnswer.setText(R.string.server_loading_text);

        String matrikelnummer = getMatriculateNumber();
        if (matrikelnummer == null) return;

        disposable = networkManager.calculateResult(matrikelnummer)
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe(res -> {
                                                txt_serverAnswer.setText(res);
                                              }, throwable -> {
                                                txt_serverAnswer.setText(R.string.server_error_text);
                                   });
    }

    private void btn_calculate_onClick(View v){
        String matrikelnummer = getMatriculateNumber();
        if (matrikelnummer == null) return;

        char[] chars = matrikelnummer.toCharArray();
        int sum = Character.getNumericValue(chars[0]);

        for (int i=1; i<chars.length; i++) {
            int num = Character.getNumericValue(chars[i]);
            if (i % 2 != 0) sum -= num;
            else sum += num;

        }
        txt_serverAnswer.setText(String.format(Locale.GERMAN, "Altern. Quersumme: %d ist %s gerade.", sum, sum % 2 == 0 ? "" : "nicht"));
    }

    private String getMatriculateNumber(){
        String matrikelnummer = edtext_input.getText().toString();
        if (matrikelnummer.isEmpty()) {
            txt_serverAnswer.setText(R.string.empty_number_text);
            return null;
        }
        return matrikelnummer;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}