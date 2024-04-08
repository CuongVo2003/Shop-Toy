app.controller("t5SpBc-ctrl", function($scope, $http) {
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


	


	// thống kê top 5 sp bán chạy 


	$scope.getTopSellingProducts = function() {
		$http.get("/rest/products/top-selling")
			.then(function(response) {
				$scope.topProducts1 = response.data;
				var topProducts = response.data;
				console.log("sp1: " + topProducts);
				drawChart(topProducts);
			}, function(error) {
				console.error("Error fetching top selling products:", error);
			});
	};

	function drawChart(topProducts) {
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Name');
		data.addColumn('number', 'Quantity');
		

		// Add rows from topProducts
		angular.forEach(topProducts, function(product) {
			var priceSum = product.quantity * product.price; // Tính toán giá trị priceSum
			data.addRow([product.product.prodName, product.quantity]); 
			
		});

		var options = {
			title: 'Biểu đồ tốp 5 sản phẩm bán chạy',
			width: 1500,
			height: 1500
		};

		var chart = new google.visualization.PieChart(document.getElementById('piechart'));
		chart.draw(data, options);
	}

	// Load Google Charts API
	google.charts.load('current', {
		'packages': ['corechart']
	});
	google.charts.setOnLoadCallback($scope.getTopSellingProducts);





})