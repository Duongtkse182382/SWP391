
function submitForm() {
    const tableBody = document.querySelector('tbody');
    if (tableBody.rows.length === 0) {
        alert('Please add at least one product before saving.');
        return; 
    }
     
    const form = document.getElementById('add-result');
    const formData = new FormData(form);

    fetch(form.action, {
        method: 'POST',
        body: formData
    }).then(response => {
        if (response.ok) {
            window.location.href = '/orders/listOfOrder';
        } else {
            alert('Failed to save the order. Please try again.');
        }
    }).catch(error => {
        console.error('Error:', error);
        alert('An error occurred. Please try again.');
    });
}
document.addEventListener("DOMContentLoaded", function() {
        function calculateTotalPrice() {
            let total = 0;
            const priceElements = document.querySelectorAll('.orderDetail-price');
            priceElements.forEach(function(priceElement) {
                total += parseFloat(priceElement.textContent) || 0;
            });
            document.getElementById('total-price').value = total.toFixed(2);
        }
        calculateTotalPrice();
    });
