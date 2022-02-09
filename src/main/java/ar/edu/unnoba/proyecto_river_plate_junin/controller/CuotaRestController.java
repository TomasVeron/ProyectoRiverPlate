package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CuotaRestController {
    

    @RequestMapping(path = "/cuotas/guardarpdf", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void guardarPdf(@RequestPart MultipartFile pdf)throws IOException {
        byte [] bytes = pdf.getBytes();
        DataOutputStream out = new DataOutputStream(new  BufferedOutputStream(new FileOutputStream(new File("cuotasPdf/cuota.pdf"))));
        out.write(bytes);
      
    }
}
