
function updatePrice() {
    const typeGold = document.getElementById("typeGold").value;
    const weight = document.getElementById("weight").value;

    if (typeGold && weight) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", `/orders/updatePrice?typeGold=${typeGold}&weight=${weight}`, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                document.querySelector('#gold-price').value = response.goldPrice;
            }
        };
        xhr.send();
    }
}

 function submitForm() {
        const form = document.getElementById('add-result');
        const formData = new FormData(form);

        fetch(form.action, {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                window.location.href = '/orders/listOfPurchaseOrder';
            } else {
                alert('Failed to save the order. Please try again.');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again.');
        });
    }