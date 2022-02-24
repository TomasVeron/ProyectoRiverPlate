

function addScript(url) {
    var script = document.createElement('script');
    script.type = 'application/javascript';
    script.src = url;
    document.head.appendChild(script);
}
addScript('https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js');
addScript('https://unpkg.com/axios/dist/axios.min.js');

// const pdfInput = document.querySelector("#pdfcuota");
const formPdf = document.querySelector("#formpdf");
const btnPdf = document.querySelector(".pdf-generator");
const socioDni = document.querySelector(".socio-dni");
const reciboFecha = document.querySelector(".fecha-creacion");

function registerPayment(){
    const modal = document.querySelector(".confirm");
    modal.classList.toggle("active");
}


btnPdf.addEventListener("click" ,()=> {
    const $elementoParaConvertir = document.querySelector(".recibo-pdf"); // <-- Aquí puedes elegir cualquier elemento del DOM
    html2pdf()
        .set({
            margin: 1,
            filename: `Recibo-DNI_${socioDni.value}-CuotaMes_${reciboFecha.value}.pdf`,
            image: {
                type: 'jpeg',
                quality: 0.98
            },
            html2canvas: {
                scale: 3, // A mayor escala, mejores gráficos, pero más peso
                letterRendering: true,
            },
            jsPDF: {
                unit: "in",
                format: "a5",
                orientation: 'landscape' // landscape o portrait
            }
        })
        .from($elementoParaConvertir)
        .toPdf()
        .save()
        .output("blob")
        .then( (pdfResult) => {
            const formData= new FormData(formPdf);
            formData.append("pdf", pdfResult);
            console.log(formData);
             axios.post("http://localhost:8080/cuotas/generarRecibo",formData , {
                headers: {'Content-Type': 'multipart/form-data',
                            'responseType': 'arraybuffer'},
            });

        })
        .catch(err => console.log(err));

        registerPayment();
});