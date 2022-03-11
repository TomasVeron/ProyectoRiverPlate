package ar.edu.unnoba.proyecto_river_plate_junin.service;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.SocioRepository;
import ar.edu.unnoba.proyecto_river_plate_junin.utils.DateManager;

@Service
public class SocioServiceImp implements SocioService{

@Autowired
private DateManager dateManager;

    @Autowired
    private SocioRepository repository;

    @Override
    public List<Socio> getAllSocios() {
        return repository.filterSocios();
    }

    public Socio encontrarSocioTitular(String socioTitular) throws Exception{
        Socio titular = repository.encontrarSocioTitular(socioTitular);
        if(titular==null){
            throw new Exception("Socio Titular ingresado no existe!!");
        }
        if(!titular.isTitular()){
            throw new Exception("El codigo de socio ingresado no pertenece a un socio titular!!");
        }
        return titular;
    }

    /*@Override
    public boolean validacionEdadMinima(Date fechaNacimiento, int edadMinima){
        Calendar fechaEdad = Calendar.getInstance();
        fechaEdad.setTime(fechaNacimiento);
        fechaEdad.add(Calendar.YEAR, edadMinima);
        Date fechaActual = new Date();

        if(fechaEdad.getTime().after(fechaActual)){
            return false; 
        }
        else{
            return true;
        }
    }*/

    @Override
    public Socio createSocio(Socio socio, String socioTitular) throws Exception {
         String codigo = String.valueOf(contarSocios() + 1L) ;
        socio.setCodigo(codigo);
        Date fechaHoy = new Date(); 
        if(dateManager.desdeMayorQueHasta(socio.getFechaNacimiento(),fechaHoy)) throw new Exception("la fecha de nacimiento debe ser antes de la fecha actual");
    
        if(socio.getDni().equals(repository.findByDni(socio.getDni()))){
            throw new Exception("el dni de socio no esta disponible");
        }
        if(!socioTitular.equals("") && socio.isCategoriaGrupoFamiliar()){
            Socio socioTi = encontrarSocioTitular(socioTitular);
            if(repository.contarSociosGrupoFamiliar(socioTi.getId()) >= 4 ){
                throw new Exception("Se ha superado el limite del grupo familiar");
            }
            socio.setDomicilio(socioTi.getDomicilio());
            socio.setSocioTitular(socioTi);
        }
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
        
        Socio uDB = repository.findById(socio.getId()).orElse(null);//Socio uDB (anterior), Socio socio ( nuevo )

        //INDIVIDUAL O TITULAR
        if(uDB.getSocioTitular()==null){
            if(uDB.isCategoriaGrupoFamiliar()){//TITULAR
                if(socio.isCategoriaIndividual()){//TITULAR -> INDIVIDUAL
                    uDB.setCategoria(socio.getCategoria());
                    actualizarGrupoFamiliar(false,socio.getDomicilio(), uDB.getCodigo());//ACTUALIZA A LA FAMILIAR EN ESTADO INACTIVO
                }
                else if(!socio.getCodigoSocioTitular().equals("")){//TITULAR -> DEPENDIENTE
                    if(repository.contarSociosGrupoFamiliar(socio.getId())>0){
                            throw new Exception("El socio titular no puede pasar a ser dependiente de otro socio porque tiene socios familiares");
                        }
                    Socio titular = encontrarSocioTitular(socio.getCodigoSocioTitular());
                    actualizarGrupoFamiliar(false,socio.getDomicilio(), uDB.getCodigo());//ACTUALIZA A LA FAMILIAR EN ESTADO INACTIVO
                    uDB.setSocioTitular(titular);
                }
                if(socio.getEstado()!=uDB.getEstado() || socio.getDomicilio().equals(uDB.getDomicilio())==false){//CAMBIO EL ESTADO O EL DOMICILIO
                    actualizarGrupoFamiliar(socio.getEstado(),socio.getDomicilio(), uDB.getCodigo());
                }
            }
            if(uDB.isCategoriaIndividual()){//INDIVIDUAL
                if(socio.isCategoriaGrupoFamiliar()){//INDIVIDUAL -> TITULAR
                    uDB.setCategoria(socio.getCategoria());
                    actualizarGrupoFamiliar(socio.getEstado(),socio.getDomicilio(), uDB.getCodigo());
                    if(!socio.getCodigoSocioTitular().equals("")){//INDIVIDUAL -> DEPENDIENTE
                        Socio titular = encontrarSocioTitular(socio.getCodigoSocioTitular());
                        uDB.setSocioTitular(titular);
                    }
                }
            }
        uDB.setDomicilio(socio.getDomicilio()); //SETEA EL DOMICIOLIO ANTES PORQUE SI ES DEPENDIENTE LLEGA NULO
        //SOCIO DEPENDIENTE
        }else if(uDB.getSocioTitular()!=null){
            if(socio.isCategoriaGrupoFamiliar()){//DEPENDIENTE -> INDIVIDUAL
                uDB.setSocioTitular(null);
                uDB.setCategoria(socio.getCategoria());
                
            }else if(socio.getCodigoSocioTitular().equals("")){//DEPENDIENTE -> TITULAR
                uDB.setSocioTitular(null);
            }
        }
        uDB.setEstado(socio.getEstado());
        uDB.setTelefono(socio.getTelefono());
        uDB.setEmail(socio.getEmail());
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
    public Long contarSocios() {
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
    public int contarSociosTitulares() {
        return repository.contarSociosTitulares();
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
    public void actualizarGrupoFamiliar(boolean habilitado,String domicilio,  String codigo) {
        Long idSocio = repository.obtenerIdSocio(codigo);
        repository.actualizarGrupoFamiliar(habilitado,domicilio, idSocio);
    }



    @Override
    @Transactional(readOnly = true)
    public boolean titularHabilitado(Long id) {
        Socio socioTitular = repository.findById(id).orElse(null);
        if (socioTitular.getEstado() == true && socioTitular.isCategoriaGrupoFamiliar()) {
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

    @Override
    public List<Socio> buscarSociosNoDependientes(String keyword) {
        return repository.buscarSociosNoDependientes(keyword);
    }
}
