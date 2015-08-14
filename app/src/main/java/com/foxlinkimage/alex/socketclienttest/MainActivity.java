package com.foxlinkimage.alex.socketclienttest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Button sendMsg;
    TextView rcvmsg;
    Socket socket = null;
    Handler handler;
    public static final String SERVER_IP = "192.168.56.1";
    public static final int SERVER_PORT = 2345;
    Handler mHandler;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMsg = (Button) findViewById(R.id.sendMsg);
        rcvmsg = (TextView) findViewById(R.id.rcvmsg);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendTask().execute();
            }
        });
    }

    public class SendTask extends AsyncTask<Void, Byte, Void> {
        @Override
        protected void onProgressUpdate(Byte... values) {
            String msg = rcvmsg.getText().toString();
            rcvmsg.setText(msg+ ", " + values[0] );
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                Employee my = new Employee();
                my.name.set("Kai");
                my.year.set(100);
                my.day.set(5);
                byte[] out_buf = new byte[68];
                for (int n = 0; n < 68; n++) {
                    out_buf[n] = my.getByteBuffer().get(n);
                }
                outputStream.write(out_buf);

                //recv
                while (!socket.isClosed()) {
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    byte[] buffer = new byte[input.available()];
                    if (buffer.length != 0) {
                        input.read(buffer);
                        //String msg = new String(buffer, "UTF-8");
                        for(int i=0;i<buffer.length;i++)
                            publishProgress(buffer[i]);
                    }
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
