async function printCatalogoLoteCliente()
{
    console.log("printCatalogoLote")
    let ulLote = document.getElementById("ulLote")
    let inputSearch = document.getElementById("inputSearch")
    ulLote.innerHTML=""
    if(inputSearch.value==""){
    const resp = await fetch("http://localhost:8080/lote", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })}
    else{
        const resp = await fetch("http://localhost:8080/lote/produto/"+inputSearch.value, {
            method: "GET",
            headers: {
                "Content-Type":"application/json"
            }
        })
    }
    const data = await resp.text()
    JSON.parse(data).forEach(element=> {
        let {id, dataEntrega, observacao, idOD, idOF, codigo, orgaoDonatario, orgaoFiscal, produto} = element
        let li = document.createElement("li")
        console.log(orgaoFiscal.nome)
        li.setAttribute("value","OD")
        let div = document.createElement("div")
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
        ulP.style["font-size"] = "small"
        ulP.innerHTML = "Produtos"
        let tabela = document.createElement("table")
        tabela.setAttribute("class", "uk-table uk-table-hover")
        let trhead = document.createElement("tr")
        let th1 = document.createElement("th")
        let th2 = document.createElement("th")
        let th3 = document.createElement("th")
        let th4 = document.createElement("th")
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
        li.appendChild(div)
        ulLote.appendChild(li)
    })
}

async function loadCatalogoLoteCliente()
{

    const resp = await fetch("lote/CatalogoLote.html")
    const html = await resp.text()
    document.getElementById("bodyContent").innerHTML=html
    printCatalogoLoteCliente();
}








loadCatalogoLoteCliente();