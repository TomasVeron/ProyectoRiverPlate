package ar.edu.unnoba.proyecto_river_plate_junin.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        
                        "</head>"+
                        "<body>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>DNI</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+cuota.getSocio().getDni()+"</span>"+
                            "</div>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>Nombre</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+cuota.getSocio().getNombre()+"</span>"+
                            "</div>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>Apellido</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+cuota.getSocio().getApellido()+"</span>"+
                            "</div>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>Domicilio</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+cuota.getSocio().getDomicilio()+"</span>"+
                            "</div>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>Codigo</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+cuota.getSocio().getCodigo()+"</span>"+
                            "</div>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>Cuota Mes</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+mesFechaEmision+"</span>"+
                            "</div>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>Fecha Caducidad</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+fechaCaducidadString+"</span>"+
                            "</div>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>Fecha Emision</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+fechaEmisionString+"</span>"+
                            "</div>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>Importe</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+"$"+cuota.getImporte()+"</span>"+
                            "</div>"+
                            "<div class='userDetails-container' style='display:flex;justify-content:space-between;padding:10px;'>"+
                                "<h3 class='userDetails__detailName' style='display:block; padding: 10px;'>Categoria</h3>"+
                                "<span class='userDetails__detailInfo' style='display:block; padding: 10px;'>"+cuota.getCategoria().getNombre()+"</span>"+
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
    

    

}
