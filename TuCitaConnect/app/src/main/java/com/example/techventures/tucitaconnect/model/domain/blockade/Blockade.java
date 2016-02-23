package com.example.techventures.tucitaconnect.model.domain.blockade;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.List;

@ParseClassName("Blockade")
public class Blockade extends ParseObject{

    public String getType(){

        return getString(BlockadeAttributes.type);

    }

    public String getDate(){

        return getString(BlockadeAttributes.date);

    }

    public List<String> getDataArray(){

        return (List<String>) get(BlockadeAttributes.dataArray);

    }

    public void putType(String type){

        put(BlockadeAttributes.type, type);

    }

    public void putDate(Date date){

        put(BlockadeAttributes.date, date);

    }

    public void putDataArray(List<String> dataArray){

        addAllUnique(BlockadeAttributes.dataArray, dataArray);

    }

    public static ParseQuery<Blockade> getQuery() {

        return ParseQuery.getQuery(Blockade.class);

    }
}
