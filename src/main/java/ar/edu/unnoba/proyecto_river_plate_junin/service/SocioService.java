package ar.edu.unnoba.proyecto_river_plate_junin.service;
import org.springframework.stereotype.Service;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import java.util.List;
@Service
public interface SocioService {
    public List<Socio> getAllSocios();

    public Socio createSocio(Socio socio) throws Exception;
    
    public Socio encontrarSocio(Socio socio);

    public Socio updateSocio(Socio socio);

    public Socio getSocio (Socio socio);

    public List<Socio> buscarEnSocio(String keyword);

    public int contarSocios();

    public int contarSociosActivos();


    
       
    


}
