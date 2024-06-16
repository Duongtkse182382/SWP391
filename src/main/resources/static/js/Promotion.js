function showPasswordModal() {
  // Kiểm tra nếu đây là lần đầu tiên tải trang
  if (!sessionStorage.getItem("isPageLoaded")) {
    // Đặt cờ trong sessionStorage để đánh dấu rằng trang đã được tải
    sessionStorage.setItem("isPageLoaded", "true");
    return; // Không hiển thị modal
  }
  document.getElementById("passwordModal").style.display = "block";
  document.querySelector(".content-group").classList.add("overlay");
}

function hidePasswordModal() {
  document.getElementById("passwordModal").style.display = "none";
  document.querySelector(".content-group").classList.remove("overlay");
}

function searchProducts() {
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("searchInput");
        filter = input.value.toUpperCase().trim();
        table = document.querySelector("table");
        tr = table.getElementsByTagName("tr");

        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[0]; 
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
