package parser;


import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.JSONFunctions;
import model.DataStationMeteo;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

import java.nio.file.Path;
import java.util.ArrayList;

public class TestReader {

    private String filePath;
    private ArrayList<DataStationMeteo> resultRead;

    public TestReader(String filePath) {
        this.filePath = filePath;
        resultRead = new ArrayList<>();
    }


    public ArrayList<JSONObject> getData() {
        String currentLine;
        FileReader fr;
        BufferedReader br;

        try {
            fr = new FileReader(this.filePath);
            br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null){
                //System.out.println(currentLine + " whiled");

                if (currentLine.indexOf(0) != '#') {
                    String[] splitLine = currentLine.split(",");
                    if (splitLine.length >= 2){
                        if (splitLine[0].length() > 1){

                            DataStationMeteo station = readStationLine(br, splitLine);
                           if (station != null) {
                               resultRead.add(station);
                           }
                        } else {
                            // TODO: 29/08/18 Error ville 1 seule lettre
                        }
                    } else {
                        // TODO: 29/08/18 Error ligne + de 2 elements
                    }
                }
            }
            //System.out.println(resultRead.get(2).getData());
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<JSONObject> outList = new ArrayList<>();
        for (DataStationMeteo stationMeteo: resultRead
             ) {
            outList.add(stationMeteo.toJSON());
        }


        return outList;
    }

    private DataStationMeteo readStationLine(BufferedReader br, String[] splitLine){
        DataStationMeteo dataStationMeteo = new DataStationMeteo();
        dataStationMeteo.setVille(splitLine[0]);

        int lim = Integer.parseInt(splitLine[1]);

        for (int i = 0; i < lim; i++){
            try {
                switch (readMesure(br.readLine(), dataStationMeteo, i)){
                    case 0:
                        continue;
                    case 1:
                        // TODO: 29/08/18 Error
                        continue;
                    case 2:
                        // readMesure rencontre une ville = erreur dans le nombre de mesures

                        return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return dataStationMeteo;
    }

    private int readMesure(String ligne, DataStationMeteo obj, int index){
        String[] strings;
        strings = ligne.split(",");
        JSONArray mesures;
        JSONObject data;

        //System.out.println(ligne + "readed");
        if (obj.getData() != null) {
            data = new JSONObject(obj.getData());
        } else {
            data = new JSONObject();
        }
        if (data.has("mesures")) {
            mesures = data.getJSONArray("mesures");
        }
        else {
            mesures = new JSONArray();
        }

        switch (strings[0].toUpperCase()) {
            case "P" :
                if (strings.length == 4) {
                    JSONObject mesure = new JSONObject();
                    mesure.put("value", strings[3]);
                    mesure.put("date", strings[2]);
                    mesure.put("unite", strings[1]);
                    mesures.put(index, mesure);
                } else {
                    return 1;
                }
                break;
            case "T":
                if (strings.length == 3) {
                    JSONObject mesure = new JSONObject();
                    mesure.put("value", strings[2]);
                    mesure.put("unite", strings[1]);
                    mesures.put(index, mesure);
                } else {
                    return 1;
                }
                break;
            case "H":
                if (strings.length == 2) {
                    JSONObject mesure = new JSONObject();
                    mesure.put("value", strings[1]);
                    mesure.put("unite", "%");
                    mesures.put(index, mesure);
                } else {
                    return 1;
                }
                break;
            default:
                if (strings[0].length() > 1){
                    return 2;
                }
                break;
        }
        data.put("mesures", mesures);
        obj.setData(data.toString());
       // System.out.println(data);
        return 0;
    }

}
