
app.controller("admin-voucher-ctrl", function($scope, $http) {
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
				$scope.userImage = imageUrl;
			} else {
				$scope.userImage = "/admin/dist/img/default-avatar.png";

			}

		});

	}
	// khở đầu
	$scope.intializeAcc();
	// DANH MỤC
	$scope.itemsVoucher = [];
	$scope.formVoucher = {};



	$scope.intializeVoucher = function() {
		// load voucher
		$http.get("/api/vouchers").then(resp => {
			$scope.itemsVoucher = resp.data;
			$scope.itemsVoucher.forEach(item => {
				item.expiryDate = new Date(item.expiryDate)
			})
			console.log($scope.itemsVoucher);
		});

	}
	// khở đầu
	$scope.intializeVoucher();

	// load table
	function refreshTableVoucher() {
		$http.get('/api/vouchers').then(resp => {
			$scope.itemsVoucher = resp.data;
			console.log($scope.itemsVoucher);
		}).catch(error => {
			console.log("Error", error);
		});
	}

	//xóa form
	$scope.resetVoucher = function() {
		$scope.formVoucher = {
			createDate: new Date()



		};
	}


	// hiển thị
	$scope.editVoucher = function(item) {
		$scope.formVoucher = angular.copy(item);
		console.log($scope.formVoucher)
		// $(".nav-tabs a:eq(0)").tab("show") // hàm xử lý lúc xài 2 tap và nhấn nó tự đổi qua tab kia
	}

	// thêm new 
	$scope.createVoucher = function() {
		var item = angular.copy($scope.formVoucher);
		$http.post(`/api/vouchers`, item).then(resp => {

			$scope.itemsVoucher.push(resp.data);
			$scope.resetVoucher();
			$scope.message = "Thêm mới voucher thành công!";
			refreshTableVoucher();
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Lỗi thêm voucher";
		})

	}

	// update 
	$scope.updateVoucher = function() {
		var item = angular.copy($scope.formVoucher);
		$http.put(`/api/vouchers/${item.vouId}`, item).then(resp => {
			var index = $scope.itemsVoucher.findIndex(v => v.vouId == item.vouId);
			$scope.itemsVoucher[index] = item;
			console.log(item)
			$scope.message = "Cập nhật voucher thành công!";
			refreshTableVoucher();
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Lỗi cập nhật voucher";

		})
	}


	// Delete category function
	$scope.deleteVoucher = function(item) {
		$http.delete(`/api/vouchers/${item.vouId}`).then(resp => {
			var index = $scope.itemsVoucher.findIndex(v => v.vouId == item.vouId);
			$scope.itemsVoucher.splice(index, 1);
			$scope.message = "Xóa voucher thành công!";
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Đã xảy ra lỗi khi xóa voucher.";
		});
	};





	$scope.resetVoucher();


	// phân trang
	$scope.pagerVoucher = {
		page: 0,
		size: 3,
		get itemsVoucher() {
			var start = this.page * this.size;
			return $scope.itemsVoucher.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsVoucher.length / this.size);
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
})




