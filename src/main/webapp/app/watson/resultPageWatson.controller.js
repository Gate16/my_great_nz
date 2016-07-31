(function() {
    'use strict';

    angular.module('myGreatNzApp')
        .controller('ResultPageWatsonController', ResultPageWatsonController);

    ResultPageWatsonController.$inject = ['$scope', '$state'];

    function ResultPageWatsonController($scope) {
        $scope.audio = document.getElementById('audio');
        $scope.wavsource = document.getElementById('wavsource');
        $scope.text = "";

        $scope.init = function(newText) {
            $scope.text = newText;
            wavsource.src = '/api/public/speak?text=' + $scope.text;
        };
    }

})();
