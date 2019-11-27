package com.example.fitnesscrew.gui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;

public class AsyncTaskRabbitMQ extends AsyncTask<Integer, Integer, Integer> {

    private Context context;
    private ProgressDialog progressDialog;

    public AsyncTaskRabbitMQ(Context context) {
        this.context = context;
        execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Html.fromHtml("Enviando registros ..."));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();
    }


    @Override
    protected Integer doInBackground(Integer... integers) {

        try {

            Thread.sleep(2000);

            RabbitMQ.sendRabbitMQMessage(
                    "ExchangeFitnessCrew",
                    "QueueFitnessCrew",
                    "BindFitnesCrew" ,
                    "Teste");

            return 1;
        }catch (Exception e) {
            System.err.println("Erro ao enviar mensagem RabbitMQ: " + e.getMessage());
            return 0;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        progressDialog.dismiss();
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        if (integer == 1) {

            alert.setMessage("Mensagem enviada ao RabbitMQ");
            alert.setNeutralButton("OK", null);
            alert.create();
            alert.show();

        } else {

            alert.setMessage("Erro ao enviar mensagem ao RabbitMQ");
            alert.setNeutralButton("OK", null);
            alert.create();
            alert.show();

        }


    }
}
