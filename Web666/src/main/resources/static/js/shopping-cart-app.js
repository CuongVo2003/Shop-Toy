const app = angular.module("shopping-cart-app", []);
app.controller("shopping-cart-ctrl", function($scope, $http) {




	

	// xác nhận đơn hàng đã giao 
	$scope.confirmOrder = function() {
		// var orderId = window.location.pathname.split('/').pop();
		var orderId = document.getElementById('orderId').value;
		$http.post('/rest/orders/confirm/' + orderId)
			.then(function(response) {
				if (response.data.success) {
					window.location.href = '/order/list';
					alert('Xác nhận nhận hàng thành công!');
					// Cập nhật giao diện nếu cần
				} else {
					alert('Đã xảy ra lỗi. Không thể xác nhận nhận hàng.');
				}
			})
			.catch(function(error) {
				console.error('Lỗi khi gửi yêu cầu xác nhận đơn hàng:', error);
				alert('Đã xảy ra lỗi. Không thể xác nhận nhận hàng.');
			});
	};



	// Tất cả sản phẩm
	$scope.form = {};
	$scope.account = [];

	$scope.products = [];
	$scope.message = null;
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



	// Kết quả tìm kiếm
	$scope.filteredProducts = [];

	// Văn bản tìm kiếm
	$scope.searchText = "";
	$scope.showProducts = true;
	// lấy thông tin cho modal :v
	$scope.modalPro = {};

	// QUẢN LÝ GIỎ HÀNG
	$scope.cart = {
		items: [],

		//thêm sản phẩm
		add(id) {
			//var productId = parseInt(id, 10);
			var item = this.items.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			} else {
				$http.get(`/rest/products/${id}`).then(resp => {
					resp.data.qty = 1;
					resp.data.image = "/assets/images/" + resp.data.image1;

					this.items.push(resp.data);
					console.log(resp.data)
					this.saveToLocalStorage();
				})
			}
		},



		//xóa sp
		remove(id) {
			var index = this.items.findIndex(item => item.id == id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},
		//xóa hết
		clear() {
			this.items = [];
			this.saveToLocalStorage();
		},
		//tính thành tiền 1sp
		amt_of(item) { },
		//tính tổng sl các mặt hàng 
		get count() {
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		//tổng thành tiền các mặt hàng
		get amount() {
			return this.items
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		// lưu giỏ hàng vào local
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},
		// đọc giỏ hàng từ local
		loadFromLocal() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		}
	}

	$scope.cart.loadFromLocal();

	// xử lý đặt hàng
	$scope.order = {
		createDate: new Date(),
		order_address: "",
		ordPhone: "",
		priceSum: 0, // Khởi tạo giá trị priceSum
		ghiChu: "",
		account: { username: $("#username").text() },
		voucher: {},
		tinhTrang: false,
		// dòng này để them vào orderServiceImpl
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					product: { proId: item.proId },

					price: item.price,
					quantity: item.qty

				}
			});
		},
		get voucher() {
			// Lấy thông tin voucher từ input
			return { vouId: $("#voucherId").val() };
		},


		//đặt hàng
		purchase() {

			// Lấy giá trị của trường ẩn hiddenTotalAmount
			var hiddenTotalAmount = document.getElementById('hiddenTotalAmount').value;
			// Chuyển đổi giá trị thành số dạng float
			var priceSum = parseFloat(hiddenTotalAmount);
			console.log("d" + priceSum);
			var order = angular.copy(this);
			order.priceSum = priceSum;
			console.log(order);// In ra console để kiểm tra dữ liệu trước khi gửi đi

			// thực hiện đặt hàng
			$http.post("/rest/orders", order).then(resp => {
				alert("Đặt hàng thành công");
				$scope.cart.clear();
				location.href = "/order/detail/" + resp.data.ordId;
			}).catch(error => {
				alert("đặt hàng lỗi")
				console.log(error)
			})
		}

	}

	// Hàm mở modal và load thông tin sản phẩm
	$scope.openModal = function(pId) {

		console.log("Product ID:", pId);
		// Gửi yêu cầu AJAX để lấy thông tin sản phẩm dựa trên productId
		$http.get(`/rest/products/${pId}`).then(resp => {
			// Cập nhật nội dung modal với thông tin sản phẩm nhận được
			$scope.modalPro = resp.data; // Gán thông tin sản phẩm vào $scope
			$scope.modalPro.image = '/assets/images/' + $scope.modalPro.image1;
			// Hiển thị modal
			$('#modal_box').modal('show');
			console.log("Product IDshow:", $scope.modalPro);
			console.log("Image URL:", $scope.modalPro.image);

		}).catch(error => {
			console.error("Lỗi show sp js shoppe cart");
		});
	};





	// Hàm tìm kiếm
	$scope.search = function() {
		// Kiểm tra xem searchText có giá trị hay không
		if ($scope.searchText.trim() === "") {
			// Nếu không có giá trị, ẩn danh sách sản phẩm
			$scope.showProducts = false;
		} else {
			// Nếu có giá trị, hiển thị danh sách sản phẩm và lọc danh sách dựa trên searchText
			$scope.showProducts = true;
			$scope.filteredProducts = $scope.products.filter(function(product) {
				// Tìm kiếm theo name, price, createDate
				return (
					product.prodName.toLowerCase().includes($scope.searchText.toLowerCase()) ||
					product.price.toString().includes($scope.searchText) ||
					product.createDate.includes($scope.searchText)
				);
			});
		}
	};




	// Hàm lấy danh sách sản phẩm từ server
	$scope.loadProducts = function() {
		$http.get("/rest/products").then(resp => {
			$scope.products = resp.data;
		}).catch(error => {
			console.error("Lỗi load sản phẩm");
		});
	};

	// Gọi hàm loadProducts để lấy danh sách sản phẩm khi controller khởi tạo
	$scope.loadProducts();


	// hàm xử lý sp yêu thích
	$scope.favorites = {
		itemYt: [],

		// Thêm sản phẩm vào danh sách yêu thích


		add(idYt) {
			//var productId = parseInt(id, 10);
			var item = this.itemYt.find(item => item.idYt == idYt);

			if (!item) {
				$http.get(`/rest/products/${idYt}`).then(resp => {

					resp.data.image = "/assets/images/" + resp.data.image1;

					this.itemYt.push(resp.data);
					console.log(resp.data)
					console.log('Sản phẩm đã được thêm vào danh sách yêu thích.');
					this.saveToLocalStorage();
				})
			}
		},



		//xóa spyt
		removeFromFavorites(idYt) {
			var index = this.itemYt.findIndex(item => item.idYt == idYt);
			this.itemYt.splice(index, 1);
			this.saveToLocalStorage();
			console.log('xóa sản phẩm  yêu thích tc.');
		},
		//xóa hết spyt
		clearSpyt() {
			this.itemYt = [];
			this.saveToLocalStorage();
		},

		// Lưu danh sách sản phẩm yêu thích vào localStorage
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.itemYt));
			localStorage.setItem("favorites", json);
		},

		// Đọc danh sách sản phẩm yêu thích từ localStorage
		loadFromLocal() {
			var json = localStorage.getItem("favorites");
			this.itemYt = json ? JSON.parse(json) : [];
		}


	};

	// Load danh sách sản phẩm yêu thích từ localStorage khi trang được tải
	$scope.favorites.loadFromLocal();







	// lấy user
	$scope.intialize = function() {
		// load product
		$http.get("/rest/accounts/current").then(resp => {
			$scope.form = resp.data;
			console.log($scope.form);
		});

	}
	// khở đầu
	$scope.intialize();


	//xóa form
	$scope.reset = function() {
		$scope.form = {

			photo: 'cloud-upload.jpg'

		};
	}


	// update 
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/accounts/${item.username}`, item).then(resp => {
			var index = $scope.account.findIndex(a => a.username == item.username);
			$scope.account[index] = item;

			$scope.message = "Cập nhật tài khoản thành công!";

		}).catch(error => {
			console.log("Error", error);

			$scope.message = "Lỗi cập nhật tài khoản";

		})
	}

	// update hinh
	$scope.imageChanged = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.photo = resp.data.name;
		}).catch(error => {
			alert("Lỗi upload hình ảnh ");
			console.log("Error", error);
		})
	}
	$scope.reset();






})