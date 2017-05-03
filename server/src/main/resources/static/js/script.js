var app = angular.module('app', ['ngCookies']);

app.controller('ResourcesController', function($scope, $http) {
   var getResources = function () {
       $http.get('/api/resources', {headers: {'X-AUTH-TOKEN': 'secret_token'}}).then(function(response) {
           $scope.resources = response.data;
       });
   };

   getResources();
});

app.controller('LoginController', function($scope, $http, $cookieStore, $window) {
    $scope.login = function() {
        $http({
            method: 'POST',
            url: '/login',
            data: $.param({username: angular.element('#username').val(), password: angular.element('#password').val()}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response) {
            $cookieStore.put('X-AUTH-TOKEN', response.headers('X-AUTH-TOKEN'));
            $window.location.href = '/resources';
        }, function() {
            angular.element('#login-error').show();
        });
    };

    angular.element('#login-error').hide();
});
