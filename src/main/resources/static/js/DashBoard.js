const ctx1 = document.getElementById("overviewChart1").getContext("2d");
const ctx2 = document.getElementById("overviewChart2").getContext("2d");

const options = {
  scales: {
    y: {
      beginAtZero: true,
      grid: {
        display: false,
      },
      ticks: {
        callback: function (value, index, values) {
          return "$" + value.toLocaleString();
        },
      },
    },
    x: {
      grid: {
        display: false,
      },
    },
  },
};

new Chart(ctx1, {
  type: "bar",
  data: {
    labels: [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
      "Dec",
    ],
    datasets: [
      {
        label: "Overview",
        data: [
          45000, 50000, 55000, 60000, 70000, 80000, 75000, 65000, 60000, 55000,
          50000, 30000,
        ],
        backgroundColor: "#CCFCFC",
      },
    ],
  },
  options: options,
});

new Chart(ctx2, {
  type: "bar",
  data: {
    labels: [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
      "Dec",
    ],
    datasets: [
      {
        label: "Overview",
        data: [
          40000, 45000, 50000, 55000, 65000, 70000, 60000, 58000, 54000, 50000,
          46000, 25000,
        ],
        backgroundColor: "rgba(0, 0, 0, 0.8)",
      },
    ],
  },
  options: options,
});
