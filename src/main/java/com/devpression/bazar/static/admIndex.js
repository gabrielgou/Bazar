let cadastrarProduto = document.getElementById("cadastrarProduto");
cadastrarProduto.onclick= (ev)=> {
    ev.preventDefault();
    const json={}
    let form = document.getElementById("formCadastroProduto");
    let dataForm = new FormData(form);
    for ([name, value] of dataForm) {
        json[name] = value
        dataForm.set(name,null)
    }
    fetch("http://localhost:8080/produto", {
        method: "POST",
        body: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function (response) {
            return response.text()
        })
        .then(function (data) {

            alert(data)

        })
        .catch(function (erro) {
            alert(erro)
        })
    form.reset()
}
let catalogoProduto = document.getElementById("btnCatalogoProduto")
catalogoProduto.onclick= (ev)=> {
    ev.preventDefault()
    let tabela = document.getElementById("tabelaCatalogoProduto")
    fetch("http://localhost:8080/produto", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
        .then(function (response) {
            return response.text()
        })
        .then(function(data){
            JSON.parse(data).forEach(element=> {
                let {codigo,nome, descricao} = element
                let tr = document.createElement("tr")
                let td1 = document.createElement("th")
                let td2 = document.createElement("td")
                let td3 = document.createElement("td")
                let td4 = document.createElement("td")
                let bt1 = document.createElement("button")
                //bt1.setAttribute("class", "btn btn-primary");
                bt1.setAttribute("onclick", "apagarProduto("+codigo+")")
                bt1.innerHTML="Apagar"
                let bt2 = document.createElement("button")
                //bt2.setAttribute("class", "btn btn-primary");
                bt2.setAttribute("onclick", "alterarProduto("+codigo+")")
                bt2.innerHTML="Alterar"
                td1.innerHTML=codigo
                td2.innerHTML=nome
                td3.innerHTML=descricao
                td4.appendChild(bt1)
                td4.appendChild(bt2)
                tr.appendChild(td1)
                tr.appendChild(td2)
                tr.appendChild(td3)
                tr.appendChild(td4)
                tabela.appendChild(tr);
            })
        })
        .catch(function (erro) {
            alert(erro)
        })
}
