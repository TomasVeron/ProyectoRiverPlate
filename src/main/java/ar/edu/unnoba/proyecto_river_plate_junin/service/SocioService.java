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

    public Long contarSocios();

    public int contarSociosActivos();

    public int contarSociosGf();
    public int contarSociosInd();
    public int contarSociosTitulares();

    public void actualizarGrupoFamiliar(boolean habilitado,String domicilio, String codigo);

    public Socio consultarSocioTitular(String socioTitular);

    public boolean titularHabilitado(Long id);

    public List<Socio> getFamiliares(Long id);

    public List<Socio> getSocioNoDependientes();
    public List<Socio> getSocioNoDependientesActivos();

    public List<Socio> buscarSociosNoDependientes(String keyword);

   /* public boolean validacionEdadMinima(Date fechaNacimiento, int edadMinima);*/
}
