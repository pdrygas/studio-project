var app = angular.module('app', ['ngCookies']);

app.controller('ResourcesController', function($scope, $http) {
   var getResources = function () {
       $http.get('/api/resources', {headers: {'X-AUTH-TOKEN': 'secret_token'}}).then(function(response) {
           $scope.resources = response.data;
       });
   };

   getResources();
});

app.controller('LoginController', function($scope, $http, $cookies, $window) {
    $scope.login = function() {
        $http({
            method: 'POST',
            url: '/login',
            data: $.param({username: angular.element('#username').val(), password: angular.element('#password').val()}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response) {
            setTokenCookie(response.headers('X-AUTH-TOKEN'));
            $window.location.href = '/resources';
        }, function() {
            angular.element('#login-error').show();
        });
    };

    var setTokenCookie = function(token) {
        var expireDate = new Date();
        expireDate.setDate(expireDate.getDate() + 365)
        $cookies.put('X-AUTH-TOKEN', token, {expires: expireDate});
    };

    angular.element('#login-error').hide();
});
