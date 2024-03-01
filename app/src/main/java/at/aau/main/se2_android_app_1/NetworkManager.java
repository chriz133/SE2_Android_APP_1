package at.aau.main.se2_android_app_1;

import android.util.Log;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkManager {
    private static final String SERVER_URL = "se2-submission.aau.at";
    private static final Integer SERVER_PORT = 20080;

    public Observable<String> calculateResult(byte[] matrikelnummerBytes) {
        return Observable.fromCallable(() -> {

            Log.d("TEST", "ASDASDASD");
            String result;
            try {

                Socket socket = new Socket(SERVER_URL, SERVER_PORT);
//                socket.setSoTimeout(10 * 1000);
                // Send Matrikelnummer
                OutputStream out = socket.getOutputStream();
                out.write(matrikelnummerBytes, 0, matrikelnummerBytes.length);

                // Receive result
//                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                result = "SUCCESS";
                socket.close();
            }catch (Exception e){
                result = "ERROR";
                e.printStackTrace();
            }
            return result;
        }).subscribeOn(Schedulers.io());
    }
}
