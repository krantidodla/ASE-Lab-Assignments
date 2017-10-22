angular.module("ap",[])
    .controller('con',function ($scope,$http) {
        $scope.search=function () {

            $http.get("http://127.0.0.1:8082/calories",{params: {name:$scope.in}}).success(function (data) {
                console.log(data)
                if (data != null) {
                    $scope.final= {
                        "score": data.calo[0].calories,
                        "image": data.calo[1].link,
                        "qt":data.calo[0].quantity,
                        "desc":data.calo[1].desc
                    }
                }
            });
            document.getElementById("col").style.display='block';
        }
    })