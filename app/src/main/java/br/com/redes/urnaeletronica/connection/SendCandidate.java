package br.com.redes.urnaeletronica.connection;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import br.com.redes.urnaeletronica.models.Vote;

/**
 * Created by Matheus Mesquita on 29-Jun-16.
 */
public class SendCandidate extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private Socket mSocket;
    private Vote mVote;

    public SendCandidate(Context context, Vote vote) {
        mContext = context;
        mVote = vote;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            SocketAddress socketAddress = new InetSocketAddress(Utils.SVADDRESS, Utils.PORT);
            mSocket = new Socket();
            mSocket.connect(socketAddress, 5000);

            Gson gson = new Gson();

            OutputStreamWriter out = new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8");
            out.write(gson.toJson(mVote)+"\n");
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
        Toast.makeText(mContext, "Voto computado", Toast.LENGTH_LONG).show();
    }
}