(function() {
    'use strict';

    angular.module('myGreatNzApp')
        .controller('FrontPageWatsonController', FrontPageWatsonController);

    FrontPageWatsonController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

//    var audio = document.getElementById('audio');
//    var wavsource = document.getElementById('wavsource');

        function FrontPageWatsonController ($scope, Principal, LoginService, $state) {
//        var text ="Front Page Watson Controller"
//        wavsource.src = '/api/public/speak?text=' + text;
//
////        audio.load();
////        audio.play();

        $scope.audio = document.getElementById('audio');
        $scope.wavsource = document.getElementById('waveSourceFrontPage');
        $scope.text = "Welcome"

        $scope.init = function(text) {
            $scope.wavsource.src = '/api/public/speak?text=' + text;

//            $scope.audio.load();
//            $scope.audio.play();
         }

        $scope.init($scope.text);
    }

})();
