(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('HomeController', HomeController);

    angular
    	.module('myGreatNzApp')
    	.controller('TypeaheadController',TypeAheadController);
    
    angular
	.module('myGreatNzApp')
	.controller('ButtonController',ButtonController);
    
    
    angular
	.module('myGreatNzApp')
	.controller('DropdownController',DropdownController);
    
    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];
    TypeAheadController.$inject = ['$scope', '$http'];
    ButtonController.$inject = ['$scope', '$window'];
    DropdownController.$inject = ['$scope', '$log'];
    
    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
    function TypeAheadController($scope, $http)
    {
      var _selected;

      $scope.selected = undefined;
   	  $scope.professions = ['Management', 'IT Professional', 'Human Resources', 'Finance', 'Real Estate', 'Farmer', 'Underwater Basket Weaver'];
      $scope.nationalities = ['New Zealand', 'China', 'India','Russia','Great Britian/United Kingdom', 'England', 'Ireland','Scotland','Wales','United States of America','Canada','Vietnam','Cambodia','Japan' ];
   	  
   	  // Any function returning a promise object can be used to load values asynchronously
      $scope.getLocation = function(val) {
    	    return $http.get('//maps.googleapis.com/maps/api/geocode/json', {
    	      params: {
    	        address: val,
    	        sensor: false
    	      }
    	    }).then(function(response){
    	      return response.data.results.map(function(item){
    	        return item.formatted_address;
    	      });
    	    });
    	  };

    	  $scope.ngModelOptionsSelected = function(value) {
    	    if (arguments.length) {
    	      _selected = value;
    	    } else {
    	      return _selected;
    	    }
    	  };

    	  $scope.modelOptions = {
    	    debounce: {
    	      'default': 500,
    	      blur: 250
    	    },
    	    getterSetter: true
    	  };
	
    	
    }
    function ButtonController($scope, $window) {
    	  $scope.singleModel = 1;

    	  $scope.radioModel = 'DC';

    	  $scope.checkModel = {
    	    left: false,
    	    middle: true,
    	    right: false
    	  };

    	  $scope.checkResults = [];

    	  $scope.$watchCollection('checkModel', function () {
    	    $scope.checkResults = [];
    	    angular.forEach($scope.checkModel, function (value, key) {
    	      if (value) {
    	        $scope.checkResults.push(key);
    	      }
    	    });
    	  });
    	}

    function DropdownController($scope, $log) {
    	$scope.label = "Married";  
    	$scope.selectedItem="Family";
    	
        $scope.dropboxitemselected = function (item) { 
            $scope.selectedItem = item;
        }
    	$scope.relationships = [
    	    'Married without kids',
    	    'Married with kids',
    	    'Single'
    	  ];

    	  $scope.status = {
    	    isopen: false
    	  };

    	  $scope.toggled = function(open) {
    	    $log.log('Dropdown is now: ', open);
    	  };

    	  $scope.toggleDropdown = function($event) {
    	    $event.preventDefault();
    	    $event.stopPropagation();
    	    $scope.status.isopen = !$scope.status.isopen;
    	  };

    	  $scope.appendToEl = angular.element(document.querySelector('#dropdown-long-content'));
    	}
})();


