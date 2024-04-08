
app.controller("admin-ctrl1", function($scope, $http) {
	$scope.message = null;
	$scope.auth = [];
	
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
	
	// DANH MỤC
	$scope.itemsCategory = [];
	$scope.formCategory = {};



	$scope.intializeCategory = function() {
		// load product
		$http.get("/rest/categories").then(resp => {
			$scope.itemsCategory = resp.data;
			console.log($scope.itemsCategory);
		});

	}
	// khở đầu
	$scope.intializeCategory();

	// load table
	function refreshTableCategory() {
		$http.get('/rest/categories').then(resp => {
			$scope.itemsCategory = resp.data;
			console.log($scope.itemsCategory);
		}).catch(error => {
			console.log("Error", error);
		});
	}

	//xóa form
	$scope.resetCategory = function() {
		$scope.formCategory = {




		};
	}


	// hiển thị
	$scope.editCategory = function(item) {
		$scope.formCategory = angular.copy(item);
		console.log($scope.formCategory)
		// $(".nav-tabs a:eq(0)").tab("show") // hàm xử lý lúc xài 2 tap và nhấn nó tự đổi qua tab kia
	}

	// thêm new 
	$scope.createCategory = function() {
		var item = angular.copy($scope.formCategory);
		$http.post(`/rest/categories`, item).then(resp => {

			$scope.itemsCategory.push(resp.data);
			$scope.resetCategory();
			$scope.message = "Thêm mới sản phẩm thành công!";
			refreshTableCategory();
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Lỗi thêm sản phẩm";
		})

	}

	// update 
	$scope.updateCategory = function() {
		var item = angular.copy($scope.formCategory);
		$http.put(`/rest/categories/${item.cateId}`, item).then(resp => {
			var index = $scope.itemsCategory.findIndex(c => c.cateId == item.cateId);
			$scope.itemsCategory[index] = item;
			$scope.message = "Cập nhật sản phẩm thành công!";
			refreshTableCategory();
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Lỗi cập nhật sản phẩm";

		})
	}


	// Delete category function
	$scope.deleteCategory = function(item) {
		// Check if the category has associated products
		$http.get(`/rest/products/category/${item.cateId}`).then(resp => {
			if (resp.data.length > 0) {
				// If there are associated products, display a message and do not delete
				$scope.message = "Category này đang có sản phẩm liên quan. Vui lòng xóa các sản phẩm trước khi xóa category.";
			} else {
				// If there are no associated products, delete the category
				$http.delete(`/rest/categories/${item.cateId}`).then(resp => {
					var index = $scope.itemsCategory.findIndex(c => c.cateId == item.cateId);
					$scope.itemsCategory.splice(index, 1);
					$scope.message = "Xóa category thành công!";
				}).catch(error => {
					console.log("Error", error);
					$scope.message = "Đã xảy ra lỗi khi xóa category.";
				});
			}
		}).catch(error => {
			console.log("Error", error);
			$scope.message = "Đã xảy ra lỗi khi kiểm tra sản phẩm liên quan đến category.";
		});
	};





	$scope.resetCategory();


	// phân trang
	$scope.pagerCategory = {
		page: 0,
		size: 3,
		get itemsCategory() {
			var start = this.page * this.size;
			return $scope.itemsCategory.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsCategory.length / this.size);
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




