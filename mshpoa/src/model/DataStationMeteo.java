package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataStationMeteo {

    private String ville, data;
    private int id;
    private static int counter = 0;

    public DataStationMeteo() {
        this.id = counter++;
    }

    public DataStationMeteo(JSONObject object){
        setVille(object.get("ville").toString());
        setData(object.get("data").toString());
    }

public static ArrayList<DataStationMeteo> initFromListJson(ArrayList<JSONObject> jsonObjects){
        ArrayList<DataStationMeteo> listOut = new ArrayList<>();
    for (JSONObject js :
            jsonObjects) {
        listOut.add(new DataStationMeteo(js));
    }
    return listOut;
}

    public int getId() {
        return id;
    }


    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public JSONObject toJSON(){
        JSONObject out = new JSONObject();
        out.put("data", getData());
        out.put("ville", getVille());
        out.put("id", getId());
        return out;
    }

    public ArrayList<Mesure> getMesures(){
        ArrayList<Mesure> out = new ArrayList<>();
        JSONArray jsonArray = new JSONObject(getData()).getJSONArray("mesures");
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            out.add(new Mesure(jsonObject.get("unite").toString(), jsonObject.get("value").toString()));
        }
        //System.out.println(out);
        return out;
    }

}
