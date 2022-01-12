const socioTitularDiv = document.querySelector(".addSocio");
const socioCategoriaDiv = document.querySelector(".categoriaSocio");
const categoriaSelect = document.querySelector(".categoriaSocioSelect");

const mostrarSocioTitular = ()=>{     
    if (categoriaSelect.options[categoriaSelect.selectedIndex].text === "Grupo Familiar"){
        socioTitularDiv.classList.add("show")
    }
    else if(categoriaSelect.options[categoriaSelect.selectedIndex].text === "Individual"){
        socioTitularDiv.classList.remove("show")
    }
}

categoriaSelect.addEventListener('change', mostrarSocioTitular);
