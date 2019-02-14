package com.example.yaya.chatli.db;

public class chatbot {
    private String id;
    private String respuesta; // 回应
    private String url;  //返回的链接

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRespuesta() {
        return this.respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
