let addFriendsForm = document.querySelector("#addFriendsForm");
let addFriendsInput = document.querySelector('#addFriendsInput');
let addFriendsClose = document.querySelector('#addFriendsClose');
let tableBody = document.querySelector('#tableBody');


addFriendsForm.addEventListener('submit', (e) => {
    e.preventDefault();

    fetch(`http://localhost:8080/toktik/users/${addFriendsInput.value}`, {
        method: 'GET'
    })
    .then(data => data.json())
    .then(res => {
        tableBody.innerHTML = ``

        if (res.length > 0) {
            let count = 1;
            res.forEach(user => {
                tableBody.innerHTML += `
                  <tr>
                    <th scope="col">${count}</th>
                    <td>${user.nome}</td>
                    <td align="right">
                      <button
                        type="button"
                        class="btn btn-primary btn-sm"
                        onclick="follow(${user.uuid})"
                       >
                       Seguir
                       </button>
                    </td>
                  </tr>
               `;
                count++;
            });
        }
        else tableBody.innerHTML += `<p>Nenhum usuário encontrado</p>`

    });
});

function follow(uuid) {
    fetch(`http://localhost:8080/toktik/users/add-friend/${uuid}`, {
        method: 'PATCH'
    })
    location.reload()
}

addFriendsClose.addEventListener('click', () => {
    addFriendsInput.value = ``;
    tableBody.innerHTML = ``;
});