const formaPagoInput= document.querySelector("#formaPago");
const detallePagoInput= document.querySelector("#detallePago");

function hiddenModal() {
    const modal = document.querySelector(".modal");
    modal.classList.toggle("show"); //quita la clase show para dejar de mostrar el modal
  }

function validateInputs(){
    if(formaPagoInput.value!="" && detallePagoInput.value!=""){
        hiddenModal();
    }
}