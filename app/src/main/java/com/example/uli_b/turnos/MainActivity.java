package com.example.uli_b.turnos;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    ImageButton ib_mail, ib_services, ib_sends, ib_copy,
                ib_scan, ib_passport, ib_forms, ib_fax,
                ib_phone, ib_money, ib_other, ib_info,
                ib_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib_mail = (ImageButton)findViewById(R.id.mail);       ib_services = (ImageButton)findViewById(R.id.services);
        ib_sends = (ImageButton)findViewById(R.id.sends);     ib_copy = (ImageButton)findViewById(R.id.copy);
        ib_scan = (ImageButton)findViewById(R.id.scan);       ib_passport = (ImageButton)findViewById(R.id.passport);
        ib_forms = (ImageButton)findViewById(R.id.forms);     ib_phone = (ImageButton)findViewById(R.id.phone);
        ib_doc = (ImageButton)findViewById(R.id.doc);         ib_other = (ImageButton)findViewById(R.id.others);
        ib_info = (ImageButton)findViewById(R.id.info);       ib_fax = (ImageButton)findViewById(R.id.fax);
        ib_money = (ImageButton)findViewById(R.id.money);

        try {
            findBT();
            openBT();
        }catch(IOException e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: " + e.getMessage());
        }

        ib_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendData();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_mail: " + e.getMessage());
                }
            }
        });

        ib_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_fax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_forms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_sends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_Doc.class);
                startActivity(intent);
            }
        });

        ib_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    //Seccion de codigo para conexion web services
    public void getdata(String param) throws IOException, JSONException {
        //el metodo debera recibir el numero del servicio que sera el enviado al web service;
        String sql="http://ulisescardenas78.xyz/index.php/?param1="+param;
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url=null;
        HttpURLConnection conn;

        url=new URL(sql);
        conn=(HttpURLConnection) url.openConnection();

        BufferedReader in =new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;

        StringBuffer response=new StringBuffer();

        String json=" ";

        while ((inputLine=in.readLine())!= null){
            response.append(inputLine);
        }

        json=response.toString();

        JSONArray jsonArr=null;
        jsonArr=new JSONArray(json);
        //en el for se recore el array de datos en este caso el array solo contiene un dato. y con al instruccion
        //jsonObject.optString(""); adentro del parentesis debera ponerse el nombre del elemento del json
        //para ver la estructura del json colocar la direccion web usada anteriormente en el navegador
        //como recive un parametro debera agregarse
        //asi: http://ulisescardenas78.xyz/index.php/?param1=1
        for (int i=0;i<jsonArr.length();i++){
            JSONObject jsonObject=jsonArr.getJSONObject(i);
        }
    }
    //Terminacion de codigo del web services
    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                Toast.makeText(this, "Bluetooth no support device.", Toast.LENGTH_SHORT).show();
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals("clatinos") || device.getName().equals("tecycom1")) {
                        mmDevice = device;
                        Log.e(TAG, "Find BT: "+ device.getName());
                        break;
                    }else{

                    }
                }
            }

            Toast.makeText(this, "Bluetooth device found.", Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            e.printStackTrace();
            Log.e(TAG, "Find BT: " + e.getMessage());
        }
    }
    void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            Toast.makeText(this, "Bluetooth Opened", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Open BT: " + e.getMessage());
            Toast.makeText(this, "Bluetooth not conected.", Toast.LENGTH_SHORT).show();
        }
    }
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
                                                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
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
            Log.e(TAG, "For data: " + e.getMessage());
        }
    }
    public void sendData() throws IOException {
        try {

            // the text typed by the user
            String msg = "Hola";
            msg += "\n";

            mmOutputStream.write(msg.getBytes());

            // tell the user data were sent
            //Toast.makeText(this, "Data sent.", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Send data: " + e.getMessage());
            Toast.makeText(this, "Data doesn't sent.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            closeBT();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "onDestroy: " + e.getMessage());
        }
    }
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            Toast.makeText(this, "Bluetooth Closed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Close BT " + e.getMessage());
        }
    }
}