

function addScript(url) {
    var script = document.createElement('script');
    script.type = 'application/javascript';
    script.src = url;
    document.head.appendChild(script);
}
addScript('https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js');
addScript('https://unpkg.com/axios/dist/axios.min.js');

const pdfInput = document.querySelector("#pdfcuota");
const formPdf = document.querySelector("#formpdf");
const btnPdf = document.querySelector(".pdf-generator");

function text2Binary(string) {
    return string.split('').map(function (char) {
        return char.charCodeAt(0).toString(2);
    }).join(' ');
}

btnPdf.addEventListener("click" ,()=> {
    const $elementoParaConvertir = document.querySelector(".cuota-generar"); // <-- Aquí puedes elegir cualquier elemento del DOM
    html2pdf()
        .set({
            margin: 1,
            filename: 'cuota.pdf',
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
                format: "a4",
                orientation: 'portrait' // landscape o portrait
            }
        })
        .from($elementoParaConvertir)
        .toPdf()
        .output("blob")
        .then( (pdfResult) => {
            const formData= new FormData(formPdf);
            formData.append("pdf", pdfResult);
            console.log(formData);
             axios.post("http://localhost:8080/cuotas/guardarpdf",formData , {
                headers: {'Content-Type': 'multipart/form-data',
                            'responseType': 'arraybuffer'},
            });

        })
        .catch(err => console.log(err));

        const form = new FormData();
});