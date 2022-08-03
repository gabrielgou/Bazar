
async function CadastrarOD() {
    const json={}
    let form = document.getElementById("formCadastroOD");
    let dataForm = new FormData(form);
    for ([name, value] of dataForm) {
        json[name] = value
    }
    const resp = await fetch("http://localhost:8080/orgaoDonatario", {
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

async function printCatalogoOD()
{
    let conteudoTabelaOD = document.getElementById("conteudoTabelaOD")
    let tabela1 = document.getElementById("tabelaCatalogoOD")
    conteudoTabelaOD.removeChild(tabela1)
    let tabela = document.createElement("table")
    tabela.setAttribute("class", "table")
    tabela.setAttribute("id","tabelaCatalogoOD" )
    let trhead = document.createElement("tr")
    let th1 = document.createElement("th")
    let th2 = document.createElement("th")
    let th3 = document.createElement("th")
    let th4 = document.createElement("th")
    let th5 = document.createElement("th")
    let th6 = document.createElement("th")
    let th7 = document.createElement("th")
    th1.innerHTML="Id"
    th2.innerHTML="Nome"
    th3.innerHTML="Endereço"
    th4.innerHTML="Telefone"
    th5.innerHTML="Horario Funcionamento"
    th6.innerHTML="Descrição"
    th7.innerHTML="Ações"
    trhead.appendChild(th1)
    trhead.appendChild(th2)
    trhead.appendChild(th3)
    trhead.appendChild(th4)
    trhead.appendChild(th5)
    trhead.appendChild(th6)
    trhead.appendChild(th7)
    tabela.appendChild(trhead)
    const resp = await fetch("http://localhost:8080/orgaoDonatario", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    const data = await resp.text()
    JSON.parse(data).forEach(element=> {
        let {id, nome, endereco, telefone, horarioFuncionamento, descricao} = element
        let tr = document.createElement("tr")
        let td1 = document.createElement("th")
        let td2 = document.createElement("td")
        let td3 = document.createElement("td")
        let td4 = document.createElement("td")
        let td5 = document.createElement("td")
        let td6 = document.createElement("td")
        let td7 = document.createElement("td")
        let bt1 = document.createElement("button")
        //bt1.setAttribute("class", "btn btn-primary");
        bt1.setAttribute("onclick", "apagarOD(" + id + ")")
        bt1.innerHTML = "Apagar"
        let bt2 = document.createElement("button")
        //bt2.setAttribute("class", "btn btn-primary");
        bt2.setAttribute("onclick", "loadAlterarOD(" + id + ")")
        bt2.innerHTML = "Alterar"
        td1.innerHTML = id
        td2.innerHTML = nome
        td3.innerHTML = endereco
        td4.innerHTML = telefone
        td5.innerHTML = horarioFuncionamento
        td6.innerHTML = descricao
        td7.appendChild(bt1)
        td7.appendChild(bt2)
        tr.appendChild(td1)
        tr.appendChild(td2)
        tr.appendChild(td3)
        tr.appendChild(td4)
        tr.appendChild(td5)
        tr.appendChild(td6)
        tr.appendChild(td7)
        tabela.appendChild(tr);
        conteudoTabelaOD.appendChild(tabela)
    })
}
async function apagarOD(id) {
    if(confirm("Você tem certeza que deseja apagar?")) {
        const resp = await fetch("http://localhost:8080/orgaoDonatario/" + id, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })
        const data = await resp.text()
        alert(data)
        printCatalogoOD()
    }
}
async function loadCatalogoOD()
{
    const resp = await fetch("orgDonatario/CatalogoOD.html");
    const html = await resp.text();
    document.getElementById("bodyContent").innerHTML=html
    printCatalogoOD();
}
async function loadAlterarOD(id)
{
    const resp = await fetch("orgDonatario/AlterarOD.html");
    const html = await resp.text();
    document.getElementById("bodyContent").innerHTML=html
    const resp1 = await fetch("http://localhost:8080/orgaoDonatario/"+id, {
        method:"GET",
        headers:{
            "Content-Type": "application/json"
        }
    })
    const data = await resp1.text()
    let {id1,nome,endereco,telefone,horarioFuncionamento,descricao} = JSON.parse(data)
    document.getElementById("alterarId").value=id
    document.getElementById("alterarNome").value=nome
    document.getElementById("alterarEndereco").value=endereco
    document.getElementById("alterarTelefone").value=telefone
    document.getElementById("alterarHorario").value=horarioFuncionamento
    document.getElementById("alterarDescrição").value=descricao
}
async function alterarOD() {
    const json={}
    let form = document.getElementById("formAlterarOD");
    let dataForm = new FormData(form);
    for ([name, value] of dataForm) {
        json[name] = value
    }
    const resp = await fetch("http://localhost:8080/orgaoDonatario", {
        method: "PUT",
        body: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    const data = await resp.text()
    alert(data)
    loadCatalogoOD()
}

