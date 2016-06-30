/**
 * Diego Wilde Rodrigues 					7239200
 * Matheus Mesquita Nascimento dos Santos 	8531459
 * Sergio Andrade de Souza        			8531588
 */

package br.com.redes.urnaeletronica.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.redes.urnaeletronica.R;
import br.com.redes.urnaeletronica.adapter.CandidatesAdapter;
import br.com.redes.urnaeletronica.connection.Client;
import br.com.redes.urnaeletronica.models.Candidato;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgCandidate;
    private Spinner mSpCandidates;
    private Button mBtnVote;
    private Button mBtnVoteWhite;
    private Button mBtnVoteNull;

    private Context mContext;

    private List<Candidato> mCandidates;
    private String mJsonArray;

    private MediaPlayer urnSound;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mJsonArray = intent.getStringExtra("json");

            try {
                JSONArray jsonArray = new JSONArray(mJsonArray);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    Candidato candidato = new Candidato();
                    candidato.setCod_vote(jsonObject.getInt("codigo_votacao"));
                    candidato.setName(jsonObject.getString("nome_candidato"));
                    candidato.setParty(jsonObject.getString("partido"));
                    candidato.setVotes(jsonObject.getInt("num_votos"));

                    mCandidates.add(candidato);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, getCandidateNames());
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpCandidates.setAdapter(dataAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mCandidates = new ArrayList<>();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("user-event"));

        generateView();

        getCandidatesList();
    }

    private void getCandidatesList() {
        Client client = new Client(mContext);
        client.execute();
    }

    private void generateView() {
        mSpCandidates = (Spinner) findViewById(R.id.spCandidates);
        mBtnVote = (Button) findViewById(R.id.btnVote);
        mBtnVoteWhite = (Button) findViewById(R.id.btnVoteWhite);
        mBtnVoteNull = (Button) findViewById(R.id.btnVoteNull);

        mBtnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVoteSound();
            }
        });

        mBtnVoteWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVoteSound();
            }
        });

        mBtnVoteNull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVoteSound();
            }
        });
    }


    private List<String> getCandidateNames() {
        List<String> names = new ArrayList<>();
        for (Candidato candidato: mCandidates) {
            names.add(candidato.getName());
        }
        return names;
    }

    private void playVoteSound(){
        urnSound = MediaPlayer.create(this, R.raw.urna);
        urnSound.start();
    }
}