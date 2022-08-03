
async function CadastrarOF() {
    const json={}
    let form = document.getElementById("formCadastroOF");
    let dataForm = new FormData(form);
    for ([name, value] of dataForm) {
        json[name] = value
        dataForm.set(name,null)
    }
    const resp = await fetch("http://localhost:8080/orgaoFiscalizador", {
        method: "POST",
        body: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    const data = await resp.text()
    alert(data)
    form.reset()
}

async function printCatalogoOF()
{
    let conteudoTabelaOF = document.getElementById("conteudoTabelaOF")
    let tabela1 = document.getElementById("tabelaCatalogoOF")
    conteudoTabelaOF.removeChild(tabela1)
    let tabela = document.createElement("table")
    tabela.setAttribute("class", "table")
    tabela.setAttribute("id","tabelaCatalogoOF" )
    let trhead = document.createElement("tr")
    let th1 = document.createElement("th")
    let th2 = document.createElement("th")
    let th3 = document.createElement("th")
    let th4 = document.createElement("th")
    th1.innerHTML="Id"
    th2.innerHTML="Nome"
    th3.innerHTML="Descrição"
    th4.innerHTML="Ações"
    trhead.appendChild(th1)
    trhead.appendChild(th2)
    trhead.appendChild(th3)
    trhead.appendChild(th4)
    tabela.appendChild(trhead)
    const resp = await fetch("http://localhost:8080/orgaoFiscalizador", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    const data = await resp.text()
    JSON.parse(data).forEach(element=> {
        let {id,nome, descricao} = element
        let tr = document.createElement("tr")
        let td1 = document.createElement("th")
        let td2 = document.createElement("td")
        let td3 = document.createElement("td")
        let td4 = document.createElement("td")
        let bt1 = document.createElement("button")
        //bt1.setAttribute("class", "btn btn-primary");
        bt1.setAttribute("onclick", "apagarOF("+id+")")
        bt1.innerHTML="Apagar"
        let bt2 = document.createElement("button")
        //bt2.setAttribute("class", "btn btn-primary");
        bt2.setAttribute("onclick", "loadAlterarOF("+id+")")
        bt2.innerHTML="Alterar"
        td1.innerHTML=id
        td2.innerHTML=nome
        td3.innerHTML=descricao
        td4.appendChild(bt1)
        td4.appendChild(bt2)
        tr.appendChild(td1)
        tr.appendChild(td2)
        tr.appendChild(td3)
        tr.appendChild(td4)
        tabela.appendChild(tr);
        conteudoTabelaOF.appendChild(tabela)
    })

}
async function apagarOF(id) {
    if(confirm("Você tem certeza que deseja apagar?")) {
        const resp = await fetch("http://localhost:8080/orgaoFiscalizador/" + id, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })
        const data = await resp.text()
        alert(data)
        printCatalogoOF()
    }
}
async function loadCatalogoOF()
{
    const resp = await fetch("orgFiscalizador/CatalogoOF.html");
    const html = await resp.text();
    document.getElementById("bodyContent").innerHTML=html
    printCatalogoOF();
}
async function loadAlterarOF(id)
{
    const resp = await fetch("orgFiscalizador/AlterarOF.html");
    const html = await resp.text();
    document.getElementById("bodyContent").innerHTML=html
    const resp2 = await  fetch("http://localhost:8080/orgaoFiscalizador/"+id, {
        method:"GET",
        headers:{
            "Content-Type": "application/json"
        }
    })
    const data = await resp2.text()
    let {id1,nome, descricao} = JSON.parse(data)
    document.getElementById("alterarId").value=id
    document.getElementById("alterarNome").value=nome
    document.getElementById("alterarDescrição").value=descricao
}
async function alterarOF() {
    const json={}
    let form = document.getElementById("formAlterarOF");
    let dataForm = new FormData(form);
    for ([name, value] of dataForm) {
        json[name] = value
    }
    const resp = await fetch("http://localhost:8080/orgaoFiscalizador", {
        method: "PUT",
        body: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    const data = await resp.text()
    alert(data)
    loadCatalogoOF()
}

