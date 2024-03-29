package at.aau.main.network;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class NetworkManager {
    private static final String SERVER_URL = "se2-submission.aau.at";
    private static final Integer SERVER_PORT = 20080;

    public Observable<String> calculateResult(String matrikelnummer) {
        return Observable.fromCallable(() -> {

            String result;
            try {
                Socket socket = new Socket(SERVER_URL, SERVER_PORT);
                socket.setSoTimeout(10 * 1000);

                OutputStream out = socket.getOutputStream();
                out.write(matrikelnummer.getBytes());
                out.write('\n');

                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                result = reader.readLine();
                socket.close();
            }catch (UnknownHostException e){
                result = "Verbindung zum Host kann nicht hergestellt werden! Mit Netzwerk verbunden?";
                e.printStackTrace();
            }catch (IOException e){
                result = Objects.requireNonNull(e.getCause()).getMessage();
                e.printStackTrace();
            }
            return result;
        }).subscribeOn(Schedulers.io());
    }
}
