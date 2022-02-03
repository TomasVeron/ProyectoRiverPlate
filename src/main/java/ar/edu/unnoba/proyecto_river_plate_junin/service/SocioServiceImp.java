package ar.edu.unnoba.proyecto_river_plate_junin.service;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.SocioRepository;

@Service
public class SocioServiceImp implements SocioService{

    @Autowired
    private SocioRepository repository;

    @Override
    public List<Socio> getAllSocios() {
        return repository.filterSocios();
    }

    public Socio encontrarSocioTitular(String socioTitular){
        return repository.encontrarSocioTitular(socioTitular);
    }

    @Override
    public Socio createSocio(Socio socio, String socioTitular) throws Exception {
        
        String codigo = String.valueOf(repository.contarSocios() + 1) ;
        socio.setCodigo(codigo);
        if(socio.getDni()==repository.findByDni(socio.getDni())){
            throw new Exception("el dni de socio no esta disponible");
        }
        if(!socioTitular.equals("")){
            if(encontrarSocioTitular(socioTitular) == null){
                throw new Exception("Socio Titular ingresado no existe!!");
            }
        }
        Socio socioTi =encontrarSocioTitular(socioTitular);
        socio.setSocioTitular(socioTi);
        socio.setFechaAlta(new Date());
        socio.setEstado(true);
        
        return  repository.save(socio);
    }

    @Override
    @Transactional(readOnly = true)
    public Socio encontrarSocio(Socio socio){
        return repository.findById(socio.getId()).orElse(null); 
    }

    @Override
    @Transactional
    public Socio updateSocio(Socio socio) throws Exception { 
        Socio uDB = repository.findById(socio.getId()).orElse(null);
        if(socio.getSocioTitular() != null){//socio dependiente
            if(socio.getCategoria().getNombre().equals("Individual")){
                uDB.setSocioTitular(null);
            }
            else if (socio.getEstado() == true && titularHabilitado(socio.getSocioTitular().getId()) == false) {
                    throw new Exception("No se puede habilitar un familiar cuando el titular esta inactivo");
            }
            if(socio.getCodigoSocioTitular().equals(uDB.getSocioTitular().getCodigo())==false){//verifica si hubo un cambio de titular
                if(encontrarSocioTitular(socio.getCodigoSocioTitular())==null){
                    throw new Exception("Socio Titular ingresado no existe!!");
                }
                Socio titular = encontrarSocioTitular(socio.getCodigoSocioTitular());
                uDB.setSocioTitular(titular); 
            }
        }
        if(socio.getSocioTitular() == null){// socio titular o Individual
            if(socio.getCategoria().getId()==1 && socio.getEstado()!=uDB.getEstado()){//actualiza a la familia si es socio titular y si hubo un cambio de estado   
                actualizarGrupoFamiliar(socio.getEstado(), socio.getCodigo());
            }
            if(socio.getCategoria().getNombre().equals("Individual") && uDB.getCategoria().getId()==1){
                actualizarGrupoFamiliar(false, socio.getCodigo());
            }
        }
        uDB.setEmail(socio.getEmail());
        uDB.setDomicilio(socio.getDomicilio());
        uDB.setTelefono(socio.getTelefono());
        uDB.setEstado(socio.getEstado());
        uDB.setCategoria(socio.getCategoria());
        return repository.save(uDB);
    }

    public Socio getSocio (Socio socio) {
        return repository.findById(socio.getId()).orElse(null); 
    
    }


    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarEnSocio(String keyword){
        return repository.searchSocios(keyword);
   }


    @Override
    public int contarSocios() {
        return repository.contarSocios();
    }


    @Override
    public int contarSociosActivos() {
        return repository.contarSociosActivos();
    }


    @Override
    public int contarSociosGf() {
        return repository.contarSociosGf();
    }


    @Override
    public int contarSociosInd() {
        return repository.contarSociosInd();
    }


    @Override
    public Socio consultarSocioTitular(String socioTitular) {
        return repository.encontrarSocioTitular(socioTitular);
    }


    @Override
    @Transactional
    public void actualizarGrupoFamiliar(boolean habilitado, String codigo) {
        Long idSocio = repository.obtenerIdSocio(codigo);
        repository.actualizarGrupoFamiliar(habilitado, idSocio);
    }


    @Override
    @Transactional(readOnly = true)
    public boolean titularHabilitado(Long id) {
        Socio socioTitular = repository.findById(id).orElse(null);
        if (socioTitular.getEstado() == true && socioTitular.getCategoria().getId()==1) {
            return true;
        }
        return false;
    }

    @Override
    public List<Socio> getFamiliares(Long id) {
        return repository.getFamiliares(id);
    }

    @Override
    public List<Socio> getSocioNoDependientes() {
        return repository.getSociosNoDependientes();
    }

    @Override
    public Socio getSocio(Long socioId) {
        return repository.getSocio(socioId);
    }
}
