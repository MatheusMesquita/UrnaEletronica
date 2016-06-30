package br.com.redes.urnaeletronica.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.redes.urnaeletronica.R;
import br.com.redes.urnaeletronica.models.Candidato;

/**
 * Created by Matheus Mesquita on 18-Jun-16.
 */
public class CandidatesAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Candidato> mCandidates;

    public CandidatesAdapter(Context context, List<Candidato> candidates) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCandidates = candidates;
    }

    @Override
    public int getCount() {
        return mCandidates.size();
    }

    @Override
    public Object getItem(int position) {
        return mCandidates.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return mCandidates.get(position).getCod_vote();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_main, null);
            holder.spNames = (Spinner) convertView.findViewById(R.id.spCandidates);

        } else
            holder = (Holder) convertView.getTag();

        convertView.setTag(holder);

        return convertView;
    }

    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (Candidato candidato: mCandidates) {
            names.add(candidato.getName());
        }
        return names;
    }

    public class Holder{
        Spinner spNames;
    }
}
