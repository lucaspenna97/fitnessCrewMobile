package com.example.fitnesscrew.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;

import com.example.fitnesscrew.bean.User;
import com.example.fitnesscrew.gui.ProcessResults;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender extends AsyncTask<Integer, Integer, Integer>{

    private Context context;
    private User user;
    private ProgressDialog progressDialog;


    public MailSender(Context context, User user) {
        this.context = context;
        this.user = user;
        execute();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Html.fromHtml("Enviado e-mail, aguarde um momento."));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected Integer doInBackground(Integer... integers) {

        try {

            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("lucaspenna97@gmail.com","zonk02031997");
                }
            });
            session.setDebug(false);


            Message message = new MimeMessage(session);

            //Remetente
            message.setFrom(new InternetAddress("lucaspenna97@gmail.com"));

            //Destinatarios
            Address[] destinatarios = InternetAddress.parse(user.getEmail());
            message.setRecipients(Message.RecipientType.TO, destinatarios);

            //Assunto
            message.setSubject("Fitness Crew - Resumo Resultados");

            //Conteudo
            message.setText("Olá " + user.getNome() + "!\n" +
                    "Aqui esta um resumo dos resultados obtidos atraves do aplicativo mobile: \n" +
                    "Índice de Massa Corporal: " + ProcessResults.calculateImc(user.getPeso(), user.getAltura()) + "\n" +
                    "Grau de Acordo com o IMC: " + ProcessResults.calculateGrauImc(Integer.parseInt(ProcessResults.calculateImc(user.getPeso(), user.getAltura()))) + "\n" +
                    "Resumo: " + ProcessResults.resumoDescricao(Integer.parseInt(ProcessResults.calculateImc(user.getPeso(), user.getAltura()))) + "\n" +
                    "Taxa Metabólica Basal:" + ProcessResults.calculateTmb(Integer.parseInt(user.getPeso()), Integer.parseInt(user.getAltura()), Integer.parseInt(user.getIdade()), user.getSexo()));

            Transport.send(message);
            return 1;

        } catch (Exception e){
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
            return -1;
        }

    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        progressDialog.dismiss();

        if (integer == 1) {
            AlertDialog.Builder mensagemEnviada = new AlertDialog.Builder(context);
            mensagemEnviada.setMessage(Html.fromHtml("E-mail encaminhado para <b>" + user.getEmail() + "</b> ."));
            mensagemEnviada.setNeutralButton("OK", null);
            mensagemEnviada.show();
        } else {
            AlertDialog.Builder envioFalhou = new AlertDialog.Builder(context);
            envioFalhou.setMessage(Html.fromHtml("Não conseguimos enviar o e-mail, por favor verifique a <b>conexão de rede</b> e tente novamente ."));
            envioFalhou.setNeutralButton("OK", null);
            envioFalhou.show();
        }

    }

}