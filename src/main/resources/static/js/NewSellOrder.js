let selectedProductId = null;

function showDropdown() {
    document.getElementById('dropdown').classList.add('show');
}

function selectProduct(productName, productId) {
    document.getElementById('product-search').value = productName;
    selectedProductId = productId;
    document.getElementById('dropdown').classList.remove('show');
}

function addProduct() {
    if (selectedProductId) {
        window.location.href = `/orders/new-sell-order?productId=${selectedProductId}`;
    } else {
        alert("Please select a product first.");
    }
}

function removeProduct(row) {
    row.remove();
}

 function submitForm() {
            const form = document.getElementById('add-result');
            const formData = new FormData(form);

            fetch(form.action, {
                method: 'POST',
                body: formData
            }).then(response => {
                if (response.ok) {
                    window.location.href = '/seller/products/bill-of-sell';
                } else {
                    alert('Failed to save the order. Please try again.');
                }
            }).catch(error => {
                console.error('Error:', error);
                alert('An error occurred. Please try again.');
            });
        }