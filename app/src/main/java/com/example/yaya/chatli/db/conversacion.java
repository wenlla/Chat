package com.example.yaya.chatli.db;

public class conversacion {

    private String id;
    private String preguntas;
    private String respuestas; // 回应


    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id =id;
    }
    public String getPreguntas(){
        return this.preguntas;
    }
    public  void setPreguntas(String preguntas){
        this.preguntas = preguntas;
    }
    public String getRespuestas(){
        return this.respuestas;
    }
    public void setRespuestas(String respuestas){
        this.respuestas = respuestas;
    }
}
