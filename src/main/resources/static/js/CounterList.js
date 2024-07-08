function showPasswordModal() {
  document.getElementById("passwordModal").style.display = "block";
  document.querySelector(".content-group").classList.add("overlay");
}
document.addEventListener("DOMContentLoaded", function() {
  document.getElementById("passwordModal").style.display = "none";
  document.getElementById('editModal').style.display = 'none';
});

function hidePasswordModal() {
  document.getElementById("passwordModal").style.display = "none";
  document.querySelector(".content-group").classList.remove("overlay");
}

    function searchOrders() {
        let input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("searchInput");
        filter = input.value.toUpperCase().trim();
        table = document.querySelector("table");
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            tdName = tr[i].getElementsByTagName("td")[1]; 
            tdPhone = tr[i].getElementsByTagName("td")[2];

            if (tdName || tdPhone) {
                txtValueName = tdName.textContent || tdName.innerText;
                txtValuePhone = tdPhone.textContent || tdPhone.innerText;
                
                if (txtValueName.toUpperCase().includes(filter) || txtValuePhone.includes(filter)) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
	


	