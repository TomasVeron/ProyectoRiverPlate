package ar.edu.unnoba.proyecto_river_plate_junin.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
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

		// hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        // helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);
    }
    

    

}
