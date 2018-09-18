package com.example.uli_b.turnos;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    //Variables para comunicar los elementos en el xml
    TextView conection;
    ImageButton btn_mail, btn_service,btn_powers,
                btn_sends, btn_copy, btn_acord,
                btn_travel, btn_scan, btn_tax,
                btn_car, btn_forms, btn_money_ord,
                btn_notary, btn_phone, btn_pack,
                btn_photo, btn_loan, btn_temp_car,
                btn_traductor;


    //Variables necesarias para mandar a llamar las herramientas del BLUETOOTH
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    //Variables necesarias para establecer la comunicación entre dispositivos.
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazamos los elementos del xml con sus correspondientes variables.
        conection = (TextView)findViewById(R.id.conexion);
        btn_acord = (ImageButton)findViewById(R.id.acord);
        btn_car = (ImageButton)findViewById(R.id.car);
        btn_copy = (ImageButton)findViewById(R.id.copy);
        btn_forms = (ImageButton)findViewById(R.id.forms);
        btn_loan = (ImageButton)findViewById(R.id.loan);
        btn_mail = (ImageButton)findViewById(R.id.mail);
        btn_money_ord = (ImageButton)findViewById(R.id.moneyOrd);
        btn_notary = (ImageButton)findViewById(R.id.notary);
        btn_pack = (ImageButton)findViewById(R.id.pack);
        btn_phone = (ImageButton)findViewById(R.id.phone);
        btn_photo = (ImageButton)findViewById(R.id.photos);
        btn_powers = (ImageButton)findViewById(R.id.powers);
        btn_scan = (ImageButton)findViewById(R.id.scan);
        btn_sends = (ImageButton)findViewById(R.id.sends);
        btn_service = (ImageButton)findViewById(R.id.service);
        btn_tax = (ImageButton)findViewById(R.id.tax);
        btn_temp_car = (ImageButton)findViewById(R.id.temp_car);
        btn_traductor = (ImageButton)findViewById(R.id.traduction);
        btn_travel = (ImageButton)findViewById(R.id.travel);

        //Mandamos a llamar los metodos findBT() y openBT() para buscar un dispositivo BT y abrir la conexion.
        try {
            findBT();
            openBT();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //Seccion de codigo para conexion web services
    private class InvocarServicioRegistrarAlumnos extends AsyncTask<Void, Integer, Void> {

        private int progreso;
        @Override
        protected Void doInBackground(Void... arg0) {
            registrarServicio();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            //bt1.setClickable(true);
            if(result==null){
                Toast.makeText(MainActivity.this,"Soy null", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"Proceso existoso", Toast.LENGTH_SHORT).show();
                jsonInsertLocal(result);
            }


        }
        @Override
        protected void onPreExecute() {
            progreso = 0;

            // bt1.setClickable(false);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }


    private void registrarServicio() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        //Enviamos los parametros necesarios para el webservice
        String idServicio = "1";
        parameters.put("param1",idServicio);
        // parameters.put("param2", et2.getText().toString());
        //parameters.put("param3", et3.getText().toString());
        String response = "";
        try {

            URL url = new URL("https://ulises261996.000webhostapp.com/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(parameters));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) first = false;
            else result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    private void jsonInsertLocal(Void result)  {



        try {
            JSONArray jsonarray = new JSONArray(result);
            for(int i=0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String idticket       = jsonobject.getString("idticket");
                //String title    = jsonobject.getString("param2");
                //String company  = jsonobject.getString("param3");
                Toast.makeText(MainActivity.this,"Alumno registrado "+idticket, Toast.LENGTH_SHORT).show();

                // lv12.setText(id);
            }

        } catch(JSONException e) {

        }
    }
    //Terminacion de codigo del web services

    //Metodo para encontrar la conexion BT con la impresora.
    void findBT() {

        try {

            //Aqui instanciamos el bluetooth adapter para tener accesso a los metodos.
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            //En este if comprobamos que el dispositivo a utilizar, disponga de un modulo bluetooth para un buen funcionamiento.
            if(mBluetoothAdapter == null) {
                conection.setText("No bluetooth adapter available");
            }

            //Aqui comprobamos que se encuentre activo el bluetooth.
            // En caso de que no esté activo se le mostrara un cuadro de dialogo al usuario con la peticion para activarlo.
            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            //Aqui instanciamos una variable para la manipulacion de dispositivos.
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            //Aqui hacemos la comparacion para saber si existen dispositivos emparejados con bluetooth en nuestro dispositivo.
            if(pairedDevices.size() > 0) {

                //Aqui realizamos una busqueda en el historial de dispositovos emparejados
                for (BluetoothDevice device : pairedDevices) {

                    //Durante la busqueda si se encuentra el nombre de la impresora que deseamos conectar
                    //Se almaenara en la variable mmDevice
                    if (device.getName().equals("RPP300")) {
                        mmDevice = device;
                        break;
                    }
                }
            }

            //Aqui se mandara en el TextView para informar que se encontro la conexion de la impresora
            conection.setText("Bluetooth device found.");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Metodo para abrir la conexion BT.
    void openBT() throws IOException {
        try {

            // Se establece el serial del puerto de servicio estandar paa la ceonexion.
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

            //Se establece el Socket para la conexion y se conecta
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();

            //Declaramos las variables para envia y recivir datos durante la conexion
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            //Llamamos el metodo que estara a la espera de datos
            beginListenForData();

            //Mandamos al TextView para informar que la conexion se  ah abierto.
            conection.setText("Bluetooth Opened");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo utilizado para recibir datos desde otro dispositivo
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                conection.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo para enviar los datos a la impresora
    public void sendData() throws IOException {
        try {

            // the text typed by the user
            //String msg = myTextbox.getText().toString();
            //msg += "\n";

            //mmOutputStream.write(msg.getBytes());

            // tell the user data were sent
            conection.setText("Data sent.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //En caso de que la aplicacion se cierre por completo, se manda a llamar el metodo closeBT().
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            closeBT();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Metodo que sirve para cerar todos los servicios necesarios para la conexion.
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            conection.setText("Bluetooth Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}