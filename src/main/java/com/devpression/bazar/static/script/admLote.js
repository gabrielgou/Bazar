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
async function printCatalogoLote()
{
    console.log("printCatalogoLote")
    let ulLote = document.getElementById("ulLote")
    const resp = await fetch("http://localhost:8080/lote", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    const data = await resp.text()
    ulLote.innerHTML=""
    JSON.parse(data).forEach(element=> {
        let {id, dataEntrega, observacao, idOD, idOF, codigo, orgaoDonatario, orgaoFiscal, produto} = element
        let li = document.createElement("li")
        let obs = document.createElement("p")
        obs.innerHTML="Observação: "
        obs.insertAdjacentHTML("beforeend", observacao)
        console.log(orgaoFiscal.nome)
        li.setAttribute("data-value","OD")
        let div = document.createElement("div")
        let apagar = document.createElement("button")
        apagar.innerHTML="Apagar"
        apagar.setAttribute("onclick", "apagarLote("+id+")")
        apagar.setAttribute("class", "btn btn-primary")
        div.setAttribute("class", "uk-card uk-card-default uk-card-body")
        div.innerHTML="ID: "+id
        let ulOD = document.createElement("ul")
        ulOD.setAttribute("class", "uk-card uk-card-default uk-card-body")
        ulOD.innerHTML = "Orgão Donatário"
        let liOD = document.createElement("li")
        liOD.innerHTML=orgaoDonatario.nome
        let ulOF = document.createElement("ul")
        ulOF.setAttribute("class", "uk-card uk-card-default uk-card-body")
        ulOF.innerHTML = "Orgão Fiscal"
        let liOF = document.createElement("li")
        liOF.innerHTML=orgaoFiscal.nome
        ulOD.appendChild(liOD)
        ulOF.appendChild(liOF)
        let ulP = document.createElement("ul")
        ulP.setAttribute("class", "uk-card uk-card-default uk-card-body")
        //ulP.style["font-size"] = "small"
        ulP.innerHTML = "Produtos"
        let tabela = document.createElement("table")
        tabela.setAttribute("class", "uk-table uk-table-hover")
        let trhead = document.createElement("tr")
        let th1 = document.createElement("th")
        let th2 = document.createElement("th")
        let th3 = document.createElement("th")
        th1.innerHTML = "Código"
        th2.innerHTML = "Nome"
        th3.innerHTML = "Descrição"
        trhead.appendChild(th1)
        trhead.appendChild(th2)
        trhead.appendChild(th3)
        tabela.appendChild(trhead)
        ulP.appendChild(tabela)
        produto.forEach(element2 => {
            let tr = document.createElement("tr")
            let td1 = document.createElement("th")
            let td2 = document.createElement("td")
            let td3 = document.createElement("td")
            td1.innerHTML = element2.codigo
            td2.innerHTML = element2.nome
            td3.innerHTML = element2.descricao
            tr.appendChild(td1)
            tr.appendChild(td2)
            tr.appendChild(td3)
            tabela.appendChild(tr)
        })
        div.appendChild(ulOD)
        div.appendChild(ulOF)
        div.appendChild(ulP)
        ulP.appendChild(obs)
        ulP.appendChild(apagar)
        li.appendChild(div)
        ulLote.appendChild(li)
    })
}

async function loadCatalogoLote()
{

    const resp = await fetch("lote/CatalogoLote.html")
    const html = await resp.text()
    document.getElementById("bodyContent").innerHTML=html
    printCatalogoLote();
}
async function apagarLote(id) {
    if(confirm("Você tem certeza que deseja apagar?")) {
        const resp = await fetch("http://localhost:8080/lote/" + id, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })
        const data = await resp.text()
        alert(data)
        printCatalogoLote()
    }
}


