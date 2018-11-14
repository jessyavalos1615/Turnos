package com.example.uli_b.turnos;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class Activity_Doc extends AppCompatActivity {

    String TAG = "Activity_Doc";

    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothSocket mmSocket;
    public BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    public OutputStream mmOutputStream;
    public InputStream mmInputStream;
    public Thread workerThread;

    ImageButton ib_traslate, ib_notary,
            ib_tax, ib_acords,
            ib_powers, ib_permission;

    public byte[] readBuffer;
    public int readBufferPosition;
    public volatile boolean stopWorker;

    ProgressBar progressbar;

    Handler handler;

    LinearLayout layout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__doc);


        ib_traslate = (ImageButton) findViewById(R.id.traslate);
        ib_notary = (ImageButton) findViewById(R.id.notary);
        ib_tax = (ImageButton) findViewById(R.id.tax);
        ib_acords = (ImageButton) findViewById(R.id.acords);
        ib_powers = (ImageButton) findViewById(R.id.powers);
        ib_permission = (ImageButton) findViewById(R.id.permission);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        layout = (LinearLayout) findViewById(R.id.doc);
        final Intent intent = new Intent(Activity_Doc.this, MainActivity.class);
        handler = new Handler();

        try {
            findBT();
            openBT();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressbar.setVisibility(View.INVISIBLE);
                    layout.setVisibility(View.VISIBLE);
                }
            }, 2000);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }


        ib_powers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //14
                    progressbar.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.INVISIBLE);
                    getdata("11", "Poderes y cont.");
                    startActivity(intent);
                    closeBT();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_powers: " + e.getMessage());
                    Toast.makeText(Activity_Doc.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }, 2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //14
                    progressbar.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.INVISIBLE);
                    getdata("11", "Permisos para v.");
                    startActivity(intent);
                    closeBT();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_permission: " + e.getMessage());
                    Toast.makeText(Activity_Doc.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }, 2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_acords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //14
                    progressbar.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.INVISIBLE);
                    getdata("11", "Apostillados y acu.");
                    startActivity(intent);
                    closeBT();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_acords: " + e.getMessage());
                    Toast.makeText(Activity_Doc.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }, 2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_traslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //14
                    progressbar.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.INVISIBLE);
                    getdata("11", "Traducciones");
                    startActivity(intent);
                    closeBT();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_traslate: " + e.getMessage());
                    Toast.makeText(Activity_Doc.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }, 2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ib_notary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Doc.this);
                builder.setMessage("¿Es la primera vez que recurre a nuestro servicio de notaria?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            progressbar.setVisibility(View.VISIBLE);
                            layout.setVisibility(View.INVISIBLE);
                            getdata("12", "Notary");
                            startActivity(intent);
                            closeBT();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(TAG, "ib_notary: " + e.getMessage());
                            Toast.makeText(Activity_Doc.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressbar.setVisibility(View.INVISIBLE);
                                    layout.setVisibility(View.VISIBLE);
                                }
                            }, 2000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            progressbar.setVisibility(View.VISIBLE);
                            layout.setVisibility(View.INVISIBLE);
                            getdata("13", "Notary");
                            startActivity(intent);
                            closeBT();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(TAG, "ib_notary: " + e.getMessage());
                            Toast.makeText(Activity_Doc.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressbar.setVisibility(View.INVISIBLE);
                                    layout.setVisibility(View.VISIBLE);
                                }
                            }, 2000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                /*
                try {
                    //14
                    progressbar.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.INVISIBLE);
                    getdata("14", "Notary public");
                    startActivity(intent);
                    closeBT();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_notary: " + e.getMessage());
                    Toast.makeText(Activity_Doc.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }, 2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });

        ib_tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //14
                    progressbar.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.INVISIBLE);
                    getdata("11", "Income tax");
                    startActivity(intent);
                    closeBT();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ib_tax: " + e.getMessage());
                    Toast.makeText(Activity_Doc.this, "Problem with the conetion.", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }, 2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getdata(String idServicio, String Servicio) throws IOException, JSONException {
        try {
            //el metodo debera recibir el numero del servicio que sera el enviado al web service;
            Log.e(TAG, "Valores que recive del boton: " + idServicio + " " + Servicio);
            String sql = "http://centrodeservicioslatinos.xyz/index2.php/?param1=" + idServicio;
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
                    layout.setVisibility(View.VISIBLE);
                }
            }, 2000);
        }
    }

    public void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
                Toast.makeText(this, "Bluetooth no support device.", Toast.LENGTH_SHORT).show();
            }

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
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
                                                Toast.makeText(Activity_Doc.this, data, Toast.LENGTH_SHORT).show();
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
                    layout.setVisibility(View.VISIBLE);
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
                    layout.setVisibility(View.VISIBLE);
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
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    try {
                        findBT();
                        openBT();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

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
        closeBT();
    }

}
