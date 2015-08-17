package com.foxlinkimage.alex.socketclienttest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends AppCompatActivity {
    Button sendMsg;
    TextView rcvmsg;
    Socket socket = null;
    public static final String SERVER_IP = "192.168.56.1";
    public static final int SERVER_PORT = 2345;

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

    public class SendTask extends AsyncTask<Void, byte[], Void> {
        @Override
        protected void onProgressUpdate(byte[]... values) {
            Employee objEmployee = new Employee();
            ByteBuffer bytebuffer= ByteBuffer.wrap(values[0]);
            bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
            objEmployee.setByteBuffer(bytebuffer, 0);
            String name = objEmployee.name.get();
            int year = objEmployee.year.get();
            int day = objEmployee.day.get();
            rcvmsg.setText("The return value shown as below\n name=" + name + ", year=" + String.valueOf(year) + ", day=" + String.valueOf(day));
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
                        publishProgress(buffer);
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
