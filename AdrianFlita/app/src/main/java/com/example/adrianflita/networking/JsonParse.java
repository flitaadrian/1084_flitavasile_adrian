package com.example.adrianflita.networking;

import com.example.adrianflita.models.BankInfo;
import com.example.adrianflita.models.Cont;
import com.example.adrianflita.models.ExtraPachet;
import com.example.adrianflita.models.Optiuni;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParse {

    public static BankInfo getFromJson(String json){

        try{
            JSONObject object = new JSONObject(json);
            String sucursala =  object.getString("sucursala");
            String sediu =  object.getString("sediu");
            String CIF =  object.getString("CIF");
            String telefon =  object.getString("Telefon");
            String fax =  object.getString("Fax");
            List<Cont> conts = getArray(object.getJSONArray("conturi_reclama"));
//            JSONArray array = new JSONArray(json);
//            return getArray(array);
            return new BankInfo(sucursala,sediu,CIF,telefon,fax,conts);
        }catch (JSONException e){
            e.printStackTrace();
        }


        return null;
    }

    private static List<Cont> getArray(JSONArray array) throws JSONException {
        List<Cont> conts = new ArrayList<>();
        for(int i = 0; i < array.length();i++){
            Cont obj = getObject(array.getJSONObject(i));
            conts.add(obj);
        }

        return conts;
    }

    private static Cont getObject(JSONObject object) throws JSONException {

        String tip =  object.getString("tip");
        Optiuni optiuni = getObjectOptiuni(object.getJSONObject("optiuni"));





        return new Cont(tip, optiuni);
    }

    private static Optiuni getObjectOptiuni(JSONObject object) throws JSONException {

        String comision =  object.getString("comision");
        boolean crypto = object.getBoolean("crypto");

        ExtraPachet extraPachet = getObjectExtra(object.getJSONObject("extra_pachet"));




        return new Optiuni(comision,crypto,extraPachet);
    }

    private static ExtraPachet getObjectExtra(JSONObject object) throws JSONException {

        boolean cont_valuta = object.getBoolean("cont_valuta");
        boolean conversie_interbancara = object.getBoolean("conversie_interbancara");






        return new ExtraPachet(cont_valuta,conversie_interbancara);
    }
}