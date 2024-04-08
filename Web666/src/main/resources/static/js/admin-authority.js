
app.controller("authority-ctrl", function($scope, $http, $location) {

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


	$scope.roles = [];
	$scope.admins = [];
	$scope.authorities = [];
	$scope.message = null;

	$scope.initialize = function() {
		// load all roles
		$http.get("/rest/roles").then(resp => {
			$scope.roles = resp.data;
		})

		// load nhân viên và admin 
		$http.get("/rest/accounts?admin=true")
			.then(resp => {
				$scope.admins = resp.data;
			})
			.catch(error => {
				console.error("Error fetching data:", error);
				// Hiển thị thông báo lỗi hoặc xử lý một cách phù hợp
			});

		// load authorities of nhân viên và admin
		$http.get("/rest/authorities?admin=true").then(resp => {
			$scope.authorities = resp.data;
		}).catch(error => {
			$window.location.href = "/admin/index";
		})

		// kiểm tra tìm xem có quyền hay không trong ng-checked
		$scope.authority_of = function(acc, role) {
			if ($scope.authorities) {
				return $scope.authorities.find(ur => ur.account.username == acc.username && ur.role.roleId == role.roleId);
			}
		}

		// click cấp quyền hoặc đổi quyền
		$scope.authority_changed = function(acc, role) {
			var authority = $scope.authority_of(acc, role);
			if (authority) { // đã cấp quyền => thu dồi quyền
				$scope.revoke_authority(authority);
			} else { // chưa cấp và click để cấp
				authority = { account: acc, role: role };
				$scope.grant_authority(authority);
			}
		}

		// thêm mới quyền
		$scope.grant_authority = function(authority) {
			$http.post("/rest/authorities", authority).then(resp => {
				$scope.authorities.push(resp.data)
				$scope.message = "Cấp quyền sử dụng thành công!";
				alert("tc")
			}).catch(error => {
				$scope.message = "Cấp quyền sử dụng thất bại vì bạn ko có quyền thay đổi!";
				alert("tb")
				console.log("Error", error);
			})
		}

		// xóa quyền
		$scope.revoke_authority = function(authority) {
			$http.delete(`/rest/authorities/${authority.authId}`).then(resp => {
				var index = $scope.authorities.findIndex(a => a.authId == authority.authId);
				$scope.authorities.splice(index, 1);
				$scope.message = "Thu hồi quyền sử dụng thành công!";
				alert("tc")
			}).catch(error => {
				$scope.message = "Thu hồi quyền sử dụng thất bại vì bạn ko có quyền thay đổi!";
				alert("tb")
				console.log("Error", error);
			})
		}

	}
	$scope.initialize();








});