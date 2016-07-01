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
import android.widget.AdapterView;
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
import br.com.redes.urnaeletronica.connection.ReceiveCandidates;
import br.com.redes.urnaeletronica.connection.SendCandidate;
import br.com.redes.urnaeletronica.connection.SendOption;
import br.com.redes.urnaeletronica.models.Candidato;
import br.com.redes.urnaeletronica.models.Vote;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgCandidate;
    private Spinner mSpCandidates;
    private Button mBtnVote;
    private Button mBtnVoteWhite;
    private Button mBtnVoteNull;

    private Context mContext;

    private List<Candidato> mCandidates;
    private int mChoosenCandidate;
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
                    candidato.setCodigo_votacao(jsonObject.getInt("codigo_votacao"));
                    candidato.setNome_candidato(jsonObject.getString("nome_candidato"));
                    candidato.setPartido(jsonObject.getString("partido"));
                    candidato.setNum_votos(jsonObject.getInt("num_votos"));

                    mCandidates.add(candidato);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, getCandidatesNames());
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

        sendOption("999");

        getCandidatesList();
    }

    private void sendOption(String option) {
        SendOption op = new SendOption(option);
        op.execute();
    }

    private void getCandidatesList() {
        ReceiveCandidates receiveCandidates = new ReceiveCandidates(mContext);
        receiveCandidates.execute();
    }

    private void generateView() {
        mSpCandidates = (Spinner) findViewById(R.id.spCandidates);
        mBtnVote = (Button) findViewById(R.id.btnVote);
        mBtnVoteWhite = (Button) findViewById(R.id.btnVoteWhite);
        mBtnVoteNull = (Button) findViewById(R.id.btnVoteNull);

        mSpCandidates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mChoosenCandidate = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBtnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVote("normal");
                playVoteSound();
            }
        });

        mBtnVoteWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVote("white");
                playVoteSound();
            }
        });

        mBtnVoteNull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVote("nullVote");
                playVoteSound();
            }
        });
    }

    private void sendVote(String voteType) {
        sendOption("888");
        Vote vote;

        switch (voteType){
            case("normal"):
                vote = new Vote(mCandidates.get(mChoosenCandidate), voteType);
                break;
            case("white"):
                vote = new Vote(null, voteType);
                break;
            default:
                vote = new Vote(null, voteType);
                break;
        }
        SendCandidate sendCandidate = new SendCandidate(this, vote);
        sendCandidate.execute();
    }

    private List<String> getCandidatesNames() {
        List<String> names = new ArrayList<>();
        for (Candidato candidato: mCandidates) {
            names.add(candidato.getNome_candidato());
        }
        return names;
    }

    private void playVoteSound(){
        urnSound = MediaPlayer.create(this, R.raw.urna);
        urnSound.start();
    }
}
