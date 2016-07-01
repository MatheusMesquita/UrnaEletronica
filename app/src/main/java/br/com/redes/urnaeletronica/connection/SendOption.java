package br.com.redes.urnaeletronica.connection;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Matheus Mesquita on 29-Jun-16.
 */
public class SendOption extends AsyncTask<Void, Void, Void> {

    private Socket mSocket;
    private String mOption;

    public SendOption(String option){
        mOption = option;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            SocketAddress socketAddress = new InetSocketAddress(Utils.SVADDRESS, Utils.PORT);
            mSocket = new Socket();
            mSocket.connect(socketAddress, 5000);

            OutputStreamWriter out = new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8");
            out.write(mOption+"\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
        super.onPostExecute(result);
        System.out.println("OPTION " + mOption + " SENT");
    }
}