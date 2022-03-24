const inputSocioTitular = document.querySelector(".inputSocioTitular");
const categoriaSelect = document.querySelector(".categoriaSocioSelect");
const spanNoSocio = document.querySelector(".spanNoSocio");


const mostrarDomicilio =()=>{
    if (categoriaSelect.options[categoriaSelect.selectedIndex].text === "Grupo Familiar"){
        inputSocioTitular.classList.add("show")
        spanNoSocio.classList.remove("show")
    }
    else if(categoriaSelect.options[categoriaSelect.selectedIndex].text === "Individual"){
        inputSocioTitular.value="";
        inputSocioTitular.classList.remove("show")
        spanNoSocio.classList.add("show")
     }
    
}

categoriaSelect.addEventListener('change',mostrarDomicilio);