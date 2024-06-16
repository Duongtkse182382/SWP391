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
        // Kiểm tra xem selectedProductId trước đó có giống với productId mới khôn
        window.location.href = `/orders/new-sell-order?productId=${selectedProductId}`;
    } else {
        alert("Please select a product first.");
    }
}

function removeProduct(row) {
    row.remove();
}
