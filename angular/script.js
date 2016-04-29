
var application = angular.module('appname', [

]);


application.config(['$httpProvider',function ($httpProvider) {
 $httpProvider.defaults.useXDomain = true;
 delete $httpProvider.defaults.headers.common["X-Requested-With"];
 $httpProvider.defaults.headers.common["Access-Control-Allow-Origin"] = "*";

}]) ;

application.factory('dataFactory', ['$http', function($http) {

    var apiUrl = 'https://s2z34x36ai.execute-api.us-east-1.amazonaws.com/prod/ImpromptuDBConnect';

    var dataFactory = {};

    dataFactory.execute = function (cust) {

         return $http.post(apiUrl, cust);
    };

    return dataFactory;
}]);

application.controller('theController', ['$scope', 'dataFactory',
  function($scope, dataFactory) {

    $scope.addItem = function() {
        insertData($scope.member);
    };

    $scope.refresh = function() {
        getMembers();
    };

    getMembers();

    // Get from AWS Dynamo DB
    function getMembers() {

        var payload = {
            Select: 'ALL_ATTRIBUTES',
        };

        var data = {
            operation: 'list',
            tableName: 'Member',
            payload: payload
        };

        dataFactory.execute(data)
            .then(function (response) {
               console.log("Got members list " + response.data);

                $scope.members = response.data.Items;
            }, function(error) {
                $scope.status = 'Unable to insert customer: ' + error.message;
            });


    }

    // Insert in AWS Dynamo DB
    function insertData(member) {

        var payload = {
            Item: member
        };

        var data = {
            operation: 'create',
            tableName: 'Member',
            payload: payload
        };

        dataFactory.execute(data)
            .then(function (response) {
               console.log("Inserted Customer " + response.data);

                $scope.status = response.data;
            }, function(error) {
                $scope.status = 'Unable to insert customer: ' + error.message;
            });
    };

}]);