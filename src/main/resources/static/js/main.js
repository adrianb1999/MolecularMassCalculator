function calculateMass() {

    let formula = document.getElementById("formulaInput").value
    let result = document.getElementById("massResult")
    result.innerHTML = ""

   fetch(`/api/massCalculator?formula=${formula}`,
        {
            method: 'GET',
        })
        .then(response => {
                if (response.status === 200) {
                    response.json().then(data => {

                            for (i of data.formulas) {
                                result.insertAdjacentHTML("beforeend",
                                    `${i.formula} : ${i.mass}<br>`
                                )
                            }
                        }
                    )
                }
                else {
                    response.json().then(data => {
                        result.insertAdjacentHTML("beforeend",data.message)
                    })
                }
            }
        )
        .catch((error) => {
            console.error('Error:', error);
        });
}

document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("#formulaForm").onsubmit = function () {
        calculateMass()
        return false
    }
})