package ar.edu.unnoba.proyecto_river_plate_junin.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
@Component
public class SendEmail {

    @Autowired
    private JavaMailSender javaMailSender;


    public void enviarEmailCuotas(Socio socio) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(socio.getEmail());

        msg.setSubject("Cuota");
        msg.setText("Se ha generado una nueva cuota para el socio"+socio.getNombre()+ " " + socio.getApellido());

        javaMailSender.send(msg);
    }
    

    

}
