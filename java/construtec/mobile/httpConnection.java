package construtec.mobile;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class httpConnection {
    private static httpConnection singleton;
    //TODO get this information from user
    public static String serviceIp = "192.168.43.163";
    public static  String port      = "62801";
    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    /**
     * Construtor
     */
    private httpConnection(){}

    /**
     * Returns a sigleton instance of this class
     * @return singleton instance of this class
     */
    public static httpConnection getConnection(){
        if(singleton != null){
            return singleton;
        }
        else{
            return singleton = new httpConnection();
        }
    }

    /**
     * Makes an GET request based on an url, returns the WebService Responce
     * @param url  where request is going to be made
     * @return String with the response
     * @throws Exception
     */
    public String sendGet(String url){
        url = "http://" + serviceIp + ":" + port + "/" + url;
        String respuesta = "";
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            respuesta =  response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Sends a json to the Web Service in order to create a new entity
     * @param url where the request is going to be made
     * @param json to be sent to the WebService
     */
    public void sendPost(String url, String json){
        url = "http://" + serviceIp + ":" + port + "/" + url;
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the value of an user's attribute
     * @param url where the data is to be sent
     * @param id  of object to be changed
     * @param campo field that is going to be changed
     * @param newValue new value to said field
     */
    public void sendPut(String url, String id, String campo, String newValue){
        url = "http://" + serviceIp + ":" + port + "/" + url + "/" + id + "/" + campo + "/" + newValue;
        try {
            RequestBody body = RequestBody.create(null, new byte[0]);
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();
            Response response = client.newCall(request).execute();
            response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendDelete(String url, String id){
        url  = "http://" + serviceIp + ":" + port + "/" + url + "/" + id;
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .delete()
                    .build();
            Response response = client.newCall(request).execute();
            response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
