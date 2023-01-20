function toggleMode() {
    const body = document.body
    const button = document.querySelector("#modeButton");

    if (body.hasAttribute("data-bs-theme")) {

        body.removeAttribute("data-bs-theme");
        button.classList.toggle("btn-outline-dark");
        button.classList.toggle("btn-outline-light");
        button.innerHTML = `Dark <i class="fa-solid fa-moon"></i>`;

    } else {

        body.setAttribute("data-bs-theme", "dark");
        button.classList.toggle("btn-outline-dark");
        button.classList.toggle("btn-outline-light");
        button.innerHTML = `Light <i class=\"fa-solid fa-sun\"></i>`;

    }
}