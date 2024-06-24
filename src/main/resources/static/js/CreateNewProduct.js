$(document).ready(function() {
    // Function to show/hide jewelry specific fields based on product type
    $('#type').change(function() {
        var selectedType = $("#type option:selected").text();
        if (selectedType === 'Jewelry') {
            $('#jewelry-fields').show();
        } else {
            $('#jewelry-fields').hide();
        }
    });

    $('#create-button').click(function(event) {
        if (!validateForm()) {
            event.preventDefault(); 
            return;
        }
        
        let formData = new FormData(document.getElementById('create-product-form'));
        fetch('/manager/products/create-product/create', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (response.ok) {
                window.location.href = '/manager/products';
            } else {
                alert('Create product failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });

    function validateForm() {
        $('#product-code-error').text('');
        $('#product-name-error').text('');
        $('#weight-error').text('');
        $('#price-rate-error').text('');

        let productCode = $('#product-code').val().trim();
        let productName = $('#product-name').val().trim();
        let weight = $('#weight').val().trim();
        let priceRate = $('#price-rate').val().trim();
        let isValid = true;

        if (productCode === '') {
            $('#product-code-error').text('Product code cannot be empty.');
            isValid = false;
        } else if (/[^a-zA-Z0-9]/.test(productCode)) {
            $('#product-code-error').text('Product code must not contain special characters.');
            isValid = false;
        }

        if (productName === '') {
            $('#product-name-error').text('Product name cannot be empty.');
            isValid = false;
        } else if (/[^a-zA-Z0-9\s]/.test(productName)) {
            $('#product-name-error').text('Product must not contain special characters.');
            isValid = false;
        }

        if (weight === '') {
            $('#weight-error').text('Weight cannot be empty.');
            isValid = false;
        } else if (!/^\d+(\.\d+)?$/.test(weight)) {
            $('#weight-error').text('Weight must be a number in float format.');
            isValid = false;
        } else if (parseFloat(weight) <= 0) {
            $('#weight-error').text('Weight must be greater than 0.');
            isValid = false;
        }

        if (priceRate === '') {
            $('#price-rate-error').text('Price Rate cannot be empty.');
            isValid = false;
        } else if (!/^\d+(\.\d+)?$/.test(priceRate)) {
            $('#price-rate-error').text('Price Rate must be a number in float format.');
            isValid = false;
        } else if (parseFloat(priceRate) <= 0) {
            $('#price-rate-error').text('Price Rate must be greater than 0.');
            isValid = false;
        }
      

        return isValid;
    }
});
