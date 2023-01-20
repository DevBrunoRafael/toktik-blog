let searchForm = document.querySelector("#searchForm");
let searchInput = document.querySelector('#searchInput');
let searchClose = document.querySelector('#searchClose');
let resultSearchBox = document.querySelector('#resultSearchBox');


searchForm.addEventListener('submit', (e) => {
    e.preventDefault();

    fetch(`http://localhost:8080/toktik/find/${searchInput.value}`, {
        method: 'GET'
    })
    .then(data => data.json())
    .then(res => {
        resultSearchBox.innerHTML = ``

        if (res.length > 0) {

            res.forEach(pub => {
                const date = new Date(pub.createdAt);
                const formattedDate = date.toLocaleDateString("pt-BR");

                resultSearchBox.innerHTML += `
                  <div class="card" style="width: 28rem; margin-bottom: .5rem">
                    <div class="card-body">
                      <h5 class="card-title">${pub.name}</h5>
                      <h6 class="card-subtitle mb-2 text-muted">
                        <span>${formattedDate}</span>
                        - <span>${pub.user.nome}</span>
                      </h6>
                      <p class="card-text">${pub.text}</p>
                    </div>
                  </div>
               `;
            });

        }
        else resultSearchBox.innerHTML += `<p>Nenhuma publicação encontrada</p>`

    });
});

searchClose.addEventListener('click', () => {
    searchInput.value = ``;
    resultSearchBox.innerHTML = ``;
});