package ar.edu.unnoba.proyecto_river_plate_junin.service;
import org.springframework.stereotype.Service;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import java.util.List;
@Service
public interface SocioService {
    public List<Socio> getAllSocios();

    public Socio createSocio(Socio socio, String socioTitular) throws Exception;
    
    public Socio encontrarSocio(Socio socio);

    public Socio updateSocio(Socio socio) throws Exception; 

    public Socio getSocio (Socio socio);

    public Socio getSocio (Long socioid);

    public List<Socio> buscarEnSocio(String keyword);

    public int contarSocios();

    public int contarSociosActivos();

    public int contarSociosGf();
    public int contarSociosInd();

    public void actualizarGrupoFamiliar(boolean habilitado, String codigo);

    public Socio consultarSocioTitular(String socioTitular);

    public boolean titularHabilitado(Long id);

    public List<Socio> getFamiliares(Long id);

    public List<Socio> getSocioNoDependientes();





    
       
    


}
