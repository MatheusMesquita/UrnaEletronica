package br.com.redes.urnaeletronica.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

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
        return mCandidates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCandidates.get(position).getCod_vote();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
