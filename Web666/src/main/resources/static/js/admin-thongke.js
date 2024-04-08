app.controller("thongke-ctrl", function($scope, $http) {
	$scope.intializeAcc = function() {
		// load product
		$http.get("/rest/accounts/current").then(resp => {

			$scope.form = resp.data;
			console.log("quyền: " + $scope.form)
			$scope.fullName = $scope.form.fullname;
			//$scope.imageURL = $scope.form.photo
			//Lấy hình ảnh từ dữ liệu nhận được
			var imageUrl = $scope.form.photo;
			// Nếu có hình ảnh
			if (imageUrl) {
				$scope.userImage = "/assets/images/" + imageUrl;
			} else {
				$scope.userImage = "/admin/dist/img/AdminLTELogo.jpg";

			}


		});

	}
	// khở đầu
	$scope.intializeAcc();


	$scope.getStatistics = function(days) {
		$http.get(`/rest/thongke/statistics?days=${days}`)

			.then(function(response) {
				updateChart(response.data);
				updateStatistics(response.data);
			})
			.catch(function(error) {
				console.error('Error fetching statistics:', error);
			});
	};


	var myChart;

	function updateChart(data) {
		// Nếu biểu đồ hiện tại đã được tạo, hủy nó trước khi vẽ biểu đồ mới
		if (myChart) {
			myChart.destroy();
		}

		var ctx = document.getElementById('orderChart').getContext('2d');
		myChart = new Chart(ctx, {
			type: 'bar',
			data: {
				labels: ['Tổng số đơn hàng', 'Tổng tiền', 'Tổng số lượng', 'Tổng số khách hàng'],
				datasets: [{
					label: 'Tổng số đơn hàng',
					data: [
						data.totalOrders,
						0, 0, 0
					],
					backgroundColor: 'rgba(75, 192, 192, 0.2)',
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				}, {
					label: 'Tổng tiền',
					data: [
						0, data.totalMoney, 0, 0
					],
					backgroundColor: 'rgba(255, 99, 132, 0.2)',
					borderColor: 'rgba(255, 99, 132, 1)',
					borderWidth: 1
				}, {
					label: 'Tổng số lượng',
					data: [
						0, 0, data.totalQuantity, 0
					],
					backgroundColor: 'rgba(54, 162, 235, 0.2)',
					borderColor: 'rgba(54, 162, 235, 1)',
					borderWidth: 1
				}, {
					label: 'Tổng số khách hàng',
					data: [
						0, 0, 0, data.totalUniqueCustomers
					],
					backgroundColor: 'rgba(100, 162, 188, 0.2)',
					borderColor: 'rgba(100, 162, 188, 1)',
					borderWidth: 1
				}]
			},
			options: {
				scales: {
					y: {
						beginAtZero: true
					}
				}
			}
		});
	}





	function formatCurrency(amount) {
		return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
	}

	function updateStatistics(data) {
		document.getElementById('totalOrders').querySelector('h3').innerText = data.totalOrders;
		document.getElementById('totalAmount').querySelector('h3').innerText = formatCurrency(data.totalMoney);
		document.getElementById('totalCustomers').querySelector('h3').innerText = data.totalUniqueCustomers;
		document.getElementById('totalQuantity').querySelector('h3').innerText = data.totalQuantity;
	}







	





})