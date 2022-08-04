async function Cadastrarlote() {
    console.log("CadastrarLote")
    const json={}
    let form = document.getElementById("formCadastroLote");
    let dataForm = new FormData(form);
    let produto=[]
    let codigoPro=[]

    let i=0
    for ([name, value] of dataForm) {
        if(name==="produto")
        {
            let jsonCod={}
            console.log(value)
            produto[i]=value
            jsonCod["codigo"]=value
            codigoPro[i]=jsonCod
            i++
        }
        else{
            json[name] = value
        }
    }
    console.log(codigoPro)
    json["array"]=produto
    json["produto"]=codigoPro
    console.log(json)
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
    const resp3 = await fetch("http://localhost:8080/produto", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    const data3 = await resp3.text()
    JSON.parse(data3).forEach(element=> {
        let {codigo, nome, descricao} = element
        let inputCodigo = document.createElement("input")
        inputCodigo.setAttribute("type","checkbox")
        inputCodigo.setAttribute("name","produto")
        inputCodigo.setAttribute("class", "uk-checkbox")
        inputCodigo.setAttribute("value",codigo)
        let label = document.createElement("label")
        label.append(inputCodigo)
        label.insertAdjacentHTML("beforeend",nome)
        let br = document.createElement("BR")
        let div=document.getElementById("checkboxProduto")
        div.append(label)
        div.insertBefore(br,label)
    })


}


