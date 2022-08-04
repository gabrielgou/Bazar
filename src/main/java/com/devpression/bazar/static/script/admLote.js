async function Cadastrarlote(e) {
    console.log("CadastrarLote")
    const json={}
    let form = document.getElementById("formCadastroLote");
    let dataForm = new FormData(form);
    for ([name, value] of dataForm) {
        json[name] = value
        dataForm.set(name,null)
    }
    const resp = await fetch("http://localhost:8080/lote", {
        method: "POST",
        body: JSON.stringify(json),
        headers:{
            "Content-Type": "application/json"
        }
    })
    const data = await resp.text()
    form.reset()
    alert(data)
}


