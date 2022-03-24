const socioTitularDiv = document.querySelector(".addSocio");
const socioCategoriaDiv = document.querySelector(".categoriaSocio");
const categoriaSelect = document.querySelector(".categoriaSocioSelect");
const socioTitularInput = document.querySelector("#login__titular");
const domicilioDiv = document.querySelector(".domicilio-div");

const mostrarSocioTitular = ()=>{     
    if (categoriaSelect.options[categoriaSelect.selectedIndex].text === "Grupo Familiar"){
        socioTitularDiv.classList.add("show")
    }
    else if(categoriaSelect.options[categoriaSelect.selectedIndex].text === "Individual"){
        socioTitularDiv.classList.remove("show")
        socioTitularInput.value="";
        domicilioDiv.classList.add("show");
    }
}

const mostrarDomicilio =()=>{
    if(categoriaSelect.options[categoriaSelect.selectedIndex].text === "Grupo Familiar"){
        if(socioTitularInput.value==""){
            domicilioDiv.classList.add("show");
        } 
        if(socioTitularInput.value!=""){
            domicilioDiv.classList.remove("show");
        } 
    }
    
}

// socioTitularInput.addEventListener("change", mostrarDomicilio);

categoriaSelect.addEventListener('change', mostrarSocioTitular);
