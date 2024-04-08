
const app = angular.module("admin-app", []);
app.controller("admin-ctrl", function($scope, $http) {
	$scope.message = null;
	// lấy username
	$scope.intializeAcc = function() {
		// load product
		$http.get("/rest/accounts/current").then(resp => {
			$scope.form = resp.data;
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

	// product
	$scope.items = [];
	$scope.cates = [];
	$scope.form = {};


	$scope.intialize = function() {
		// load product
		$http.get("/rest/products").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate)
			})
		});
		// load category
		$http.get("/rest/categories").then(resp => {
			$scope.cates = resp.data;
			console.log($scope.cates);
		})
	}
	// khở đầu
	$scope.intialize();

	// load table
	function refreshTable() {
		$http.get('/rest/products').then(resp => {
			$scope.items = resp.data;
			console.log($scope.items);
		}).catch(error => {
			console.log("Error", error);
		});
	}

	//xóa form
	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
			image1: 'cloud-upload.jpg',
			image2: 'cloud-upload.jpg',
			image3: 'cloud-upload.jpg',
			available: true
		};
	}


	// hiển thị
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		console.log($scope.form)
		// $(".nav-tabs a:eq(0)").tab("show") // hàm xử lý lúc xài 2 tap và nhấn nó tự đổi qua tab kia
	}

	// thêm new 
	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post(`/rest/products`, item).then(resp => {
			resp.data.createDate = new Date(resp.data.createDate)
			$scope.items.push(resp.data);
			$scope.reset();
			$scope.message = "Thêm mới sản phẩm thành công!";
			refreshTable();
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Lỗi thêm sản phẩm";
		})

	}

	// update 
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/products/${item.proId}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.proId == item.proId);
			$scope.items[index] = item;
			$scope.message = "Cập nhật sản phẩm thành công!";
			refreshTable();
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Lỗi cập nhật sản phẩm";

		})
	}


	$scope.delete = function(item) {
		// Kiểm tra nếu có bản ghi liên quan trong bảng OrderDetails
		$http.get(`/rest/orderDetails?productId=${item.proId}`).then(orderDetailsResp => {
			var orderDetails = orderDetailsResp.data;

			if (orderDetails.length > 0) {

				alert("Có các đơn đặt hàng liên quan đến sản phẩm này. Xóa chúng trước khi xóa sản phẩm.");
			} else {

				$http.delete(`/rest/products/${item.proId}`).then(resp => {
					var index = $scope.items.findIndex(p => p.proId == item.proId);
					$scope.items.splice(index, 1);
					$scope.message = "Xóa sản phẩm thành công!";
					$scope.reset();

				}).catch(error => {
					console.log("Error", error);
					$scope.message = "Lỗi xóa sản phẩm";
				});
			}
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Có các đơn đặt hàng liên quan đến sản phẩm này. Xóa chúng trước khi xóa sản phẩm.";
		});
	}


	// upload hình
	$scope.imageChangedImage1 = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image1 = resp.data.name;
		}).catch(error => {
			alert("Lỗi upload hình ảnh ");
			console.log("Error", error);
		})
	}

	$scope.imageChangedImage2 = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image2 = resp.data.name;
		}).catch(error => {
			alert("Lỗi upload hình ảnh ");
			console.log("Error", error);
		})
	}
	$scope.imageChangedImage3 = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image3 = resp.data.name;
		}).catch(error => {
			alert("Lỗi upload hình ảnh ");
			console.log("Error", error);
		})
	}
	$scope.reset();


	// phân trang
	// phân trang
	$scope.pager = {
		page: 0,
		size: 3,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	};




	// tách phần account này 
	// account
	$scope.itemsAccount = [];
	$scope.formAccount = {};
	$scope.auth = [];


	$scope.intializeAccount = function() {
		// load product
		$http.get("/rest/accounts").then(resp => {
			$scope.itemsAccount = resp.data;
			console.log($scope.itemsAccount);
		});
		// load category
		$http.get("/rest/authorities").then(resp => {
			$scope.auth = resp.data;
			console.log($scope.auth);
		})
	}
	// khở đầu
	$scope.intializeAccount();

	// load table
	function refreshTableAccount() {
		$http.get('/rest/accounts').then(resp => {
			$scope.itemsAccount = resp.data;
			console.log($scope.itemsAccount);
		}).catch(error => {
			console.log("Error", error);
		});
	}

	//xóa form
	$scope.resetAccount = function() {
		$scope.formAccount = {

			photo: 'cloud-upload.jpg'


		};
	}


	// hiển thị
	$scope.editAccount = function(item) {
		$scope.formAccount = angular.copy(item);
		console.log($scope.formAccount)
		// $(".nav-tabs a:eq(0)").tab("show") // hàm xử lý lúc xài 2 tap và nhấn nó tự đổi qua tab kia
	}

	// thêm new 
	$scope.createAccount = function() {
		var item = angular.copy($scope.formAccount);
		$http.post(`/rest/accounts`, item).then(resp => {

			$scope.itemsAccount.push(resp.data);
			$scope.resetAccount();
			$scope.message = "Thêm mới sản phẩm thành công!";
			refreshTableAccount();
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Lỗi thêm sản phẩm";
		})

	}

	// update 
	$scope.updateAccount = function() {
		var item = angular.copy($scope.formAccount);
		$http.put(`/rest/accounts/${item.username}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.username == item.username);
			$scope.itemsAccount[index] = item;
			$scope.message = "Cập nhật sản phẩm thành công!";
			refreshTableAccount();
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Lỗi cập nhật sản phẩm";

		})
	}


	$scope.deleteAccount = function(item) {
		// Kiểm tra nếu có bản ghi liên quan trong bảng OrderDetails
		$http.get(`/rest/orderDetails?productId=${item.proId}`).then(orderDetailsResp => {
			var orderDetails = orderDetailsResp.data;

			if (orderDetails.length > 0) {

				alert("Có các đơn đặt hàng liên quan đến sản phẩm này. Xóa chúng trước khi xóa sản phẩm.");
			} else {

				$http.delete(`/rest/accounts/${item.proId}`).then(resp => {
					var index = $scope.itemsAccount.findIndex(p => p.proId == item.proId);
					$scope.itemsAccount.splice(index, 1);
					$scope.message = "Xóa sản phẩm thành công!";
					$scope.reset();

				}).catch(error => {
					console.log("Error", error);
					$scope.message = "Lỗi xóa sản phẩm";
				});
			}
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Có các đơn đặt hàng liên quan đến sản phẩm này. Xóa chúng trước khi xóa sản phẩm.";
		});
	}


	// upload hình
	$scope.imageChangedImageAccount = function(files) {
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


	$scope.resetAccount();


	// phân trang
	$scope.pagerAccount = {
		page: 0,
		size: 3,
		get itemsAccount() {
			var start = this.page * this.size;
			return $scope.itemsAccount.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsAccount.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	};











	// Kiểm tra dữ liệu được load từ server
	$scope.checkData = function() {
		console.log('$scope.items:', $scope.items);
	};

	// Kiểm tra xem có lỗi JavaScript nào xuất hiện không
	window.onerror = function(msg, url, lineNo, columnNo, error) {
		console.log('Error:', msg, 'URL:', url, 'Line:', lineNo, 'Column:', columnNo, 'Error object:', error);
		return false;
	};
})




