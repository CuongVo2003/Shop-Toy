app.controller("product-ctrl", function($scope, $http) {
    $scope.items = [];
    $scope.cates = [];
    $scope.form = {};
    $scope.message = null;

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
            })
        }
        // khở đầu
    $scope.intialize();

    // load table
    function refreshTable() {
        $http.get('/rest/products').then(resp => {
            $scope.items = resp.data;
        }).catch(error => {
            console.log("Error", error);
        });
    }

    //xóa form
    $scope.reset = function() {
        $scope.form = {
            createDate: new Date(),
            image: 'cloud-upload.jpg',
            available: true
        };
    }


    // hiển thị
    $scope.edit = function(item) {
        $scope.form = angular.copy(item);
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
        $http.put(`/rest/products/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
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
        $http.get(`/rest/orderDetails?productId=${item.id}`).then(orderDetailsResp => {
            var orderDetails = orderDetailsResp.data;

            if (orderDetails.length > 0) {

                alert("Có các đơn đặt hàng liên quan đến sản phẩm này. Xóa chúng trước khi xóa sản phẩm.");
            } else {

                $http.delete(`/rest/products/${item.id}`).then(resp => {
                    var index = $scope.items.findIndex(p => p.id == item.id);
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
            $scope.message = "Lỗi xóa sản phẩm";
        });
    }


    // upload hình
    $scope.imageChanged = function(files) {
        var data = new FormData();
        data.append('file', files[0]);
        $http.post('/rest/upload/images', data, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            $scope.form.image = resp.data.name;
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
        size: 10,
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



    // Kiểm tra dữ liệu được load từ server
    $scope.checkData = function() {
        console.log('$scope.items:', $scope.items);
    };

    // Kiểm tra xem có lỗi JavaScript nào xuất hiện không
    window.onerror = function(msg, url, lineNo, columnNo, error) {
        console.log('Error:', msg, 'URL:', url, 'Line:', lineNo, 'Column:', columnNo, 'Error object:', error);
        return false;
    };


});