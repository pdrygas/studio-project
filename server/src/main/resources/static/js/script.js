var app = angular.module('app', ['ngCookies']);

app.controller('ResourcesController', function($scope, $http, $cookies) {
    $scope.deleteResource = function(id, index) {
        $http.delete('/api/resources/' + id, headers()).then(function(response) {
            if(response.data.result == 'ok') {
               $scope.resources.splice(index,  1);
            }
        });
    };

    $scope.filtered = {};
    $scope.filterCategories = function() {
        return function(resource) {
            return $scope.filtered[resource.category];
        }
    };

    var getResources = function() {
        $http.get('/api/resources', headers($cookies)).then(function(response) {
            $scope.resources = response.data;
        });
    };

    var getCategories = function() {
        $http.get('/api/categories', headers($cookies)).then(function(response) {
            $scope.categories = response.data;
            showAllCategories();
        });
    };

    var showAllCategories = function() {
        $scope.categories.forEach(function(category) {
            $scope.filtered[category.title] = true;
        });
    };

    getResources();
    getCategories();
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

    if($cookies.get('X-AUTH-TOKEN') != undefined) {
        $window.location.href = '/resources';
    }
    angular.element('#login-error').hide();
});

app.controller('ResourceController', function($scope, $http, $cookies, $location) {
    var id = parseInt($location.absUrl().split('/').pop());
    $http.get('/api/resources/' + id, headers($cookies)).then(function(response) {
        $scope.resource = response.data;
    });
});

app.controller('NavbarController', function($scope, $cookies, $window) {
    $scope.logout = function() {
        $cookies.remove('X-AUTH-TOKEN', {path: '/'});
        $window.location.href = '/';
    };
});

app.controller('CategoriesController', function($scope, $http, $cookies) {
    var getCategories = function() {
        $http.get('/api/categories', headers($cookies)).then(function(response) {
            $scope.categories = response.data;
        });
    };

    getCategories();
});

app.controller('CategoryController', function($scope, $http, $cookies, $location) {
    var id = parseInt($location.absUrl().split('/').pop());
    $http.get('/api/categories/' + id, headers($cookies)).then(function(response) {
        $scope.category = response.data;
    });
    $http.get('/api/categories/' + id + '/resources', headers($cookies)).then(function(response) {
        $scope.resources = response.data;
    });
});

var headers = function(cookies) {
    return {headers: {'X-AUTH-TOKEN': cookies.get('X-AUTH-TOKEN')}};
};