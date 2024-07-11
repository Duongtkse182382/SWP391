function showPasswordModal() {
  document.getElementById("passwordModal").style.display = "block";
  document.querySelector(".profile").classList.add("overlay"); // Thêm lớp overlay vào phần tử profile
}

function hidePasswordModal() {
  document.getElementById("passwordModal").style.display = "none";
  document.querySelector(".profile").classList.remove("overlay"); // Loại bỏ lớp overlay từ phần tử profile
}
