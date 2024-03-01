package at.aau.main.se2_android_app_1;

import android.util.Log;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkManager {
    private static final String SERVER_URL = "se2-submission.aau.at";
    private static final Integer SERVER_PORT = 20080;

    public Observable<String> calculateResult(String matrikelnummer) {
        return Observable.fromCallable(() -> {

            Log.d("TEST", "ASDASDASD");
            String result;
            try {
                Socket socket = new Socket(SERVER_URL, SERVER_PORT);

                OutputStream out = socket.getOutputStream();
                out.write(matrikelnummer.getBytes());
                socket.shutdownOutput();
                // Receive result
                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                result = reader.readLine();
                socket.close();
            }catch (IOException e){
                result = e.getCause().getMessage();
                e.printStackTrace();
            }
            return result;
        }).subscribeOn(Schedulers.io());
    }
}
