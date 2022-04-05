function hiddenModal() {
    const modal = document.querySelector("#errorModal");
    if(modal.classList.contains('show')){
      modal.classList.remove('show'); 
    }else{
      modal.classList.add('show'); //quita la clase show para dejar de mostrar el modal
    }
    
  }

  function hiddenModalRecibo() {
    const modal = document.querySelector("#reciboModal");
    if(modal.classList.contains('show')){
      modal.classList.remove('show'); 
    }else{
      modal.classList.add('show'); //quita la clase show para dejar de mostrar el modal
    }
    
  }