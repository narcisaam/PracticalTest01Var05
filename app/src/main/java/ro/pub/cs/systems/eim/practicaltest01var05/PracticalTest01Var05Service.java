package ro.pub.cs.systems.eim.practicaltest01var05;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Date;

public class PracticalTest01Var05Service extends Service {
    private ProcessingThread processingThread = null;

    protected class ProcessingThread extends Thread {

        private boolean isRunning = true;
        private Integer sum;
        ProcessingThread(int sum) {
            this.sum = sum;
        }

        @Override
        public void run() {

            Intent broadcastIntent = new Intent();

            while (isRunning) {
                broadcastIntent.setAction("BROADCAST");
                broadcastIntent.putExtra("message", new Date(System.currentTimeMillis()) + " " + sum);
                sendBroadcast(broadcastIntent);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }

        protected void stopThread() {
            isRunning = false;
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        processingThread = new ProcessingThread(intent.getIntExtra("SUMA", -1));
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
