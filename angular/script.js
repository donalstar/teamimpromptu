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

application.controller('theController', ['$scope', 'dataFactory', 'SNAP_VERSION', 'snapRemote',
    function($scope, dataFactory, SNAP_VERSION, snapRemote ) {

    $scope.snapVersion = SNAP_VERSION.full;

    snapRemote.getSnapper().then(function(snapper) {
        snapper.open('left');
    });

    $scope.addItem = function() {
        insertData($scope.member);

        getMembers();
    };

    $scope.editItem = function(member) {
        member.Visible = !member.Visible;
    };

    $scope.changeItem = function(member) {
        editMember(member);

        member.Visible = !member.Visible;
    };

    $scope.deleteItem = function(member) {
        deleteMember(member);

        getMembers();
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
            tableName: member_table,
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

        member.ID = ($scope.members.length + 1).toString();

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
            }, function(error) {
                $scope.status = 'Unable to update item: ' + error.message;
            });
    };
}]);

