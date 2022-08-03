async function Cadastrarproduto(e) {
    console.log("CadastrarProduto")
    const json={}
    let form = document.getElementById("formCadastroProduto");
    let dataForm = new FormData(form);
    for ([name, value] of dataForm) {
        json[name] = value
        dataForm.set(name,null)
    }
    const resp = await fetch("http://localhost:8080/produto", {
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
async function printCatalogoProduto()
{
    console.log("printCatalogo")
    let conteudoTabelaProduto = document.getElementById("conteudoTabelaProduto")
    let tabela1 = document.getElementById("tabelaCatalogoProduto")
    conteudoTabelaProduto.removeChild(tabela1)
    let tabela = document.createElement("table")
    tabela.setAttribute("class", "uk-table uk-table-hover")
    tabela.setAttribute("id","tabelaCatalogoProduto" )
    let trhead = document.createElement("tr")
    let th1 = document.createElement("th")
    let th2 = document.createElement("th")
    let th3 = document.createElement("th")
    let th4 = document.createElement("th")
    th1.innerHTML="Código"
    th2.innerHTML="Nome"
    th3.innerHTML="Descrição"
    th4.innerHTML="Ações"
    trhead.appendChild(th1)
    trhead.appendChild(th2)
    trhead.appendChild(th3)
    trhead.appendChild(th4)
    tabela.appendChild(trhead)
    const resp =await fetch("http://localhost:8080/produto", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    const data = await resp.text()
    JSON.parse(data).forEach(element=> {
        let {codigo, nome, descricao} = element
        let tr = document.createElement("tr")
        let td1 = document.createElement("th")
        let td2 = document.createElement("td")
        let td3 = document.createElement("td")
        let td4 = document.createElement("td")
        let bt1 = document.createElement("button")
        //bt1.setAttribute("class", "btn btn-primary");
        bt1.setAttribute("class", "uk-button uk-button-danger uk-button-small")
        bt1.setAttribute("onclick", "apagarProduto(" + codigo + ")")
        bt1.innerHTML = "Apagar"
        let bt2 = document.createElement("button")
        //bt2.setAttribute("class", "btn btn-primary");
        bt2.setAttribute("class", "uk-button uk-button-primary uk-button-small")
        bt2.setAttribute("onclick", "loadAlterarProduto(" + codigo + ")")
        bt2.innerHTML = "Alterar"
        td1.innerHTML = codigo
        td2.innerHTML = nome
        td3.innerHTML = descricao
        td4.appendChild(bt1)
        td4.appendChild(bt2)
        tr.appendChild(td1)
        tr.appendChild(td2)
        tr.appendChild(td3)
        tr.appendChild(td4)
        tabela.appendChild(tr);
        conteudoTabelaProduto.appendChild(tabela)
    })
}
async function apagarProduto(codigo) {
    console.log("ApagarProduto")
    if(confirm("Você tem certeza que deseja apagar?")) {
        await fetch("http://localhost:8080/produto/" + codigo, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(await function (response) {
                return response.text()
            })
            .then(await function (data) {
                printCatalogoProduto()
                alert(data)
            })
            .catch(function (erro) {
                alert(erro)
            })
    }
}
async function atualizarBody(http) {
    console.log("AtualizarBody")
    const resp = await fetch(http);
    const html = await resp.text();
    document.getElementById("bodyContent").innerHTML=html
}
async function loadCatalogo()
{

    const resp = await fetch("produto/CatalogoProduto.html")
    const html = await resp.text()
    document.getElementById("bodyContent").innerHTML=html
    printCatalogoProduto();
}
async function loadAlterarProduto(codigo)
{
    console.log("loadAlterar")
    const resp = await fetch("produto/AlterarProduto.html");
    const html = await resp.text();
    document.getElementById("bodyContent").innerHTML=html
    const resp2 = await fetch("http://localhost:8080/produto/"+codigo, {
        method:"GET",
        headers:{
            "Content-Type": "application/json"
        }
    })
    const data = await resp2.text()
    let {id,nome, descricao} = JSON.parse(data)
    document.getElementById("alterarCodigo").value=codigo
    document.getElementById("alterarNome").value=nome
    document.getElementById("alterarDescrição").value=descricao
}
async function alterarProduto() {
    console.log("alterarProduto")
    const json={}
    let form = document.getElementById("formAlterarProduto");
    let dataForm = new FormData(form);
    for ([name, value] of dataForm) {
        json[name] = value
    }


    let resp = await fetch("http://localhost:8080/produto", {
        method: "PUT",
        body: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    let data = await resp.text()
    alert(data)
    loadCatalogo()
}
