var application = angular.module('appname', ['snap']);


application.config(['$httpProvider',function ($httpProvider) {
 $httpProvider.defaults.useXDomain = true;
 delete $httpProvider.defaults.headers.common["X-Requested-With"];
 $httpProvider.defaults.headers.common["Access-Control-Allow-Origin"] = "*";

}]) ;

application.factory('dataFactory', ['$http', function($http) {
    var dataFactory = {};

    dataFactory.execute = function (cust) {

         return $http.post(apiUrl, cust);
    };

    return dataFactory;
}]);

application.controller('controller', ['$scope', 'dataFactory', 'SNAP_VERSION', 'snapRemote',
    function($scope, dataFactory, SNAP_VERSION, snapRemote ) {


    var locs = [
              {
                  lat : 37.803062,
                  long : -122.411354
              },
              {
                  lat : 37.798433,
                  long : -122.400947
              },

              {
                  lat : 37.798433,
                  long : -122.400947
              },

                            {
                  lat : 37.7960595,
                  long : -122.4103252
              }



          ];

    var mapOptions = {
                  zoom: 15,
                  center: new google.maps.LatLng(37.803062, -122.411354),
                  mapTypeId: google.maps.MapTypeId.ROADMAP
              }

    $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);

    function getRandom(min, max) {
        return min + Math.floor(Math.random() * (max - min + 1));
    }

    $scope.markers = [];

    var infoWindow = new google.maps.InfoWindow();

    var addMarker = function (member) {

        var marker = new google.maps.Marker({
            map: $scope.map,
            position: new google.maps.LatLng(member.Latitude, member.Longitude),
            title: member.Name
        });
        marker.content = '<div class="infoWindowContent">' + member.Name + '</div>';

        google.maps.event.addListener(marker, 'click', function(){
            infoWindow.setContent('<h3>' + marker.title + '</h3>' + marker.content);
            infoWindow.open($scope.map, marker);
        });

        $scope.markers.push(marker);

    }


    $scope.openInfoWindow = function(e, selectedMarker){
        e.preventDefault();
        google.maps.event.trigger(selectedMarker, 'click');
    }

    function setMapOnAll(map) {
      for (var i = 0; i < $scope.markers.length; i++) {
        $scope.markers[i].setMap(map);
      }
    }

    $scope.clearMap = function() {
        console.log("clear map");

        setMapOnAll(null);
    }

    $scope.snapVersion = SNAP_VERSION.full;

    $scope.doAdd = false;
    $scope.doShow = true;

    snapRemote.getSnapper().then(function(snapper) {
        snapper.open('left');
    });

    $scope.addItem = function() {
        insertData($scope.member);
    };


    $scope.changeItem = function(member) {
        editMember(member);
    };

    $scope.deleteItem = function(member) {
        deleteMember(member);
    };

    $scope.refresh = function() {
        getMembers();
    };

    getMembers();

    $scope.show = function(member) {
        $scope.selectedMember = member;

        console.log("show " + member.Name);
        $scope.doShow = true;
        $scope.doAdd = false;

        snapRemote.getSnapper().then(function(snapper) {
            snapper.open('right');
        });
    }

    $scope.add = function() {
        $scope.doAdd = true;
        $scope.doShow = false;

        snapRemote.getSnapper().then(function(snapper) {
            snapper.open('right');
        });
    }

    // Get from AWS Dynamo DB
    function getMembers() {

        var payload = {
            Select: 'ALL_ATTRIBUTES',
        };

        var data = {
            operation: 'list',
            tableName: member_table,
            payload: payload
        };

        dataFactory.execute(data)
            .then(function (response) {

                $scope.members = response.data.Items;

                refreshMapMarkers();
            }, function(error) {
                $scope.status = 'Unable to insert customer: ' + error.message;
            });
    }

    function refreshMapMarkers() {
        $scope.clearMap();

        var len = $scope.members.length;
                for (var i = 0; i < len; i++) {
                    console.log("LAT " + $scope.members[i].Latitude);

                    if ($scope.members[i].Latitude != null) {
                         addMarker($scope.members[i]);
                    }
                }
    }

    // Insert in AWS Dynamo DB
    function insertData(member) {

        member.ID = ($scope.members.length + 1).toString();

        index = getRandom(0, 3);

        member.Latitude = String(locs[index].lat);
        member.Longitude = String(locs[index].long);

        var payload = {
            Item: member
        };

        var data = {
            operation: 'create',
            tableName: member_table,
            payload: payload
        };

        dataFactory.execute(data)
            .then(function (response) {
               console.log("Inserted Customer " + response.data);

                $scope.status = response.data;

                getMembers();

                snapRemote.getSnapper().then(function(snapper) {
                    snapper.open('left');
                });
            }, function(error) {
                $scope.status = 'Unable to insert customer: ' + error.message;
            });
    };

    // Delete from AWS Dynamo DB
    function deleteMember(member) {

        var keys = {
            ID: member.ID,
            Name: member.Name
        };


        var payload = {
            Key: keys
        };

        var data = {
            operation: 'delete',
            tableName: member_table,
            payload: payload
        };

        dataFactory.execute(data)
            .then(function (response) {
               console.log("Deleted member " + response.data);

                $scope.status = response.data;

                getMembers();

                snapRemote.getSnapper().then(function(snapper) {
                    snapper.open('left');
                });
            }, function(error) {
                $scope.status = 'Unable to delete member: ' + error.message;
            });
    };

    // Edit from AWS Dynamo DB item
    function editMember(member) {



        var keys = {
            ID: member.ID,
            Name: member.Name
        };

        var values = {
            ':active': member.Active
        };

        var payload = {
            Key: keys,
            UpdateExpression: "set Active = :active",
            ExpressionAttributeValues: values
        };


        var data = {
            operation: 'update',
            tableName: member_table,
            payload: payload
        };

        dataFactory.execute(data)
            .then(function (response) {
               console.log("Updated Customer " + response.data);

                $scope.status = response.data;

                getMembers();

                snapRemote.getSnapper().then(function(snapper) {
                    snapper.open('left');
                });
            }, function(error) {
                $scope.status = 'Unable to update item: ' + error.message;
            });
    };
}]);

