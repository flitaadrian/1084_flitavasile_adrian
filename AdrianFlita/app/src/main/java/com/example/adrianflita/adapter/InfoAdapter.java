package com.example.adrianflita.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.adrianflita.R;
import com.example.adrianflita.models.Cont;

import java.util.ArrayList;

public class InfoAdapter  extends ArrayAdapter<Cont> {
    private Activity context;
    private ArrayList<Cont> conts;


    public InfoAdapter(Activity context, ArrayList<Cont> conts) {
        super(context, R.layout.row_cont, conts);
        this.context = context;
        this.conts = conts;
    }


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.row_cont, null, false);

        TextView tip = view.findViewById(R.id.tv_tip);
        TextView comision = view.findViewById(R.id.tv_comision);
        TextView crypto = view.findViewById(R.id.tv_crypto);
        TextView contValuta = view.findViewById(R.id.tv_valuta);

        Cont cont = this.conts.get(position);
        tip.setText("Tip cont: "+cont.getTip());
        comision.setText("Comision: "+cont.getOptiuni().getComision());
        crypto.setText("Permite Crypto: "+(cont.getOptiuni().isCrypto() ? "DA" : "NU"));
        contValuta.setText("Cont Valuta: "+ (cont.getOptiuni().getExtra_pachet().isCont_valuta() ? "DA": "NU"));



        return view;
    }


}
