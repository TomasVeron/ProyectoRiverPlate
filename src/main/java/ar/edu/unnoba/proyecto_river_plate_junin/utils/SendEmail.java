package ar.edu.unnoba.proyecto_river_plate_junin.utils;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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

        // true = text/html
        helper.setText("<h1>Nueva Cuota generada para el socio</h1>" + socio.getNombre() + " "+ socio.getApellido() +" con DNI " + socio.getDni()+"\n"+
                        "<span><span>"
        
        
        , true);

        File cuotaPdf = new File("cuotasPdf/cuota.pdf");
		// hard coded a file path
        FileSystemResource file = new FileSystemResource(cuotaPdf);

        

        helper.addAttachment("cuota.pdf", file);
        
        

        javaMailSender.send(msg);
        
        cuotaPdf.delete();
    }
    

    

}
