var app = angular.module('app', []);

app.controller('ResourcesController', function($scope, $http) {
   var getResources = function () {
       $http.get('/api/resources', {headers: {'X-AUTH-TOKEN': 'secret_token'}}).then(function(response) {
           $scope.resources = response.data;
       });
   };

   getResources();
});