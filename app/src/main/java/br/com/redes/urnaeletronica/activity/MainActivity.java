package br.com.redes.urnaeletronica.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.redes.urnaeletronica.R;
import br.com.redes.urnaeletronica.adapter.CandidatesAdapter;
import br.com.redes.urnaeletronica.models.Candidato;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgCandidate;
    private Spinner mSpCandates;
    private Button mBtnVote;
    private Button mBtnVoteWhite;
    private Button mBtnVoteNull;

    private List<Candidato> mCandidates;

    private MediaPlayer urnSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCandidates = new ArrayList<>();

        generateView();
    }

    private void generateView() {
        mSpCandates = (Spinner) findViewById(R.id.spCandidates);
        mBtnVote = (Button) findViewById(R.id.btnVote);
        mBtnVoteWhite = (Button) findViewById(R.id.btnVoteWhite);
        mBtnVoteNull = (Button) findViewById(R.id.btnVoteNull);

        mSpCandates.setAdapter(new CandidatesAdapter(this, mCandidates));

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

    private void playVoteSound(){
        urnSound = MediaPlayer.create(this, R.raw.urna);
        urnSound.start();
    }
}
