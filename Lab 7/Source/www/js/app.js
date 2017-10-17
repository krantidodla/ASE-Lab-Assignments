


angular.module('myApp', [])


  .controller('HomeCtrl', function ($scope, $http) {
    $scope.venueList = new Array();
    $scope.mostRecentReview;
    $scope.getVenues = function () {
      var placeEntered = document.getElementById("place").value;
      var searchQuery = document.getElementById("eat").value;
      $scope.getcustomsearch(searchQuery)
      if (placeEntered != null && placeEntered != "" && searchQuery != null && searchQuery != "") {
        document.getElementById('div_ReviewList').style.display = 'none';

        var handler = $http.get("https://api.foursquare.com/v2/venues/search" +
          "?client_id=RPCLY5C5TUHTG3T2AKEPZKEEHKNAZE2J32E0NXX33XBNKSWD" +
          "&client_secret=BHNJJEU1UC5V2ZA5SA2Q4GFO5EFSYPF4YMR1VXXHI10RC2FL" +
          "&v=20160215&limit=5" +
          "&near=" + placeEntered +
          "&query=" + searchQuery);
        handler.success(function (data) {

          if (data != null && data.response != null && data.response.venues != undefined && data.response.venues != null) {
            for (var i = 0; i < data.response.venues.length; i++) {
              $scope.venueList[i] = {
                "name": data.response.venues[i].name,
                "id": data.response.venues[i].id,
                "location": data.response.venues[i].location
              };
            }
          }

        })
        handler.error(function (data) {
          alert("ERROR");
        });
      }
    }
    $scope.getcustomsearch = function (itemsearch) {
      if (itemsearch != null) {

        var handler = $http.get("https://www.googleapis.com/customsearch/v1?key=AIzaSyCuzU_yiKk14txpOZdG5vnO0-fMF4vGvZA&q=" + itemsearch + "&searchType=image&cx=011893909504029951352:o-dneodirwi")
          .then(function (response) {
            console.log(response)
            console.log(response.data.items[0].link)
            //$scope.imglink = response.data.items[0].link;
          })
      }
    }
  });
