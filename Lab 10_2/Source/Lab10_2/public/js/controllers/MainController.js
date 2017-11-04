app.controller('MainController', ['$scope', '$http', function($scope, $http) {
    $scope.todo = {
        title: "Things I Need to Do",
        list: ["Clean my room", "Go to the store", "Study Cracking the Coding Interview"]
    }

    $scope.books = {
        title: "My Movie Watch List",
        list: []
    }

    function hasOnlyNumbers(item) {
        return /^[0-9]*$/.test(item)
    }

    $scope.addItem = function(itemList, item) {
        $http.get("http://api.themoviedb.org/3/search/movie?api_key=62c648959dff042c455e5d6d7ed0413b&query=" + item)
            .then(function(response) {
                mydata = response.data;
                $scope.statuscode = response.status;
                $scope.statustext = response.statusText;
                console.log($scope.content)
                itemList.push("Title of the movie:"+mydata.results[0].title+"\n"+ "Popularity of the movie:"+mydata.results[0].popularity+"\n"+
                    "IMDB rating:"+ mydata.results[0].vote_average+"\n"+"Movie Plot:"+mydata.results[0] .overview)
            });

    }

}]);