package br.com.redes.urnaeletronica.connection;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import br.com.redes.urnaeletronica.activity.MainActivity;

/**
 * Created by Matheus Mesquita on 29-Jun-16.
 */
public class ReceiveCandidates extends AsyncTask<Void, Void, Void> {

    private Socket mSocket;
    String response;
    private Context mContext;

    public ReceiveCandidates(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            SocketAddress socketAddress = new InetSocketAddress(Utils.SVADDRESS, Utils.PORT);
            mSocket = new Socket();
            mSocket.connect(socketAddress, 5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            response = in.readLine();
            System.out.println(response);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (mSocket != null) {
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Intent intent = new Intent("user-event");
        intent.putExtra("json", response);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        super.onPostExecute(result);
    }

}