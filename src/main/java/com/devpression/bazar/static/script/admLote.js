async function Cadastrarlote() {
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
async function loadCadastrarLote()
{
    const resp = await fetch("lote/CadastroLote.html");
    const html = await resp.text();
    document.getElementById("bodyContent").innerHTML=html
    const resp1 = await fetch("http://localhost:8080/orgaoDonatario", {
        method:"GET",
        headers:{
            "Content-Type": "application/json"
        }
    })
    const data1 = await resp1.text()
    JSON.parse(data1).forEach(element=>{
        let {id,nome,endereco,telefone,horarioFuncionamento,descricao} = element
        let optionOD = document.createElement("option")
        optionOD.setAttribute("value",id)
        optionOD.innerHTML=nome
        document.getElementById("selectOD").append(optionOD)
    })
    const resp2 = await fetch("http://localhost:8080/orgaoFiscalizador", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    const data2 = await resp2.text()
    JSON.parse(data2).forEach(element=> {
        let {id,nome, descricao} = element
        let optionOF = document.createElement("option")
        optionOF.setAttribute("value",id)
        optionOF.innerHTML=nome
        document.getElementById("selectOF").append(optionOF)
    })

}


