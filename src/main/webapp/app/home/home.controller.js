(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('HomeController', HomeController);

    angular
    	.module('myGreatNzApp')
    	.controller('TypeaheadCtrl',TypeAheadController);
    
    angular
	.module('myGreatNzApp')
	.controller('TabController',TabController);
    
    angular
	.module('myGreatNzApp')
	.controller('DropdownController',DropdownController);
    
    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];
    TypeAheadController.$inject = ['$scope', '$http'];
    TabController.$inject = ['$scope', '$window'];
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
    function TabController($scope, $window) {
    	  $scope.tabs = [
    	    { title:'Dynamic Title 1', content:'Dynamic content 1' },
    	    { title:'Dynamic Title 2', content:'Dynamic content 2', disabled: true }
    	  ];

    	  $scope.alertMe = function() {
    	    setTimeout(function() {
    	      $window.alert('You\'ve selected the alert tab!');
    	    });
    	  };

    	  $scope.model = {
    	    name: 'Tabs'
    	  };
    	}
    function DropdownController($scope, $log) {
    	$scope.label = "Married";  
    	
    	$scope.items = [
    	    'The first choice!',
    	    'And another choice for you.',
    	    'but wait! A third!'
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
    function submitForm()
    {
      var form = document.getElementById("inputForm");
      form.action("result.html");
      form.submit();
      
    }
})();


