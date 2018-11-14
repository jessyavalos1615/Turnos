package com.example.uli_b.turnos;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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

    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothSocket mmSocket;
    public BluetoothDevice mmDevice, device;

    // needed for communication to bluetooth device / network
    public OutputStream mmOutputStream;
    public InputStream mmInputStream;
    public Thread workerThread;

    Handler handler;

    public byte[] readBuffer;
    public int readBufferPosition;
    public volatile boolean stopWorker;

    ImageButton ib_mail, ib_services, ib_sends, ib_copy,
            ib_scan, ib_passport, ib_forms, ib_fax,
            ib_phone, ib_money, ib_other, ib_info,
            ib_doc;

    Intent enableBluetooth;

    String sql;

    ProgressBar progressbar;

    int REQUEST_CODE = 1;

    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib_mail = (ImageButton) findViewById(R.id.mail);
        ib_services = (ImageButton) findViewById(R.id.services);
        ib_sends = (ImageButton) findViewById(R.id.sends);
        ib_copy = (ImageButton) findViewById(R.id.copy);
        ib_scan = (ImageButton) findViewById(R.id.scan);
        ib_passport = (ImageButton) findViewById(R.id.passport);
        ib_forms = (ImageButton) findViewById(R.id.forms);
        ib_phone = (ImageButton) findViewById(R.id.phone);
        ib_doc = (ImageButton) findViewById(R.id.doc);
        ib_other = (ImageButton) findViewById(R.id.others);
        ib_info = (ImageButton) findViewById(R.id.info);
        ib_fax = (ImageButton) findViewById(R.id.fax);
        ib_money = (ImageButton) findViewById(R.id.money);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        scrollView = (ScrollView) findViewById(R.id.land_scroll);
        handler = new Handler();

        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver1, filter1);

        IntentFilter filter3 = new IntentFilter();
        filter3.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter3.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mBroadcastReceiver3, filter3);

        try {
            progressbar.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
            findBT();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        openBT();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progressbar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: " + e.getMessage());
        }

        ib_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //5
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("5", "Correo Electronico");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_mail: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_money.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    //4
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("4", "Money Orders");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_money: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //2
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("2", "Recargas ");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_phone: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_fax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //6
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("6", "Fax publico");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_fax: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_forms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //7
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("7", "Llenado de formas");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_forms: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //9
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("9", "Passaport");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_passport: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //8
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("8", "Escaneo doc.");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_scan: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        ib_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //15
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("15", "Centro de copiado ");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_copy: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_sends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //1
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("1", "Envios de dinero ");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_sends: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //3
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("3", "Pago servicios");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_services: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
                if (mmSocket.isConnected()) {
                    closeBT();
                }

                Intent intent = new Intent(MainActivity.this, Activity_OtherS.class);
                startActivity(intent);
            }
        });

        ib_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
                if (mmSocket.isConnected()) {
                    closeBT();
                }
                closeBT();
                Intent intent = new Intent(MainActivity.this, Activity_Doc.class);
                startActivity(intent);

            }
        });

        ib_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //10
                    progressbar.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    getdata("10", "Informacion");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_info: " + e.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Seccion de codigo para conexion web services
    public void getdata(String idServicio, String Servicio) throws IOException, JSONException {
        //el metodo debera recibir el numero del servicio que sera el enviado al web service;
        Log.e(TAG, "Valores que recive del boton: " + idServicio + " " + Servicio);
        if (mmDevice.getName().equals("tecycom1")) {
            sql = "http://centrodeservicioslatinos.xyz/index2.php/?param1=" + idServicio;
        } else {
            sql = "http://centrodeservicioslatinos.xyz/index.php/?param1=" + idServicio;
        }
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = null;
            HttpURLConnection conn;

            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = " ";

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            json = response.toString();
            Log.e(TAG, "json: " + json);
            JSONArray jsonArr = null;
            jsonArr = new JSONArray(json);
            Log.e(TAG, "json array: " + jsonArr);
            //en el for se recore el array de datos en este caso el array solo contiene un dato. y con al instruccion
            //jsonObject.optString(""); adentro del parentesis debera ponerse el nombre del elemento del json
            //para ver la estructura del json colocar la direccion web usada anteriormente en el navegador
            //como recive un parametro debera agregarse
            //asi: http://ulisescardenas78.xyz/index.php/?param1=1
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String Departamento;
                if (jsonObject.get("idServicio").equals("1")) {
                    Departamento = "Caja ";
                } else {
                    Departamento = "Escritorio ";
                }
                Log.e(TAG, jsonObject.getString("idticket"));
                sendData((String) jsonObject.get("idticket"), Servicio, Departamento);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Data don´t send", Toast.LENGTH_SHORT).show();
            Log.e("getdata", e.getMessage());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressbar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }, 2000);
        }
    }

    //Terminacion de codigo del web services
    public void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
                Toast.makeText(this, "Bluetooth no support device.", Toast.LENGTH_SHORT).show();
            }

            if (!mBluetoothAdapter.isEnabled()) {
                enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, REQUEST_CODE);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals("tecycom1") || device.getName().equals("cslatinos")) {
                        mmDevice = device;
                        Log.e(TAG, "Find BT: " + device.getName());
                        break;
                    } else {

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Find BT: " + e.getMessage());
        }
    }

    public void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Open BT: " + e.getMessage());
            Toast.makeText(this, "Bluetooth not conected.", Toast.LENGTH_SHORT).show();
        }
    }

    public void beginListenForData() {
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

    public void sendData(String num, String Servicio, String Departamento) throws IOException {

        try {


            String msg2 =
                    "! 0 200 100 1 1\n" +
                            "IN-INCHES\n" +
                            "T 8 0 0 0 ****************\n" +

                            "IN-DOTS\n" +
                            "T 4 0 0 0 ****************\n" +
                            "T 4 0 0 13    CS latinos\n" +
                            "T 4 0  0 50  " + Servicio + "\n" +
                            "T 4 0  0 51 ______________ \n" +
                            "T 4 0 0 90  " + Departamento + "\n" +
                            "T 4 0 0 130 turno: " + num + " \n" +
                            "T 4 0 0 160 ****************\n" +
                            "PRINT\n";

            mmOutputStream.write(msg2.getBytes());

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressbar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }, 2000);

            //mmOutputStream.write(msg.getBytes());

            //tell the user data were sent
            //Toast.makeText(this, "Data sent.", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Send data: " + e.getMessage());
            Toast.makeText(this, "Data doesn't sent.", Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressbar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }, 3000);
        }
    }

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        findBT();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        break;
                    case BluetoothAdapter.STATE_ON:
                        try {
                            openBT();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:

                        break;
                }
            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    Toast.makeText(context, "BT is conected", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    Toast.makeText(context, "BT is disconnected.", Toast.LENGTH_SHORT).show();
                    closeBT();
                    findBT();
                    try {
                        openBT();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (requestCode == RESULT_CANCELED) {
                enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, REQUEST_CODE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.syncr:
                closeBT();
                findBT();
                try {
                    openBT();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void closeBT() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
        unregisterReceiver(mBroadcastReceiver3);
    }
}