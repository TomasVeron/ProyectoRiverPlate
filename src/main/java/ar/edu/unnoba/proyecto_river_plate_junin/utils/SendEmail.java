package ar.edu.unnoba.proyecto_river_plate_junin.utils;


import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
@Component
public class SendEmail {

    @Autowired
    private JavaMailSender javaMailSender;


    public void enviarEmailCuotas(Socio socio, Cuota cuota) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		
        helper.setTo(socio.getEmail());

        helper.setSubject("Club River Plate Junin - Cuota");

        // default = text/plain
        //helper.setText("Check attachment for image!");
        DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        Calendar fechaEmision = Calendar.getInstance();
        fechaEmision.setTime(cuota.getFechaCreacion());
        Calendar fechaCaducidad = Calendar.getInstance();
        fechaCaducidad.setTime(cuota.getFechaCaducidad());
        
        String fechaEmisionString = formatter.format(fechaEmision.getTime());
        String fechaCaducidadString = formatter.format(fechaCaducidad.getTime());
        int mesFechaEmision = fechaEmision.get(Calendar.MONTH) + 1;

        


        String html = "<html xmlns:th='http://www.thymeleaf.org'>"+
                        "<head>"+
                                "<style type='text/css'>"+
                                ".userDetails-container{"+
                                    "display: flex;align-items:center;padding: 10px;border: 1px solid}"+
                            "</style>"+
                            "<style type='text/css'>"+
                                ".userDetails__detailInfo{"+
                                    "width:100%; text-align: right;"+
                            "</style>"+
                            "<style type='text/css'>"+
                                ".userTittle{"+
                                    "display: flex;align-items:center;"+
                            "</style>"+
                            "<style type='text/css'>"+
                                ".userTittle span{"+
                                    "width:100%"+
                            "</style>"+
                            "<style type='text/css'>"+
                                ".logo-container{"+
                                    "width: 130px;height: 130px;"+
                            "</style>"+
                            "<style type='text/css'>"+
                                ".logo{"+
                                    "object-fit: cover;"+
                            "</style>"+
                        "</head>"+
                        "<body>"+
                            "<h1 class='userTittle' style='display: flex;align-items:center;'>"+
                                "<span style='width:100%'>Cuota</span>"+
                                "<div class='logo-container' style='width: 130px;height: 130px;'>"+
                                "<img class='logo' style='object-fit: cover;width: inherit;' src='https://scontent.fjni3-1.fna.fbcdn.net/v/t1.18169-9/10629586_780258542038653_1108998330630031778_n.jpg?_nc_cat=105&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=aOgwzj-hCfMAX_hGbDZ&_nc_ht=scontent.fjni3-1.fna&oh=00_AT96uzKbQe5mV_Q40q-_lzzSllgLnAhmBt-9UJ0wme06YQ&oe=622D6C40' alt='' />"+
                                "</div>"+
                            "</h1>"+
                            "<div class='userDetails-container'>"+
                                "<h3 class='userDetails__detailName'>DNI</h3>"+
                                "<p class='userDetails__detailInfo'style='width:100%; text-align: right;'>"+cuota.getSocio().getDni()+"</p>"+
                            "</div>"+
                            "<div class='userDetails-container'>"+
                                "<h3 class='userDetails__detailName'>Nombre</h3>"+
                                "<p class='userDetails__detailInfo'style='width:100%; text-align: right;'>"+cuota.getSocio().getNombre()+"</p>"+
                            "</div>"+
                            "<div class='userDetails-container' >"+
                                "<h3 class='userDetails__detailName' >Apellido</h3>"+
                                "<p class='userDetails__detailInfo' style='width:100%; text-align: right;'>"+cuota.getSocio().getApellido()+"</p>"+
                            "</div>"+
                            "<div class='userDetails-container' >"+
                                "<h3 class='userDetails__detailName' >Domicilio</h3>"+
                                "<p class='userDetails__detailInfo' style='width:100%; text-align: right;'>"+cuota.getSocio().getDomicilio()+"</p>"+
                            "</div>"+
                            "<div class='userDetails-container' >"+
                                "<h3 class='userDetails__detailName' >Codigo</h3>"+
                                "<p class='userDetails__detailInfo' style='width:100%; text-align: right;'>"+cuota.getSocio().getCodigo()+"</p>"+
                            "</div>"+
                            "<div class='userDetails-container'>"+
                                "<h3 class='userDetails__detailName' >Cuota Mes</h3>"+
                                "<p class='userDetails__detailInfo' style='width:100%; text-align: right;'>"+mesFechaEmision+"</p>"+
                            "</div>"+
                            "<div class='userDetails-container' >"+
                                "<h3 class='userDetails__detailName' >Fecha Caducidad</h3>"+
                                "<p class='userDetails__detailInfo'style='width:100%; text-align: right;' >"+fechaCaducidadString+"</p>"+
                            "</div>"+
                            "<div class='userDetails-container' >"+
                                "<h3 class='userDetails__detailName' >Fecha Emision</h3>"+
                                "<p class='userDetails__detailInfo'style='width:100%; text-align: right;' >"+fechaEmisionString+"</p>"+
                            "</div>"+
                            "<div class='userDetails-container' >"+
                                "<h3 class='userDetails__detailName' >Importe</h3>"+
                                "<p class='userDetails__detailInfo' style='width:100%; text-align: right;' >"+"$"+cuota.getImporte()+"</p>"+
                            "</div>"+
                            "<div class='userDetails-container'>"+
                                "<h3 class='userDetails__detailName' >Categoria</h3>"+
                                "<p class='userDetails__detailInfo'style='width:100%; text-align: right;' >"+cuota.getCategoria().getNombre()+"</p>"+
                            "</div>"+
                        "</body>"+
                    "</html>";

        // File style = new File("src/main/resources/static/css/cuotaPdf.css");
		// hard coded a file path
        // FileSystemResource file = new FileSystemResource(style);
        // true = text/html
        // helper.addAttachment("style.css", file);
        helper.setText(html, true);


        // File cuotaPdf = new File("cuotasPdf/cuota.pdf");
		// // hard coded a file path
        // FileSystemResource file = new FileSystemResource(cuotaPdf);

        

        // helper.addAttachment("cuota.pdf", file);
        
        

        javaMailSender.send(msg);
        
        // cuotaPdf.delete();
    }

    public void enviarRecibo(Socio socio, Cuota cuota) throws MessagingException{
        MimeMessage msg = javaMailSender.createMimeMessage();
        try{
            

            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            
            helper.setTo(socio.getEmail());

            DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            Calendar fechaEmision = Calendar.getInstance();
            fechaEmision.setTime(cuota.getFechaCreacion());
            String fechaEmisionString = formatter.format(fechaEmision.getTime());

            helper.setSubject("Club River Plate Junin - Recibo de Pago - fecha: " + fechaEmisionString);
            helper.setText("Se envia adjunto a este mail el recibo en pdf");
            // hard coded a file path
            FileSystemResource file = new FileSystemResource("src/main/resources/recibos/recibo.pdf");
            helper.addAttachment("recibo.pdf",file);
            
        }catch(Exception e){
            System.out.println("ha ocurrido un error......");
        }
        javaMailSender.send(msg);
        File recibo = new File("src/main/resources/recibos/recibo.pdf");
        recibo.delete();




    }
    

    

}
