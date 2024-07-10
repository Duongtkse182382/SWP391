let selectedProductId = null;
let doubleClickTimer = null;
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
        calculateTotal();
    } else {
        alert("Please select a product first.");
    }
}
function removeProduct(row) {
    row.remove();
    calculateTotal();
}
function submitForm() {
    const tableBody = document.querySelector('tbody');
    if (tableBody.rows.length === 0) {
        alert('Please add at least one product before saving.');
        return; 
    }
     if (!validateForm()) {
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


function calculateTotal() {
    let totalPrice = 0;
    document.querySelectorAll('tbody tr').forEach(row => {
        const price = parseFloat(row.cells[9].innerText.replace(/,/g, '')) || 0;
        totalPrice += price;
    });
    document.getElementById('total-price').value = totalPrice.toFixed(1);
}

function validateForm() {
    const orderDateInput = document.getElementById('order-date');
    const staffNameInput = document.getElementById('staff-name');
    const customerNameInput = document.getElementById('customer-name');
    const phoneNumberInput = document.getElementById('phone-number');
    const orderDate = orderDateInput.value.trim();
    const staffName = staffNameInput.value.trim();
    const customerName = customerNameInput.value.trim();
    const phoneNumber = phoneNumberInput.value.trim();
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    const phoneRegex = /^\d+$/;
    const specialCharRegex = /[!@#$%^&*(),.?":{}|<>]/;
    const alphanumericRegex = /^[a-zA-Z]+$/;
    let isValid = true;
    if (orderDate === '') {
        document.getElementById('order-date-error').innerText = "Date is not empty";
        isValid = false;
    }else if(!dateRegex.test(orderDate)){
		 document.getElementById('order-date-error').innerText = "Date is not valid(yyyy-mm-dd)";
        isValid = false;
	} else {
        document.getElementById('order-date-error').innerText = "";
    }

    if (staffName === '' ) {
        document.getElementById('staff-name-error').innerText = "Staff name is not empty.";
        isValid = false;
    }else if(!alphanumericRegex.test(staffName)){
		 document.getElementById('staff-name-error').innerText = "Staff name only contain letters.";
        isValid = false;
	}
     else if(specialCharRegex.test(staffName)){
		document.getElementById('staff-name-error').innerText = "Not contain special characters.";
        isValid = false;
	}
    else {
        document.getElementById('staff-name-error').innerText = "";
    }
    
    if (customerName === '') {
        document.getElementById('customer-name-error').innerText = "Customer name is not empty.";
        isValid = false;
    }else if(!alphanumericRegex.test(customerName)){
		 document.getElementById('customer-name-error').innerText = "Customer name only contain letters.";
        isValid = false;
	}
    else if(specialCharRegex.test(customerName)){
		 document.getElementById('customer-name-error').innerText = "Not contain special characters.";
        isValid = false;
	}
     else {
        document.getElementById('customer-name-error').innerText = "";
    }
    
    if(phoneNumber === '' ){
		document.getElementById('phone-number-error').innerText = "Phone number is not empty.";
        isValid = false;
	}
    else if(!phoneRegex.test(phoneNumber)) {
        document.getElementById('phone-number-error').innerText = "Phone number should contain only digits.";
        isValid = false;
    } else {
        document.getElementById('phone-number-error').innerText = "";
    }

    return isValid;
}
document.addEventListener("DOMContentLoaded", function() {
    calculateTotal();
    const orderDate = localStorage.getItem("order-date");
    const staffName = localStorage.getItem("staff-name");
    const customerName = localStorage.getItem("customer-name");
    const phoneNumber = localStorage.getItem("phone-number");

    if (orderDate) document.getElementById("order-date").value = orderDate;
    if (staffName) document.getElementById("staff-name").value = staffName;
    if (customerName) document.getElementById("customer-name").value = customerName;
    if (phoneNumber) document.getElementById("phone-number").value = phoneNumber;

    document.querySelectorAll('input').forEach(input => {
        input.addEventListener('blur', saveData);
    });
});
function saveData() {
    localStorage.setItem("order-date", document.getElementById("order-date").value);
    localStorage.setItem("staff-name", document.getElementById("staff-name").value);
    localStorage.setItem("customer-name", document.getElementById("customer-name").value);
    localStorage.setItem("phone-number", document.getElementById("phone-number").value);
}

function filterProducts() {
    let input, filter, table, tr, td, i, txtValue;
    input = document.getElementById('product-search');
    filter = input.value.trim().toUpperCase();
    table = document.querySelector('.bill-table table');
    tr = table.getElementsByTagName('tr');
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName('td')[2]; 
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = ''; 
            } else {
                tr[i].style.display = 'none'; 
            }
        }
    }
	clearTimeout(doubleClickTimer);
	  doubleClickTimer = setTimeout(() => {
	      document.getElementById('dropdown').classList.remove('show'); 
	  }, 200); 
}
